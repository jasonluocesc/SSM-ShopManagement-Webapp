package com.wenbin.o2o.dao;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.entity.Product;
import com.wenbin.o2o.entity.ProductCategory;
import com.wenbin.o2o.entity.ProductImg;
import com.wenbin.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Autowired
    private ProductDao productDao;


    @Test
    public void batchInsertProductImg() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("test pic 1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);


        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("test pic 2");
        productImg2.setPriority(2);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);

        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectNum);
    }

    @Test
    public void testInsertProduct() throws Exception{
        Shop shop1 = new Shop();
        shop1.setShopId(10L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(2L);
        Product product1 = new Product();
        product1.setProductName("Test111");
        product1.setProductDesc("test");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);

        int effectNum = productDao.insertProduct(product1);
    }

    @Test
    public void testDeleteProductImgByProductId() throws Exception{
        long productId = 1;
        int effectNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effectNum);
    }

}