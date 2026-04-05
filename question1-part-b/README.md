# Hybrid Search Strategy & Complexity Analysis

## Pseudocode for Hybrid Search Approach

### 1. Hybrid Manager Initialization
```
function initialize(Product[] initialProducts):
    productsArray = initialProducts
    nameIndex = new HashMap<String, Product>()
    
    for each product in productsArray:
        nameIndex.put(product.getName(), product)
```

### 2. Search By Name (Hybrid Strategy)
```
function searchByName(String targetName):
    // Use the HashMap for O(1) average lookup time
    return nameIndex.get(targetName)
```

### 3. Insert Product maintaining Ordered Property for Binary Search
```
function addProduct(Product[] products, Product newProduct):
    n = length(products)
    newProducts = new Array of length(n + 1)
    
    // Find the correct insertion point using Binary Search (O(log N))
    left = 0
    right = n - 1
    insertPos = n
    
    while left <= right:
        mid = left + (right - left) / 2
        if products[mid].id > newProduct.id:
            insertPos = mid
            right = mid - 1
        else:
            left = mid + 1
            
    // Shift elements taking O(N)
    copy products[0 to insertPos-1] -> newProducts[0 to insertPos-1]
    newProducts[insertPos] = newProduct
    copy products[insertPos to n-1] -> newProducts[insertPos+1 to n]
    
    this.products = newProducts
    nameIndex.put(newProduct.getName(), newProduct)
    
    return newProducts
```

## Complexity Analysis

| Operation | Best Case | Average Case | Worst Case | Space Complexity |
|-----------|-----------|--------------|------------|------------------|
| **Sequential Search (ID)** | $O(1)$ | $O(N)$ | $O(N)$ | $O(1)$ |
| **Binary Search (ID)** | $O(1)$ | $O(\log N)$ | $O(\log N)$ | $O(1)$ |
| **Hybrid Search (Name)** | $O(1)$ | $O(1)$ | $O(N)$* | $O(N)$ |
| **`addProduct` (Insert) ** | $O(N)$ | $O(N)$ | $O(N)$ | $O(N)$ |

\* *Worst case for HashMap lookup occurs strictly during hash collision collisions where all elements map to the exact same bucket (usually resolving through a linked list or $O(\log N)$ balanced tree).*

### Detailed Breakdown
*   **Hybrid Search By Name**: By maintaining a parallel `HashMap` pairing unique names as keys with `Product` object references as values, we replace the basic array's $O(N)$ sequential traversal requirement with amortized $O(1)$ lookup time.
*   **Hybrid Insertion (`addProduct`)**: 
    1. Finding the position takes $O(\log N)$ using binary search.
    2. Because ordinary arrays are fixed-length structures in Java, scaling up requires reallocating an array of length $N+1$, and copying elements. Inserting into the middle of the array mandates moving elements which forces an overall $O(N)$ upper bound.
    3. The addition into the Hash table map incurs an amortized $O(1)$ overhead.
    *Total insertion complexity*: $O(\log N) + O(N) + O(1) \rightarrow \boldsymbol{O(N)}$