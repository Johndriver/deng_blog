package com.just.advice;

import com.alibaba.fastjson.JSON;
import com.just.dto.ResultDTO;
import com.just.exception.CustomizeErrorCode;
import com.just.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice()
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(HttpServletRequest request, HttpServletResponse response, Throwable e, Model model) {

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回Json
            ResultDTO resultDTO = null;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));

                writer.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;

        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", "服务冒烟了");
            }
            return new ModelAndView("error");
        }
    }


    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
