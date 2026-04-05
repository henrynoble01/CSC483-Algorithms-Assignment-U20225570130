# Assignment 1: Search Algorithms

This project contains an implementation of a basic `ProductManager` with search capabilities, utilizing Sequential Search and Binary Search to find products by ID and Name.

## Compilation Instructions
```bash
# From the root directory (containing com/csc483/...)
javac com.csc483.assignment1.search/*.java
```

## Execution Instructions
```bash
# Run the main benchmark program
java com.csc483.assignment1.search.Main
```

## Dependencies
- Java 8 or higher
- JUnit 4 (for testing)

## Sample Usage
```java
Product[] products = { new Product(1, "Laptop", "Tech", 1000.0, 5) };
ProductManager pm = new ProductManager(products);
Product found = pm.searchByName("Laptop");
```

## Known Limitations
- The `addProduct` method takes $O(N)$ time despite binary search discovery, as shifting arrays requires $O(N)$ operations.
- Doesn't support concurrent access without external synchronized blocks.

## Testing and Coverage
To test, compile with JUnit:
```bash
javac -cp .:junit.jar com.csc483.assignment1.search/*.java
java -cp .:junit.jar:hamcrest-core.jar org.junit.runner.JUnitCore com.csc483.assignment1.search.ProductManagerTest
```
Generate coverage report via your IDE (like Jacoco in IntelliJ/Eclipse).
