package com.foxconn.cms.controller;


import com.foxconn.cms.domain.CrmBanner;
import com.foxconn.cms.service.CrmBannerService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台首页banner显示
 * </p>
 *
 * @author zj
 * @since 2020-06-14
 */
@RestController
@RequestMapping("/cms/front")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/index/banner")
    public Result findAllBanners() {
        List<CrmBanner> banners = bannerService.listBanners();
        return Result.ok().data("banners", banners);
    }

}

