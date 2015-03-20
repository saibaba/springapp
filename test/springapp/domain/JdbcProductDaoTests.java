package springapp.repository;

import java.util.List;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.junit.Test;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import springapp.domain.Product;

@ContextConfiguration(locations={"classpath:test-context.xml"})
public class JdbcProductDaoTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected ProductDao productDao;
    
    @Test
    public void testGetProductList() {
        List<Product> products = productDao.getProductList();
        Assert.assertEquals("wrong number of products?", 3, products.size());
    }

    @Test 
    public void testSaveProduct() {
        List<Product> products = productDao.getProductList();
        
        for (Product p : products) {
            p.setPrice(200.12);
            productDao.saveProduct(p);
        }
        
        List<Product> updatedProducts = productDao.getProductList();
        for (Product p : updatedProducts) {
            Assert.assertEquals("wrong price of product?", 200.12, p.getPrice());
        }
    }
}
