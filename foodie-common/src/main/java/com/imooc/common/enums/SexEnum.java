package com.imooc.common.enums;

import lombok.Getter;

/**
 * 性别enum
 * @author sunyu
 */
public enum SexEnum {
    /**
     *
     */
    WOMAN(0, "女"),
    MAN(1, "男"),
    SECRET(2, "保密");

    @Getter
    private final String desc;

    @Getter
    private final Integer type;

    SexEnum(Integer type, String desc) {
        this.desc = desc;
        this.type = type;
    }
}
