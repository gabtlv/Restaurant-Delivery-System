package Helper;

public class MenuItem {
    private String name;
    private String description;
    private String imagePath;
    private Double price;

    public MenuItem(String name, String description, String imagePath, double price) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
    }
    
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public Double getPrice() { return price; }
}
