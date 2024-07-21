package com.example;

import com.example.database.repository.MemberRepository;
import com.example.database.vo.MemberVo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(MemberServiceTest2.class)
public class MemberServiceTest {
    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private MemberServiceTest2 memberService;

    @Test
    @Disabled
    void getMember() {
        server.expect(requestTo("/memberTest/1"))
                .andRespond(withSuccess(new ClassPathResource("/test.json", getClass()), MediaType.APPLICATION_JSON));

        //MemberVo member = memberService.getMember(1L);
        MemberVo member = memberService.getMember(1L);

        // ※ Junit4 사용시
        // assertThat("test2").isEqualTo(member.getId());
        // assertThat("테스트").isEqualTo(member..getName());

        // Junit5 BDD 사용시
        then("test2").isEqualTo(member.getId());
        then("테스트").isEqualTo(member.getName());
    }
}
