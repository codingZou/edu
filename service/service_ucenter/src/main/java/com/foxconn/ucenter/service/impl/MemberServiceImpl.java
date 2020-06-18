package com.foxconn.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import com.foxconn.ucenter.domain.Member;
import com.foxconn.ucenter.domain.vo.RegisterVo;
import com.foxconn.ucenter.mapper.MemberMapper;
import com.foxconn.ucenter.service.MemberService;
import com.foxconn.util.JwtUtils;
import com.foxconn.util.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zj
 * @since 2020-06-18
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录
     *
     * @param member 用户信息
     * @return
     */
    @Override
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        QueryWrapper<Member> memberQuery = new QueryWrapper<>();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new BaseExceptionHandler(90001, "用户名或密码不能为空");
        }
        memberQuery.eq("mobile", member.getMobile());
        Member user = baseMapper.selectOne(memberQuery);
        if (user == null) {
            throw new BaseExceptionHandler(60001, "用户名或密码错误");
        }
        if (!MD5.encrypt(password).equals(user.getPassword())) {
            throw new BaseExceptionHandler(60002, "用户名或密码错误");
        }
        if (user.getIsDisabled()) {
            throw new BaseExceptionHandler(60003, "该账号已被封禁");
        }
        //登录成功使用jwt根据用户id和昵称生成一个token
        return JwtUtils.getJwtToken(user.getId(), user.getNickname());
    }

    /**
     * 注册用户
     *
     * @param registerVo
     * @return
     */
    @Override
    public boolean regist(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //TODO 后面引入hibernateValidator进行校验
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)) {
            throw new BaseExceptionHandler(90002, "参数错误");
        }
        //校验验证码
        String mobileCode = stringRedisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobileCode)) {
            throw new BaseExceptionHandler(90003, "验证码错误");
        }
        QueryWrapper<Member> memberQuery = new QueryWrapper<>();
        memberQuery.eq("mobile", registerVo.getMobile());
        Integer count = baseMapper.selectCount(memberQuery);
        if (count > 0) {
            throw new BaseExceptionHandler(60004, "该手机号码已被注册");
        }
        Member member = new Member();
        registerVo.setPassword(MD5.encrypt(registerVo.getPassword()));
        BeanUtils.copyProperties(registerVo, member);
        member.setAvatar("https://live-edu.oss-cn-shenzhen.aliyuncs.com/2020/avatar/top.jpg");//设置默认头像
        try {
            count = baseMapper.insert(member);
        } catch (Exception e) {
            throw new BaseExceptionHandler(60005, "注册失败");
        }
        return count > 0;
    }
}
