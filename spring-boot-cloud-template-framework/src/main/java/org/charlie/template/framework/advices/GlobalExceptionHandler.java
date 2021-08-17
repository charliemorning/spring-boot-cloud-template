package org.charlie.template.framework.advices;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.charlie.template.framework.constants.ErrorConstants;
import org.charlie.template.framework.exceptions.TemplateException;
import org.charlie.template.framework.entities.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangchen
 */
@RestControllerAdvice(annotations=RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value={TemplateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity templateException(TemplateException ex) {
        log.error(String.format("TemplateException: %s", ExceptionUtils.getMessage(ex)));
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value={IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity illegalArgumentException(IllegalArgumentException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity unknownException(Exception ex) {
        log.error(String.format("Unknown exception: %s", ExceptionUtils.getMessage(ex)));
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity httpMessageNotReadableException(Exception ex) {
        log.info("Transaction Message Reader Error:" + HttpStatus.INTERNAL_SERVER_ERROR, ex);
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity unknownThrowable(Throwable t) {
        log.info("Unknown error:" + HttpStatus.INTERNAL_SERVER_ERROR, t);
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

}
