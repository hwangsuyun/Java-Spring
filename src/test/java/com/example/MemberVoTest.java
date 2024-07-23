package com.example;

import com.example.database.vo.MemberVo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberVoTest {

    @Test
    public void getId() {
        final MemberVo memberVo = MemberVo.builder()
                .id("test")
                .name("테스트")
                .build();
        final String id = memberVo.getId();
        assertEquals("test", id);
    }

    @Test
    public void getName() {
        final MemberVo memberVo = MemberVo.builder()
                .id("test")
                .name("테스트")
                .build();
        final String name = memberVo.getName();
        assertEquals("스트", name);
    }
}
