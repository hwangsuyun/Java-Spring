package com.example.web.controller;

import com.example.file.ReadFile;
import com.example.file.WriteFile;
import com.example.web.service.TestService;
import com.example.web.vo.TestVo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
// @RequiredArgsConstructor // 해당 어노테이션은 자동으로 생성자 주입이 돼서 아래 생성자 사용 안해도 됨
public class HelloController {
    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /* @Autowired 필드 주입
    @Autowired
    private TestService testService;
    @Autowired
    private TestReadFile testReadFile;
    @Autowired
    private TestWriteFile testWriteFile;
    */

    /* 생성자 주입 */
    private final TestService testService;
    private final ReadFile testReadFile;
    private final WriteFile writeFile;
    public HelloController(TestService testService, ReadFile testReadFile, WriteFile writeFile) {
        this.testService = testService;
        this.testReadFile = testReadFile;
        this.writeFile = writeFile;
    }

    @ApiOperation(value = "/hello", notes = "hello page")
    @RequestMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @ApiOperation(value = "/test", notes = "test page")
    @RequestMapping("/test")
    public ModelAndView test() throws Exception {
        logger.info("log test");
        ModelAndView mav = new ModelAndView("test");
        mav.addObject("name", "test");

        List<String> testList = new ArrayList<>();
        testList.add("a");
        testList.add("b");
        testList.add("c");

        mav.addObject("list", testList);
        return mav;
    }

    @ApiOperation(value = "/thymeleafTest", notes = "thymeleaf page")
    @RequestMapping("/thymeleafTest")
    public String thymeleafTest(Model model) {
        TestVo testModel = new TestVo("test", "테스트") ;
        model.addAttribute("testModel", testModel);
        return "thymeleaf/thymeleafTest";
    }

    @ApiOperation(value = "/builderTest", notes = "builder page")
    @RequestMapping("/builderTest")
    public String builderTest(Model model) {
        TestVo testModel = TestVo.builder()
                        .id("test2")
                        .name("테스트2")
                        .build();
        model.addAttribute("testModel", testModel);
        return "thymeleaf/builderTest";
    }

    @ApiOperation(value = "/injectionTest", notes = "injection page")
    @RequestMapping("/injectionTest")
    public String injectionTest(Model model) {
        model.addAttribute("testModel", testService.setIdName());
        return "thymeleaf/injectionTest";
    }

    @ApiOperation(value = "/readFileTest", notes = "readFile page")
    @RequestMapping("/readFileTest")
    public ModelAndView readFileTest() {
        ModelAndView mav = new ModelAndView("test");
        mav.addObject("list", testReadFile.read());
        return mav;
    }

    @ApiOperation(value = "/writeFileTest", notes = "writeFile page")
    @RequestMapping("/writeFileTest")
    @ResponseBody
    public String writeFileTest() {
        writeFile.write();
        return "File write succeeded";
    }
}