package com.wenbin.o2o.dao;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.BackingStoreException;

import static org.junit.Assert.*;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void queryProductCategory() {
        long shopId =1;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("There are "+productCategoryList.size()+" category ");
    }
    @Test
    public void testBatchInsertProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("Batch test1");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1L);
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("Batch test2");
        productCategory1.setPriority(2);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(2L);
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory1);
        int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        System.out.println("Total effect number of lines are:"+effectNum);
    }

    @Test
    public void testDeleteProductCategory() throws Exception{
        long shopId =1;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for(ProductCategory pc:productCategoryList){
            if("asda".equals(pc.getProductCategoryName())){
                int effectNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(),shopId);
                assertEquals(1,effectNum);
            }
        }
    }
}