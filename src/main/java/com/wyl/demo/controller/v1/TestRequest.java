package com.wyl.demo.controller.v1;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wyl19940929@163.com
 */
@Getter
@Setter
@ToString
public class TestRequest {
    private String name;
    private TestEnum test;
    private LocalDateTime time;
    private Date date;
}
