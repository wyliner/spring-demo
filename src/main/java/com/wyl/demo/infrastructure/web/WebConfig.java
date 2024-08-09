package com.wyl.demo.infrastructure.web;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import com.wyl.demo.infrastructure.Constants;
import com.wyl.demo.infrastructure.converter.StringToEnumConverterFactory;
import com.wyl.demo.infrastructure.filter.AccessLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author wyl19940929@163.com
 */
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 用于处理接收参数枚举值的映射
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        // 清除默认的MappingJackson2HttpMessageConverter
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        final FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        final FastJsonConfig fastJsonConfig = fastConverter.getFastJsonConfig();
        // https://alibaba.github.io/fastjson2/features_cn.html
        fastJsonConfig.setWriterFeatures(
                JSONWriter.Feature.WriteMapNullValue,
                // 将List类型字段的空值序列化输出为空数组”[]”
                JSONWriter.Feature.WriteNullListAsEmpty
        );
        fastJsonConfig.setReaderFeatures(
                JSONReader.Feature.SupportSmartMatch
        );
        fastJsonConfig.setDateFormat(Constants.DATE_TIME_PATTERN);
        // 添加FastJsonHttpMessageConverter
        converters.add(fastConverter);
    }

    /**
     * 注册访问log过滤器
     *
     * @return bean
     */
    @Bean
    public FilterRegistrationBean<AccessLogFilter> accessLogFilterFilterRegistrationBean(final AccessLogFilter accessLogFilter) {
        final FilterRegistrationBean<AccessLogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(accessLogFilter);
        registration.addUrlPatterns("/*");
        registration.setName("accessLogFilter");
        registration.setOrder(-1);
        return registration;
    }

}
