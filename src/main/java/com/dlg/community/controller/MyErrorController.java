package com.dlg.community.controller;

import com.dlg.community.error.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*
*
* 处理未被捕获的controller 异常
*
* */

@Controller
@RequestMapping("/error")
public class MyErrorController  implements ErrorController {

    Logger logger = LoggerFactory.getLogger(MyErrorController.class);

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable ex, Model m) {

        logger.info("erro controller");
        HttpStatus status = getStatus(request);
        m.addAttribute("msg", "访问的网页不存在");

        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
