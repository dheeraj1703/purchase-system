package com.example.purchasesystem.service;

import com.example.purchasesystem.entity.Purchase;
import com.example.purchasesystem.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}

