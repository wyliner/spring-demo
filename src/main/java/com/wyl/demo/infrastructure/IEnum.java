package com.wyl.demo.infrastructure;

import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 1. 用于标记枚举,使用{@link ConverterFactory }对字段值转换成对应的枚举类<p>
 *
 * @author wyl19940929@163.com
 */
public interface IEnum<T> {

    T getValue();
}
