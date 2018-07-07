package com.wenbin.o2o.dto;

import lombok.Data;

import java.io.InputStream;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class ImageHolder {

    private String imageName;
    private InputStream image;

    public ImageHolder(String imageName,InputStream image){
        this.image=image;
        this.imageName=imageName;
    }
}
