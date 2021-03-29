package com.company.U1M6GroupProject.viewModels;

import com.company.U1M6GroupProject.models.Invoice;
import com.company.U1M6GroupProject.models.Item;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItemViewModel {
    private int invoiceItemId;
    private Invoice invoice;
    private Item item;
    private int quantity;
    private BigDecimal unitRate;
    private BigDecimal discount;

    public int getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(int invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
        InvoiceItemViewModel that = (InvoiceItemViewModel) o;
        return invoiceItemId == that.invoiceItemId && quantity == that.quantity && Objects.equals(invoice, that.invoice) && Objects.equals(item, that.item) && Objects.equals(unitRate, that.unitRate) && Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceItemId, invoice, item, quantity, unitRate, discount);
    }

    @Override
    public String toString() {
        return "InvoiceItemViewModel{" +
                "invoiceItemId=" + invoiceItemId +
                ", invoice=" + invoice +
                ", item=" + item +
                ", quantity=" + quantity +
                ", unitRate=" + unitRate +
                ", discount=" + discount +
                '}';
    }
}
