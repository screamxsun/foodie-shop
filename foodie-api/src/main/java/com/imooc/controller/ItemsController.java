package com.imooc.controller;


import com.imooc.common.enums.YesOrNoEnum;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.common.utils.PagedGridResult;
import com.imooc.pojo.*;
import com.imooc.service.CarouselService;
import com.imooc.service.ItemService;
import com.imooc.vo.CommentLevelCountsVO;
import com.imooc.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "商品接口", tags = {"商品信息展示相关信息"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {

    @Resource
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO = ItemInfoVO.builder()
                .item(item)
                .itemImgList(itemImgList)
                .itemSpecList(itemsSpecList)
                .itemParams(itemsParam).build();
        return IMOOCJSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);

        return IMOOCJSONResult.ok(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.queryPagedComments(itemId,
                level,
                page,
                pageSize);

        return IMOOCJSONResult.ok(grid);
    }

    @ApiOperation(value = "搜索商品", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public IMOOCJSONResult search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(keywords)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(keywords,
                sort,
                page,
                pageSize);

        return IMOOCJSONResult.ok(grid);
    }

    @ApiOperation(value = "通过分类Id搜索商品列表", notes = "通过分类Id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(
            @ApiParam(name = "catId", value = "三级分类Id", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (null == catId) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(catId,
                sort,
                page,
                pageSize);

        return IMOOCJSONResult.ok(grid);
    }


}