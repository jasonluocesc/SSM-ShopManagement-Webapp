package com.wenbin.o2o.dao;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.entity.Product;
import com.wenbin.o2o.entity.ProductCategory;
import com.wenbin.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    @Test
    public void testQueryProductList() throws Exception{
        Product productCondition = new Product();
        List<Product> productList = productDao.queryProductList(productCondition,0,3);
        assertEquals(3,productList.size());

        int count = productDao.queryProductCount(productCondition);
        assertEquals(4,count);

        productCondition.setProductName("摩卡咖啡");
        productList=productDao.queryProductList(productCondition,0,3);
        assertEquals(1,productList.size());
        count = productDao.queryProductCount((productCondition));
        assertEquals(1,count);
    }

    @Test
    public void testUpdateProductCategoryToNull()throws Exception{
        int effectNum = productDao.updateProductCategoryToNull(2L);
        System.out.println(effectNum);
    }
}