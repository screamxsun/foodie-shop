package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.pojo.Carousel;
import com.imooc.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll(Integer isShow) {
        // 1. example
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc(); // 根据指定字段倒序查询
        example.createCriteria().andEqualTo("isShow",isShow);
        return carouselMapper.selectByExample(example);
    }


}
