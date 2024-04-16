package com.epam.rd.autotask.io.serialization;


import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class Order {
    private Long id;
    private BigDecimal total;
    private Map<Item, Integer> items;

    public Order(Long id, Map<Item, Integer> items) {
        this.id = id;
        this.items = items;
    }
    private BigDecimal calculateTotal(Map<Item, Integer> itemsSet){
        if(itemsSet == null){
            this.total = BigDecimal.ZERO;
            return this.total;
        }
        this.total = BigDecimal.ZERO;
        for (Map.Entry<Item, Integer> item: itemsSet.entrySet()) {
            total = total.add(item.getKey().getPrice().multiply(BigDecimal.valueOf(item.getValue())));
        }
        return total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        if(this.total != null) return this.total;
        return calculateTotal(items);

    }



    public Map<Item, Integer> getItems() {
        return items;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getItems(), order.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItems());
    }
}
