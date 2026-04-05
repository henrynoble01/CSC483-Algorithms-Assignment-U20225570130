public class Product implements Comparable<Product> {
    private int productId;
    private String productName;
    private String category;
    private double price;
    private int stockQuantity;

    public Product(int productId, String productName, String category, double price, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.productId, other.productId);
    }
}
