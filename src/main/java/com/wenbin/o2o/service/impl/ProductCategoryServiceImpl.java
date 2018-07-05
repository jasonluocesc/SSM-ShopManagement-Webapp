package com.wenbin.o2o.service.impl;

import com.wenbin.o2o.dao.ProductCategoryDao;
import com.wenbin.o2o.dto.ProductCategoryExecution;
import com.wenbin.o2o.dto.ShopExecution;
import com.wenbin.o2o.entity.ProductCategory;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.enums.ProductCategoryStateEnum;
import com.wenbin.o2o.exceptions.ProductCategoryOperationException;
import com.wenbin.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;


    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if(productCategoryList!=null&&productCategoryList.size()>0){
            try{
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectNum<=0){
                    throw new ProductCategoryOperationException("创建店铺类别失败");
                }else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchAddProductCategory error: "+e.getMessage());
            }
        }else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //TODO将此商品类别下的商品类别id置为空
        try{
            int effectNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if(effectNum<=0){
                throw new ProductCategoryOperationException("商品类别删除失败");
            }else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductCategoryOperationException("deleteProductCategory error: "+e.getMessage());
        }
    }

}
