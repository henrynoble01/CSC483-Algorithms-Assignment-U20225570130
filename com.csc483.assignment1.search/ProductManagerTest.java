package com.csc483.assignment1.search;

import org.junit.Assert;
import org.junit.Test;

/**
 * public class 
 */
public class ProductManagerTest {

    @Test
    public void testAddProductMaintainsSortedOrder() {
        Product[] initialProducts = {
            new Product(1, "ProductA", "Cat1", 10.0, 5),
            new Product(3, "ProductC", "Cat1", 15.0, 2),
            new Product(5, "ProductE", "Cat2", 20.0, 10)
        };
        
        ProductManager manager = new ProductManager(initialProducts);
        
        Product newProduct = new Product(4, "ProductD", "Cat2", 18.0, 3);
        Product[] updatedProducts = manager.addProduct(initialProducts, newProduct);
        
        Assert.assertEquals(4, updatedProducts.length);
        Assert.assertEquals(1, updatedProducts[0].getProductId());
        Assert.assertEquals(3, updatedProducts[1].getProductId());
        Assert.assertEquals(4, updatedProducts[2].getProductId());
        Assert.assertEquals(5, updatedProducts[3].getProductId());
    }

    @Test
    public void testSearchByName() {
        Product p1 = new Product(10, "Laptop", "Electronics", 999.99, 50);
        Product p2 = new Product(20, "Mouse", "Accessories", 25.50, 200);
        
        Product[] products = { p1, p2 };
        ProductManager manager = new ProductManager(products);
        
        Product found = manager.searchByName("Mouse");
        Assert.assertNotNull(found);
        Assert.assertEquals(20, found.getProductId());
        
        Product notFound = manager.searchByName("Keyboard");
        Assert.assertNull(notFound);
    }

    @Test
    public void testAddProductUpdatesNameIndex() {
        ProductManager manager = new ProductManager(new Product[0]);
        Product newProduct = new Product(100, "Monitor", "Electronics", 300.0, 15);
        
        manager.addProduct(manager.getProducts(), newProduct);
        
        Product found = manager.searchByName("Monitor");
        Assert.assertNotNull(found);
        Assert.assertEquals(100, found.getProductId());
    }

    @Test
    public void testEmptyArray() {
        ProductManager manager = new ProductManager(new Product[0]);
        Product p = manager.searchByName("Ghost");
        Assert.assertNull(p);
    }

    @Test
    public void testNullInputs() {
        ProductManager manager = new ProductManager(null);
        Product newProduct = new Product(99, null, null, 1.0, 1);
        Product[] result = manager.addProduct(null, newProduct);
        Assert.assertEquals(1, result.length);
        Assert.assertNull(manager.searchByName(null));
    }
    
    @Test
    public void testDuplicates() {
        Product p1 = new Product(1, "A", "C", 1.0, 1);
        Product p2 = new Product(1, "A", "C", 1.0, 1);
        ProductManager manager = new ProductManager(new Product[]{p1});
        manager.addProduct(manager.getProducts(), p2);
        Assert.assertEquals(2, manager.getProducts().length);
    }


