package com.foxconn.ucenter.controller;

import com.foxconn.servicebase.exception.BaseExceptionHandler;
import com.foxconn.ucenter.domain.Member;
import com.foxconn.ucenter.service.MemberService;
import com.foxconn.ucenter.utils.ConstantPropertiesUtil;
import com.foxconn.ucenter.utils.HttpClientUtils;
import com.foxconn.util.JwtUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 微信扫码登录
 *
 * @author zj
 * @create 2020-06-20 16:50
 */
@RequestMapping("/api/ucenter/wx")
@Controller
public class WeChatController {
    @Autowired
    private MemberService memberService;

    /**
     * 获取微信登录二维码
     *
     * @param session
     * @return
     */
    @GetMapping("/login")
    public String weChatLoginQrCode(HttpSession session) {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new BaseExceptionHandler(90009, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }

    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {

        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        String result;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new BaseExceptionHandler(90005, "获取access_token失败");
        }
        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openId = (String) map.get("openid");

        //判断当前用户是否曾经使用过微信登录
        Member member = memberService.getByOpenid(openId);
        if (member == null) { // 新用户注册
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openId);
            String userInfo;
            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new BaseExceptionHandler(90007, "获取用户信息失败");
            }
            //解析json
            HashMap mapUserInfo = gson.fromJson(userInfo, HashMap.class);
            String nickName = (String) mapUserInfo.get("nickname"); //微信昵称
            String headImgUrl = (String) mapUserInfo.get("headimgurl"); //微信头像地址
            //向数据库中新增用户数据
            member = new Member();
            member.setNickname(nickName);
            member.setOpenid(openId);
            member.setAvatar(headImgUrl);
            memberService.save(member);
        }
        //有使用过微信登录则直接登录
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }
}
