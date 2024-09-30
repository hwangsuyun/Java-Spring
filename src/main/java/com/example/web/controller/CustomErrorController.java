package com.example.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@SuppressWarnings("unused")
@Controller
public class CustomErrorController implements ErrorController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //private String VIEW_PATH = "/errors/";
    private static final String VIEW_PATH = "thymeleaf/errors/";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // 에러 코드를 획득한다.
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // 에러 코드에 대한 상태 정보
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(status.toString()));

        if(status != null){
            // HttpStatus 와 비교해 페이지 분기를 나누기 위한 변수
            int statusCode = Integer.parseInt(status.toString());

            // 로그로 상태값을 기록 및 출력
            logger.info("httpStatus : " + statusCode);

            // 404 error
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                // 에러 페이지에 표시할 정보
                model.addAttribute("code", status.toString());
                model.addAttribute("msg", httpStatus.getReasonPhrase());
                model.addAttribute("timestamp", new Date());
                return VIEW_PATH + "404";
            }
            // 500 error
            if(statusCode == HttpStatus.FORBIDDEN.value()){
                // 서버에 대한 에러이기 때문에 사용자에게 정보를 제공하지 않는다.
                return VIEW_PATH + "500";
            }
        }
        // 정의한 에러 외 모든 에러는 errors/error 페이지로 보낸다.
        return "error";
    }

    //@Override
    public String getErrorPath() {
        return null;
    }
}
