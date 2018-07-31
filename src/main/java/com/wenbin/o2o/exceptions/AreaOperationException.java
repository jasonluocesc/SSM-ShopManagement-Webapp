package com.wenbin.o2o.exceptions;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class AreaOperationException extends RuntimeException {
    public AreaOperationException(String msg) {
        super(msg);
    }
}
