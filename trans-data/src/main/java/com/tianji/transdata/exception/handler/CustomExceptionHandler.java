package com.tianji.transdata.exception.handler;


import com.tianji.transdata.exception.BussinessException;
import com.tianji.transdata.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @description: CustomExceptionHandler
 * @author: xionglin
 * @create: 2021-09-01 10:15
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = BussinessException.class)
    public Result exceptionHandler(BussinessException bussinessException) {
        log.error(getTrace(bussinessException));
        return Result.fail(bussinessException.getMessage(), null);
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception exception) {
        if (exception instanceof HttpMessageNotReadableException) {
            log.error("参数不正确：{} ", exception.getMessage());
            return Result.fail("json格式不正确", null);
        }
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            log.error("请求方式错误：{} ", exception.getMessage());
            return Result.fail("请求方式错误", "");
        }
        log.error(getTrace(exception));
        return Result.fail("系统内部错误", null);
    }
    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
