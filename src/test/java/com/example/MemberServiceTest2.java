package com.example;

import com.example.database.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MemberServiceTest2 {
    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;

    public MemberServiceTest2() {}

    public MemberServiceTest2(RestTemplate restTemplateBuilder) {
        //this.restTemplate = restTemplateBuilder.build();
        this.restTemplate = restTemplateBuilder;
    }

    public MemberVo getMember(Long mbrNo) {
        MemberVo response = restTemplate.getForObject("/memberTest/" + mbrNo, MemberVo.class);
        logger.info("getMember2 : " + response);
        return response;
    }
}
