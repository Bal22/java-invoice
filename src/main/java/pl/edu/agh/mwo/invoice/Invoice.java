package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();
    private static int nextNumber = 0;
    private final int number = ++nextNumber;

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product can`t be null");
        }
        if (!products.containsKey(product)) {
            addProduct(product, 1);
        } else {
            addProduct(product, products.get(product) + 1);
        }
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        products.put(product, quantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber() {
        return number;
    }

    public void printInvoice(Invoice example) {
        System.out.println("======= Invoice number: " + example.getNumber() + " =======" + "\n");
        for (Product product: example.products.keySet()) {
            BigDecimal quantity = new BigDecimal(example.products.get(product));
            BigDecimal price = product.getPriceWithTax().multiply(quantity);
            System.out.println("Product name: " + product.getName() + " , " + "Quantity: " + quantity + " , "+ "Price: " + price);
        }
        System.out.println("Number of products: " + example.products.size());
        System.out.println("Total price: " + example.getGrossTotal() + "\n");
    }

}
