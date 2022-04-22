package org.charlie.example.common.exceptions;


import lombok.extern.slf4j.Slf4j;

/**
 * To define common exception.
 */
//@Slf4j
public class ExampleException extends Exception {
    public ExampleException(String msg) {
        super(msg);
    }
}
