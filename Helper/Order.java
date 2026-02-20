package Helper;

public class Order {
    private String customerName;
    private double total;
    private String status;

    public Order(String customerName, double total, String status) {
        this.customerName = customerName;
        this.total = total;
        this.status = status;
    }

    public String getCustomerName() { return customerName; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
}
