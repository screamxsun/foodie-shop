package com.imooc.service;

import com.imooc.common.utils.PagedGridResult;
import com.imooc.pojo.Carousel;

import java.util.List;

public interface CarouselService {

    /**
     * 查询所有展示轮播图
     *
     * @param isShow 是否展示
     * @return list
     */
    List<Carousel> queryAll(Integer isShow);


}
