package com.example.web.service;

import com.example.web.vo.TestVo;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public TestVo setIdName() {
        return TestVo.builder()
                    .id("test3")
                    .name("테스트3")
                    .build();
    }
}
