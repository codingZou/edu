package com.foxconn.ucenter.service;

import com.foxconn.ucenter.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foxconn.ucenter.domain.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zj
 * @since 2020-06-18
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    boolean regist(RegisterVo registerVo);
}
