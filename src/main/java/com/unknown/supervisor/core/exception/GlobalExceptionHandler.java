package com.unknown.supervisor.core.exception;

import com.unknown.supervisor.core.common.GlobalResultCode;
import com.unknown.supervisor.core.common.JsonResult;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
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
        return JsonResult.buildResult(GlobalResultCode.PARAM_VERIFICATION_FAILED, message);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public JsonResult<Void> handlerHandlerMethodValidationException(HandlerMethodValidationException e) {
        String errorMessage = e.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("handlerHandlerMethodValidationException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_VERIFICATION_FAILED, errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public JsonResult<Void> handlerConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.error("handlerConstraintViolationException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_VERIFICATION_FAILED, errorMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JsonResult<Void> handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handlerMissingServletRequestParameterException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_VERIFICATION_FAILED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public JsonResult<Void> handlerNoResourceFoundException(NoResourceFoundException e) {
        log.error("handlerNoResourceFoundException, ", e);
        return JsonResult.buildResult(GlobalResultCode.PARAM_VERIFICATION_FAILED);
    }

    @ExceptionHandler(Exception.class)
    public JsonResult<Void> handleException(Exception e) {
        log.error("handleException, ", e);
        return JsonResult.buildResult(GlobalResultCode.ERROR);
    }
}
