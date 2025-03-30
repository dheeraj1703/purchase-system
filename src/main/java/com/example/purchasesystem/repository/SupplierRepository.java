package com.example.purchasesystem.repository;

import com.example.purchasesystem.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> { }

