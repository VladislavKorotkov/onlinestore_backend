package bsuir.korotkov.onlinestore.dto;

public class CartDTOResponse {
    private int id;
    private int appliance_id;
    private String img;
    private String name;
    private int count;
    private int available_count;

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAvailable_count() {
        return available_count;
    }

    public void setAvailable_count(int available_count) {
        this.available_count = available_count;
    }

    public CartDTOResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppliance_id() {
        return appliance_id;
    }

    public void setAppliance_id(int appliance_id) {
        this.appliance_id = appliance_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CartDTOResponse(int id, int appliance_id, String img, String name, int count, int available_count, int price) {
        this.id = id;
        this.appliance_id = appliance_id;
        this.img = img;
        this.name = name;
        this.count = count;
        this.available_count = available_count;
        this.price = price;
    }
}
