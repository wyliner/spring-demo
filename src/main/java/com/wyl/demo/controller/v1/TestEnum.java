package com.wyl.demo.controller.v1;


import com.wyl.demo.infrastructure.IEnum;

/**
 * @author yonglin.wang@wyze.cn
 */
public enum TestEnum implements IEnum<Integer> {
    ONE("one", 3),

    TWO("two", 4);

    private final String name;
    private final Integer value;

    TestEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
