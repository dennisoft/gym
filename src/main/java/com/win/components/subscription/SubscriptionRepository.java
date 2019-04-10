package com.win.components.subscription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List <Subscription> findAll();
    Subscription findByUserID(Integer ID);
    Subscription findAllByUserID(Integer ID);
    Subscription findSubscriptionsByUserID(Integer ID);
    List<Subscription> findSubscriptionByUserID(Integer ID);
    Subscription findSubscriptionByMpesaReceipt(String mpesaReceipt);
    Subscription findSubscriptionByMerchantRequestID(String ID);
}