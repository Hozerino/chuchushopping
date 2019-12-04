package ws.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @JsonProperty
    private String name;

    @JsonProperty
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
