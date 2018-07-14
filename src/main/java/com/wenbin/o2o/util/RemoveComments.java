package com.wenbin.o2o.util;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class RemoveComments {
    public static void main(String[] args){
        String sourcecode = "";
        System.out.println(sourcecode.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)",""));
    }
}
