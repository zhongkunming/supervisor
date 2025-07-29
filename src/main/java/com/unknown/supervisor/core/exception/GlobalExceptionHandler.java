package com.unknown.supervisor.core.exception;

import com.unknown.supervisor.core.common.GlobalResultCode;
import com.unknown.supervisor.core.common.JsonResult;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author zhongkunming
 */
@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public JsonResult<Void> handlerBusinessException(BusinessException e) {
        log.error("handlerBusinessException, ", e);
        return JsonResult.buildResult(e.getResultCode(), e.getObjs());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult<Void> handlerMethodValidationException(MethodArgumentNotValidException e) {
        String message = e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("handlerMethodValidationException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_INVALID, message);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public JsonResult<Void> handlerHandlerMethodValidationException(HandlerMethodValidationException e) {
        String errorMessage = e.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("handlerHandlerMethodValidationException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_INVALID, errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public JsonResult<Void> handlerConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.error("handlerConstraintViolationException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_INVALID, errorMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JsonResult<Void> handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handlerMissingServletRequestParameterException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_INVALID, e.getParameterName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public JsonResult<Void> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handlerMethodArgumentTypeMismatchException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_INVALID, e.getName());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult<Void> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handlerHttpMessageNotReadableException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_INVALID, "请求体格式错误");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JsonResult<Void> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handlerHttpRequestMethodNotSupportedException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_INVALID, "请求方法不支持: " + e.getMethod());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public JsonResult<Void> handlerNoResourceFoundException(NoResourceFoundException e) {
        log.error("handlerNoResourceFoundException, ", e);
        return JsonResult.buildResult(GlobalResultCode.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public JsonResult<Void> handleException(Exception e) {
        log.error("handleException, ", e);
        return JsonResult.buildResult(GlobalResultCode.ERROR);
    }
}
