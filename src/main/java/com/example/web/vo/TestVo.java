package com.example.web.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestVo {
    private Long mbrNo;
    private String id;
    private String name;

    @Builder
    public TestVo(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
