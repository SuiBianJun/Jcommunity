package com.dlg.community.error;

import com.alibaba.fastjson.JSON;
import com.dlg.community.dto.ErrorVO;
import com.dlg.community.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/*
*
* 处理controller 抛出的异常
*
* */

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request,
                                           HttpServletResponse response, Throwable ex, Model m) {

        if("application/json".equals(request.getContentType())){
            // json请求,返回json数据
            MyException exception = (MyException) ex;
            logger.info("exception: " + ErrorVO.errorOf(exception));
            //response.setCharacterEncoding("utf-8");
            response.setHeader("content-type", "text/html;charset=utf-8");
            try {

                PrintWriter pw = response.getWriter();
                pw.write(JSON.toJSONString(ErrorVO.errorOf(exception)));
                pw.flush();
                //OutputStream os = response.getOutputStream();
                //os.write(JSON.toJSONString(ErrorVO.errorOf(exception)).getBytes());
                //os.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                return null;
            }
        }else{// 返回页面

            logger.info("erro handler");
            HttpStatus status = getStatus(request);
            m.addAttribute("msg", ex.getMessage());

            return new ModelAndView("error");
        }

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
