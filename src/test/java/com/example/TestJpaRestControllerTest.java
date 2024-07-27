package com.example;

import com.example.database.service.MemberService;
import com.example.database.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@RunWith(SpringRunner.class) // * Junit4 사용시
@SpringBootTest (
        properties = {
                "testId=test",
                "testName=테스트"
        }
        //classes = {TestJpaRestController.class, MemberService.class},
        ,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

@Transactional
@AutoConfigureMockMvc
@Slf4j
public class TestJpaRestControllerTest {
    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${testId}")
    private String testId;

    @Value("${testName}")
    private String testName;

    /*@MockBean
    private MemberRepository memberRepository;
     */

    @Autowired
    MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    // Service로 등록하는 빈
    @Autowired
    private MemberService memberService;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach //Junit4의 @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @Disabled
    void getMember() throws Exception {
        logger.info("##### Properties 테스트 #####");

        /******** START : MOC MVC test **********/
        logger.info("******** START : MOC MVC test **********");
        mvc.perform(get("/memberTest/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("test")))
                .andDo(print());
        logger.info("******** END : MOC MVC test **********");
        /******** END : MOC MVC test **********/

        /******** START : TestRestTemplate test **********/
        logger.info("******** START : TestRestTemplate test **********");
        ResponseEntity<MemberVo> response = restTemplate.getForEntity("/memberTest/1", MemberVo.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isNotNull();
        logger.info("******** END : TestRestTemplate test **********");
        /******** END : TestRestTemplate test **********/

        /******** START : MockBean test **********/
        logger.info("******** START : MockBean test **********");
        /*
        MemberVo memberVo = MemberVo.builder()
                .id(testId)
                .name(testName)
                .build();

        given(memberRepository.findById(1L))
                .willReturn(Optional.of(memberVo));*/

        Optional<MemberVo> member = memberService.findById(1L);
        if (member.isPresent()) {
            // ※ Junit4 사용시
            // assertThat(memberVo.getId()).isEqualTo(member.get().getId());
            // assertThat(memberVo.getName()).isEqualTo(member.get().getName());

            // Junit5 BDD 사용시
            then("test").isEqualTo(member.get().getId());
            then("테스트").isEqualTo(member.get().getName());
        }
        logger.info("******** END : MockBean test **********");
        /******** END : MockBean test **********/
    }
}
