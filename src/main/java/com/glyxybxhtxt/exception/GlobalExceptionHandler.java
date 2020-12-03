package com.glyxybxhtxt.exception;

import com.glyxybxhtxt.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Author:wangzh
 * Date: 2020/11/1 14:54
 * Version: 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResponseData exceptionHandler(Exception e){
        logger.error("未知异常！原因是:",e);
        return new ResponseData("1");
    }


}
