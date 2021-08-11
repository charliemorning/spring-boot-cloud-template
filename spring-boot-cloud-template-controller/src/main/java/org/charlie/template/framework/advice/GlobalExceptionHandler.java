package org.charlie.template.framework.advice;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.charlie.template.common.constants.ErrorConstants;
import org.charlie.template.common.exceptions.TemplateException;
import org.charlie.template.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangchen
 * @version 0.1.0
 */
@RestControllerAdvice(annotations=RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value={TemplateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response templateException(TemplateException ex) {
        log.error(String.format("TemplateException: %s", ExceptionUtils.getMessage(ex)));
        log.error(ExceptionUtils.getStackTrace(ex));
        return Response.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value={IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response illegalArgumentException(IllegalArgumentException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return Response.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response unknownException(Exception ex) {
        log.error(String.format("Unknown exception: %s", ExceptionUtils.getMessage(ex)));
        log.error(ExceptionUtils.getStackTrace(ex));
        return Response.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response httpMessageNotReadableException(Exception ex) {
        log.info("Transaction Message Reader Error:" + HttpStatus.INTERNAL_SERVER_ERROR, ex);
        log.error(ExceptionUtils.getStackTrace(ex));
        return Response.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response unknownThrowable(Throwable t) {
        log.info("Unknown error:" + HttpStatus.INTERNAL_SERVER_ERROR, t);
        return Response.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

}
