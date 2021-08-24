package org.charlie.example.framework.advices;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.charlie.example.framework.constants.ErrorConstants;
import org.charlie.example.framework.exceptions.TemplateException;
import org.charlie.example.framework.entities.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * To catch exceptions globally.
 *
 * @author Charlie
 */
@RestControllerAdvice(annotations=RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value={TemplateException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity templateException(TemplateException ex) {
        log.error(String.format("Application Exception: %s", ExceptionUtils.getMessage(ex)));
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value={IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity illegalArgumentException(IllegalArgumentException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity unknownException(Exception ex) {
        log.error(String.format("Unknown exception: %s", ExceptionUtils.getMessage(ex)));
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity httpMessageNotReadableException(Exception ex) {
        log.error("Transaction Message Reader Error:" + HttpStatus.INTERNAL_SERVER_ERROR, ex);
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity unknownThrowable(Throwable t) {
        log.error("Unknown error:" + HttpStatus.INTERNAL_SERVER_ERROR, t);
        return ResponseEntity.builder()
                .code(ErrorConstants.ERROR_CODE)
                .message(ErrorConstants.ERROR_MESSAGE)
                .build();
    }

}
