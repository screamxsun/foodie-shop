package com.imooc.common.enums;

import lombok.Getter;

/**
 * 性别enum
 * @author sunyu
 */
public enum YesOrNoEnum {
    /**
     *
     */
    NO(0, "否"),
    YES(1, "是");

    @Getter
    private final String desc;
    @Getter
    private final Integer type;

    YesOrNoEnum(Integer type, String desc) {
        this.desc = desc;
        this.type = type;
    }
}
