package org.charlie.template.advice;


import lombok.extern.slf4j.Slf4j;
import org.charlie.template.constants.FooConstants;
import org.charlie.template.exception.FooException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.charlie.template.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    FooConstants fooConstants;

    @ExceptionHandler(value={FooException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseVO commonServiceExclkeption(FooException ex) {
        log.error(String.format("FooException: %s", ExceptionUtils.getMessage(ex)));
        log.error(ExceptionUtils.getStackTrace(ex));

        return ResponseVO.builder()
                .resultCode(fooConstants.RESULT_CODE_ERROR)
                .resultMessage("ERROR")
                .build();
    }

    @ExceptionHandler(value={IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseVO illegalArgumentException(IllegalArgumentException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseVO.builder()
                .resultCode(fooConstants.RESULT_CODE_ERROR)
                .resultMessage("ERROR")
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseVO unknownException(Exception ex) {
        log.error(String.format("Unknown exception: %s", ExceptionUtils.getMessage(ex)));
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseVO.builder()
                .resultCode(fooConstants.RESULT_CODE_ERROR)
                .resultMessage("ERROR")
                .build();
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseVO httpMessageNotReadableException(Exception ex) {
        log.info("Transaction Message Reader Error:" + HttpStatus.INTERNAL_SERVER_ERROR, ex);
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseVO.builder()
                .resultCode(fooConstants.RESULT_CODE_ERROR)
                .resultMessage("ERROR")
                .build();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseVO unknownThrowable(Throwable t) {
        log.info("Unknown error:" + HttpStatus.INTERNAL_SERVER_ERROR, t);
        return ResponseVO.builder()
                .resultCode(fooConstants.RESULT_CODE_ERROR)
                .resultMessage("ERROR")
                .build();
    }

}
