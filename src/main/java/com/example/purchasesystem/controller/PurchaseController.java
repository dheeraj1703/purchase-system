package com.example.purchasesystem.controller;

import com.example.purchasesystem.entity.Purchase;
import com.example.purchasesystem.entity.Product;
import com.example.purchasesystem.entity.Supplier;
import com.example.purchasesystem.repository.ProductRepository;
import com.example.purchasesystem.repository.PurchaseRepository;
import com.example.purchasesystem.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PurchaseController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping("/purchase-form")
    public String showForm(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("purchase", new Purchase());
        return "purchase-form";
    }

    @PostMapping("/save-purchase")
    public String savePurchase(@RequestParam Long productId, 
                               @RequestParam Long supplierId, 
                               @RequestParam int quantity, 
                               Model model) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<Supplier> supplierOpt = supplierRepository.findById(supplierId);

        if (productOpt.isEmpty() || supplierOpt.isEmpty()) {
            model.addAttribute("error", "Invalid Product or Supplier ID!");
            return "purchase-form";
        }

        Product product = productOpt.get();
        Supplier supplier = supplierOpt.get();
        double totalPrice = product.getPrice() * quantity; 


        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setSupplier(supplier);
        purchase.setQuantity(quantity);
        purchase.setTotalPrice(totalPrice);
        purchase.setPurchaseDate(LocalDate.now()); 

        purchaseRepository.save(purchase);
        return "redirect:/report";
    }

    @GetMapping("/report")
    public String showReport(Model model) {
        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("purchases", purchases);
        return "report"; 
    }
}
