package com.foxconn.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.cms.domain.CrmBanner;
import com.foxconn.cms.service.CrmBannerService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器后台管理接口
 * </p>
 *
 * @author zj
 * @since 2020-06-14
 */
@RestController
@RequestMapping("/cms/admin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询banner列表
     *
     * @param current 当前页
     * @param limit   每页数量
     * @return
     */
    @GetMapping("/banner/{current}/{limit}")
    public Result pagingFindBanners(@PathVariable long current, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(current, limit);
        bannerService.page(pageBanner, null);
        return Result.ok().data("banners", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    /**
     * 根据id查询banner
     *
     * @param bannerId 主键id
     * @return
     */
    @GetMapping("/banner/{bannerId}")
    public Result findBannerById(@PathVariable String bannerId) {
        CrmBanner banner = bannerService.getById(bannerId);
        return Result.ok().data("banner", banner);
    }

    /**
     * 新增banner
     *
     * @param crmBanner
     * @return
     */
    @PostMapping("/banner")
    public Result saveBanner(@RequestBody CrmBanner crmBanner) {
        boolean flag = bannerService.save(crmBanner);
        return flag ? Result.ok() : Result.error();
    }

    /**
     * 更新banner
     *
     * @param crmBanner
     * @return
     */
    @PutMapping("/banner")
    public Result updateBanner(@RequestBody CrmBanner crmBanner) {
        boolean flag = bannerService.updateById(crmBanner);
        return flag ? Result.ok() : Result.error();
    }

    /**
     * 删除banner
     *
     * @param bannerId
     * @return
     */
    @DeleteMapping("/banner/{bannerId}")
    public Result deleteBannerById(@PathVariable String bannerId) {
        boolean flag = bannerService.removeById(bannerId);
        return flag ? Result.ok() : Result.error();
    }

}

