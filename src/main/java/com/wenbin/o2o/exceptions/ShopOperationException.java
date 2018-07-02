package com.wenbin.o2o.exceptions;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class ShopOperationException extends RuntimeException {
    public ShopOperationException(String errorMsg){
        super(errorMsg);
    }
}
