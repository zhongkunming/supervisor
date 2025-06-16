package com.unknown.supervisor.framework.exception;

import com.unknown.supervisor.common.JsonResult;
import com.unknown.supervisor.common.ResultCodeBusiness;
import com.unknown.supervisor.common.ResultCodeSystem;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        log.error("ExceptionHandler.handlerBusinessException, ", e);
        return JsonResult.buildResult(e.getResultCode(), e.getObjs());
    }

    @ExceptionHandler(SystemException.class)
    public JsonResult<Void> handlerSystemException(SystemException e) {
        log.error("ExceptionHandler.handlerSystemException, ", e);
        return JsonResult.buildResult(e.getResultCode(), e.getObjs());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult<Void> handlerMethodValidationException(MethodArgumentNotValidException e) {
        String message = e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数校验未通过: {}", message);
        return JsonResult.buildResult(ResultCodeBusiness.PARAM_VALID_FAIL, message);
    }

//    @ExceptionHandler(HandlerMethodValidationException.class)
//    public Result<Void> handlerHandlerMethodValidationException(HandlerMethodValidationException e) {
//        String errorMessage = e.getAllValidationResults()
//                .stream()
//                .flatMap(result -> result.getResolvableErrors().stream())
//                .map(MessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.joining(","));
//        log.error("ExceptionHandler.handlerHandlerMethodValidationException, ", e);
//        return Result.error(GlobalResultCode.HANDLER_METHOD_VALID_NOT_PASS, errorMessage);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public Result<Void> handlerConstraintViolationException(ConstraintViolationException e) {
//        String errorMessage = e.getConstraintViolations()
//                .stream()
//                .map(ConstraintViolation::getMessage)
//                .collect(Collectors.joining(","));
//        log.error("ExceptionHandler.handlerConstraintViolationException, ", e);
//        return Result.error(GlobalResultCode.CONSTRAINT_VALID_NOT_PASS, errorMessage);
//    }
//
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public Result<Void> handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
//        log.error("ExceptionHandler.handlerMissingServletRequestParameterException, ", e);
//        return Result.error(GlobalResultCode.MISS_PARAMETER_VALID_NOT_PASS);
//    }
//
//    @ExceptionHandler(NoResourceFoundException.class)
//    public Result<Void> handlerNoResourceFoundException(NoResourceFoundException e) {
//        log.error("ExceptionHandler.handlerNoResourceFoundException, ", e);
//        return Result.error(GlobalResultCode.RESOURCE_NOT_FOUND_ERROR);
//    }

    @ExceptionHandler(Exception.class)
    public JsonResult<Void> handleException(Exception e) {
        log.error("ExceptionHandler.handleException, ", e);
        return JsonResult.buildResult(ResultCodeSystem.SYS_ERROR);
    }
}
