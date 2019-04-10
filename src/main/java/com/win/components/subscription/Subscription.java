package com.win.components.subscription;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "amount")
    private double amount;

    @Column (name = "paid_amount")
    private double paidAmount;

    @Column (name = "mpesa_receipt")
    private String mpesaReceipt;

    @Column (name="payment_date")
    private Date paymentDate;

    @Column (name = "payment_status")
    private String paymentStatus;

    @Column (name = "response_code")
    private String responseCode;

    @Column (name = "response_desc")
    private String responseDesc;

    @Column (name = "merchant_request_id")
    private String merchantRequestID;

    @Column (name = "checkout_request_id")
    private String checkoutRequestID;
}
