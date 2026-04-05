package com.csc483.assignment1.search;

/**
 * Represents a product with properties such as ID, name, category, price, and stock quantity.
 */
public class Product implements Comparable<Product> {
    private int productId;
    private String productName;
    private String category;
    private double price;
    private int stockQuantity;

    /**
     * Constructs a new Product.
     * @param productId ID of the product
     * @param productName Name of the product
     * @param category Category
     * @param price Price
     * @param stockQuantity Quantity in stock
     */
    /**
     * Constructs a new Product.
     * @param productId ID of the product
     * @param productName Name of the product
     * @param category Category
     * @param price Price
     * @param stockQuantity Quantity in stock
     */
    public Product(int productId, String productName, String category, double price, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets the product ID.
     * @return productId
     */
    /**
     * Gets the product ID.
     * @return productId
     */
    public int getProductId() { return productId; }
    /**
     * Gets the product name.
     * @return productName
     */
    /**
     * Gets the product name.
     * @return productName
     */
    public String getProductName() { return productName; }
    /**
     * Gets the product category.
     * @return category
     */
    /**
     * Gets the product category.
     * @return category
     */
    public String getCategory() { return category; }
    /**
     * Gets the product price.
     * @return price
     */
    /**
     * Gets the product price.
     * @return price
     */
    public double getPrice() { return price; }
    /**
     * Gets the stock quantity.
     * @return stockQuantity
     */
    /**
     * Gets the stock quantity.
     * @return stockQuantity
     */
    public int getStockQuantity() { return stockQuantity; }

    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.productId, other.productId);
    }
}
