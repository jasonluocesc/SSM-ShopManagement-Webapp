package com.wenbin.o2o.dto;

import com.wenbin.o2o.entity.ProductCategory;
import com.wenbin.o2o.enums.ProductCategoryStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class ProductCategoryExecution {

    private int state;

    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {

    }
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }

    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.productCategoryList=productCategoryList;
    }
}
