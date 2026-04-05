package com.csc483.assignment1.search;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages an array of products and provides search functionalities.
 */
public class ProductManager {
    private Product[] products;
    private Map<String, Product> nameIndex;

    /**
     * Initialize the ProductManager with a set of products.
     * @param initialProducts Initial array of products
     */
    /**
     * Initialize the ProductManager with a set of products.
     * @param initialProducts Initial array of products
     */
    public ProductManager(Product[] initialProducts) {
        this.products = initialProducts;
        buildNameIndex();
    }

    private void buildNameIndex() {
        nameIndex = new HashMap<>();
        if (products != null) {
            for (Product p : products) {
                if (p != null) {
                    nameIndex.put(p.getProductName(), p);
                }
            }
        }
    }

    /**
     * Retrieves the array of products.
     * @return current array of products
     */
    /**
     * Retrieves the array of products.
     * @return current array of products
     */
    public Product[] getProducts() {
        return products;
    }

    /**
     * Searches for a product by name using the HashMap index.
     * Time Complexity: O(1) average case, O(N) worst case.
     */
    public Product searchByName(String name) {
        return nameIndex.get(name);
    }

    /**
     * Adds a new product while maintaining sorted order by ID.
     * Time Complexity: O(N) because it needs to copy the array to insert at the correct position.
     * 
     * @param products The current sorted array of products.
     * @param newProduct The new product to add.
     * @return The new sorted array containing the added product.
     */
    public Product[] addProduct(Product[] products, Product newProduct) {
        if (products == null) {
            this.products = new Product[]{newProduct};
            nameIndex.put(newProduct.getProductName(), newProduct);
            return this.products;
        }

        int n = products.length;
        Product[] newProducts = new Product[n + 1];

        // Find insertion point using binary search logic for O(log N) discovery, 
        // though insertion into array still takes O(N).
        int left = 0;
        int right = n - 1;
        int insertPos = n; // Default to end if it's the largest

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (products[mid].getProductId() > newProduct.getProductId()) {
                insertPos = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // Copy elements before insertion point
        System.arraycopy(products, 0, newProducts, 0, insertPos);
        
        // Insert new product
        newProducts[insertPos] = newProduct;
        
        // Copy remaining elements
        System.arraycopy(products, insertPos, newProducts, insertPos + 1, n - insertPos);

        this.products = newProducts;
        nameIndex.put(newProduct.getProductName(), newProduct);
        
        return newProducts;
    }
}
