package com.foxconn.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foxconn.cms.domain.CrmBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zj
 * @since 2020-06-14
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> listBanners();
}
