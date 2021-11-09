package com.universign.universigncs.billing.domain;

import com.universign.universigncs.billing.domain.enumeration.InvoiceType;
import com.universign.universigncs.billing.domain.enumeration.Status;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Invoice.
 */
@Document(collection = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("human_id")
    private String humanId;

    @Field("type")
    private InvoiceType type;

    @Field("customer_id")
    private String customerId;

    @Field("customer_name")
    private String customerName;

    @Field("month")
    private Integer month;

    @Field("year")
    private Integer year;

    @Field("emission_date")
    private LocalDate emissionDate;

    @Field("items")
    private String items;

    @Field("vat")
    private Double vat;

    @Field("due_date")
    private LocalDate dueDate;

    @Field("date_to_send")
    private LocalDate dateToSend;

    @Field("send_date")
    private LocalDate sendDate;

    @Field("dunning_send_date")
    private LocalDate dunningSendDate;

    @Field("credit_note_send_date")
    private LocalDate creditNoteSendDate;

    @Field("status")
    private Status status;

    @Field("comments")
    private String comments;

    @Field("pvalidation_date")
    private LocalDate pvalidationDate;

    @Field("validator_id")
    private String validatorId;

    @Field("payment")
    private String payment;

    @Field("payments_historic")
    private String paymentsHistoric;

    @Field("payment_method")
    private String paymentMethod;

    @Field("refund")
    private String refund;

    @Field("purchase_order")
    private String purchaseOrder;

    @Field("message")
    private String message;

    @Field("additional_items")
    private String additionalItems;

    @Field("total_price_gross")
    private Double totalPriceGross;

    @Field("refund_amount")
    private Integer refundAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Invoice id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHumanId() {
        return this.humanId;
    }

    public Invoice humanId(String humanId) {
        this.setHumanId(humanId);
        return this;
    }

    public void setHumanId(String humanId) {
        this.humanId = humanId;
    }

    public InvoiceType getType() {
        return this.type;
    }

    public Invoice type(InvoiceType type) {
        this.setType(type);
        return this;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Invoice customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Invoice customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getMonth() {
        return this.month;
    }

    public Invoice month(Integer month) {
        this.setMonth(month);
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return this.year;
    }

    public Invoice year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getEmissionDate() {
        return this.emissionDate;
    }

    public Invoice emissionDate(LocalDate emissionDate) {
        this.setEmissionDate(emissionDate);
        return this;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getItems() {
        return this.items;
    }

    public Invoice items(String items) {
        this.setItems(items);
        return this;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Double getVat() {
        return this.vat;
    }

    public Invoice vat(Double vat) {
        this.setVat(vat);
        return this;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public Invoice dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDateToSend() {
        return this.dateToSend;
    }

    public Invoice dateToSend(LocalDate dateToSend) {
        this.setDateToSend(dateToSend);
        return this;
    }

    public void setDateToSend(LocalDate dateToSend) {
        this.dateToSend = dateToSend;
    }

    public LocalDate getSendDate() {
        return this.sendDate;
    }

    public Invoice sendDate(LocalDate sendDate) {
        this.setSendDate(sendDate);
        return this;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    public LocalDate getDunningSendDate() {
        return this.dunningSendDate;
    }

    public Invoice dunningSendDate(LocalDate dunningSendDate) {
        this.setDunningSendDate(dunningSendDate);
        return this;
    }

    public void setDunningSendDate(LocalDate dunningSendDate) {
        this.dunningSendDate = dunningSendDate;
    }

    public LocalDate getCreditNoteSendDate() {
        return this.creditNoteSendDate;
    }

    public Invoice creditNoteSendDate(LocalDate creditNoteSendDate) {
        this.setCreditNoteSendDate(creditNoteSendDate);
        return this;
    }

    public void setCreditNoteSendDate(LocalDate creditNoteSendDate) {
        this.creditNoteSendDate = creditNoteSendDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public Invoice status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComments() {
        return this.comments;
    }

    public Invoice comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getPvalidationDate() {
        return this.pvalidationDate;
    }

    public Invoice pvalidationDate(LocalDate pvalidationDate) {
        this.setPvalidationDate(pvalidationDate);
        return this;
    }

    public void setPvalidationDate(LocalDate pvalidationDate) {
        this.pvalidationDate = pvalidationDate;
    }

    public String getValidatorId() {
        return this.validatorId;
    }

    public Invoice validatorId(String validatorId) {
        this.setValidatorId(validatorId);
        return this;
    }

    public void setValidatorId(String validatorId) {
        this.validatorId = validatorId;
    }

    public String getPayment() {
        return this.payment;
    }

    public Invoice payment(String payment) {
        this.setPayment(payment);
        return this;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPaymentsHistoric() {
        return this.paymentsHistoric;
    }

    public Invoice paymentsHistoric(String paymentsHistoric) {
        this.setPaymentsHistoric(paymentsHistoric);
        return this;
    }

    public void setPaymentsHistoric(String paymentsHistoric) {
        this.paymentsHistoric = paymentsHistoric;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public Invoice paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRefund() {
        return this.refund;
    }

    public Invoice refund(String refund) {
        this.setRefund(refund);
        return this;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getPurchaseOrder() {
        return this.purchaseOrder;
    }

    public Invoice purchaseOrder(String purchaseOrder) {
        this.setPurchaseOrder(purchaseOrder);
        return this;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getMessage() {
        return this.message;
    }

    public Invoice message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdditionalItems() {
        return this.additionalItems;
    }

    public Invoice additionalItems(String additionalItems) {
        this.setAdditionalItems(additionalItems);
        return this;
    }

    public void setAdditionalItems(String additionalItems) {
        this.additionalItems = additionalItems;
    }

    public Double getTotalPriceGross() {
        return this.totalPriceGross;
    }

    public Invoice totalPriceGross(Double totalPriceGross) {
        this.setTotalPriceGross(totalPriceGross);
        return this;
    }

    public void setTotalPriceGross(Double totalPriceGross) {
        this.totalPriceGross = totalPriceGross;
    }

    public Integer getRefundAmount() {
        return this.refundAmount;
    }

    public Invoice refundAmount(Integer refundAmount) {
        this.setRefundAmount(refundAmount);
        return this;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", humanId='" + getHumanId() + "'" +
            ", type='" + getType() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", month=" + getMonth() +
            ", year=" + getYear() +
            ", emissionDate='" + getEmissionDate() + "'" +
            ", items='" + getItems() + "'" +
            ", vat=" + getVat() +
            ", dueDate='" + getDueDate() + "'" +
            ", dateToSend='" + getDateToSend() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", dunningSendDate='" + getDunningSendDate() + "'" +
            ", creditNoteSendDate='" + getCreditNoteSendDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", comments='" + getComments() + "'" +
            ", pvalidationDate='" + getPvalidationDate() + "'" +
            ", validatorId='" + getValidatorId() + "'" +
            ", payment='" + getPayment() + "'" +
            ", paymentsHistoric='" + getPaymentsHistoric() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", refund='" + getRefund() + "'" +
            ", purchaseOrder='" + getPurchaseOrder() + "'" +
            ", message='" + getMessage() + "'" +
            ", additionalItems='" + getAdditionalItems() + "'" +
            ", totalPriceGross=" + getTotalPriceGross() +
            ", refundAmount=" + getRefundAmount() +
            "}";
    }
}
