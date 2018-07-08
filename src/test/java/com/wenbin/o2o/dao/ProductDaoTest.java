package com.wenbin.o2o.dao;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.entity.Product;
import com.wenbin.o2o.entity.ProductCategory;
import com.wenbin.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;
    @Test
    public void testUpdateProduct(){
        Product product = new Product();
        ProductCategory pc = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(1L);
        pc.setProductCategoryId(2L);
        product.setShop(shop);
        product.setProductId(1L);
        product.setProductName("第一个产品");
        product.setProductCategory(pc);
        int effectNum = productDao.updateProduct(product);
        assertEquals(1,effectNum);
    }
}