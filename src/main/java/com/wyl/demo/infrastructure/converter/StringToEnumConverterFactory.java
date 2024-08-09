package com.wyl.demo.infrastructure.converter;

import com.wyl.demo.infrastructure.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.Assert;

import java.util.EnumSet;

/**
 * {@code Enum}接口需要替换为自己定义的枚举类的接口
 * 替代了{@link org.springframework.core.convert.support.StringToEnumConverterFactory}
 *
 * @author wyl19940929@163.com
 */
public final class StringToEnumConverterFactory implements ConverterFactory<String, IEnum> {
    /**
     * Get the converter to convert from S to target type T, where T is also an instance of R.
     *
     * @param targetType the target type to convert to
     * @return a converter from S to T
     */
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(final Class<T> targetType) {
        return new StringToEnum(getEnumType(targetType));
    }


    private static class StringToEnum<T extends Enum> implements Converter<String, T> {
        private final Class<T> enumType;

        @SuppressWarnings("checkstyle:RedundantModifier")
        public StringToEnum(final Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(final String source) {
            if (source.isEmpty()) {
                return null;
            }
            final EnumSet enumSet = EnumSet.allOf(this.enumType);
            for (final Object next : enumSet) {
                final IEnum item = (IEnum) next;
                if (source.equals(item.getValue().toString())) {
                    return (T) item;
                }
            }
            return (T) Enum.valueOf(this.enumType, source.trim());
        }
    }

    public static Class<?> getEnumType(final Class<?> targetType) {
        Class enumType;
        for (enumType = targetType; enumType != null && !enumType.isEnum(); enumType = enumType.getSuperclass()) {
            // no
        }
        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }
}
