package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("memberTest")
public class TestJpaRestController {
    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


}
