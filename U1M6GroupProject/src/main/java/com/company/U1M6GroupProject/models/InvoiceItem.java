package com.company.U1M6GroupProject.models;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItem {
    private int invoiceItemId;
    private int invoiceId;
    private int itemId;
    private int quantity;
    private BigDecimal unitRate;
    private BigDecimal discount;

    public int getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(int invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(BigDecimal unitRate) {
        this.unitRate = unitRate;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return invoiceItemId == that.invoiceItemId && invoiceId == that.invoiceId && itemId == that.itemId && quantity == that.quantity && Objects.equals(unitRate, that.unitRate) && Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceItemId, invoiceId, itemId, quantity, unitRate, discount);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "invoiceItemId=" + invoiceItemId +
                ", invoiceId=" + invoiceId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", unitRate=" + unitRate +
                ", discount=" + discount +
                '}';
    }
}
