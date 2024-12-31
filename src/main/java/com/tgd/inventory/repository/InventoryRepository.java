package com.tgd.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tgd.inventory.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

    Optional<Inventory> findByProductId(Long productId);

}
