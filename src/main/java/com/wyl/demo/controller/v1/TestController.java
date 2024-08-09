package com.wyl.demo.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyl.demo.infrastructure.response.ResponseResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wyl19940929@163.com
 */
@RequestMapping
@RestController
@AllArgsConstructor
public class TestController {
    private final ObjectMapper objectMapper;

    @GetMapping("/test")
    public ResponseResult<TestRequest> test() {
        final TestRequest request = new TestRequest();
        request.setTest(TestEnum.ONE);
        request.setTime(LocalDateTime.now());
        request.setDate(new Date());
        request.setName("aaaaa");
        return ResponseResult.success(request);
    }


    private void testc() {
        try {
            // objectMapper.registerModule(new JavaTimeModule());
            System.out.println(objectMapper.writeValueAsString(new Date()));
            System.out.println(objectMapper.writeValueAsString(LocalDate.now()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
