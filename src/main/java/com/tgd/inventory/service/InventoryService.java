package com.tgd.inventory.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgd.inventory.entity.Inventory;
import com.tgd.inventory.repository.InventoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	// Get the available stock for a product
	public Integer getStockForProduct(Long productId) {
		Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
		return inventory.map(Inventory::getStockQuantity).orElse(0);
	}

	// Reserve stock for an order
	public boolean reserveStock(Long productId, Integer quantity) {
		Optional<Inventory> inventoryOptional = inventoryRepository.findByProductId(productId);
		if (inventoryOptional.isPresent()) {
			Inventory inventory = inventoryOptional.get();
			if (inventory.getStockQuantity() - inventory.getReservedQuantity() >= quantity) {
				inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
				inventoryRepository.save(inventory);
				return true;
			}
		}
		return false;
	}

	// Release previously reserved stock
	public boolean releaseReservedStock(Long productId, Integer quantity) {
		Optional<Inventory> inventoryOptional = inventoryRepository.findByProductId(productId);
		if (inventoryOptional.isPresent()) {
			Inventory inventory = inventoryOptional.get();
			if (inventory.getReservedQuantity() >= quantity) {
				inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
				inventoryRepository.save(inventory);
				return true;
			}
		}
		return false;
	}

	// Update inventory levels when new stock is received
	public void updateStock(Long productId, Integer quantity) {
		Optional<Inventory> inventoryOptional = inventoryRepository.findByProductId(productId);
		if (inventoryOptional.isPresent()) {
			Inventory inventory = inventoryOptional.get();
			inventory.setStockQuantity(inventory.getStockQuantity() + quantity);
			inventoryRepository.save(inventory);
		} else {
			Inventory newInventory = new Inventory();
			newInventory.setProductId(productId);
			newInventory.setStockQuantity(quantity);
			newInventory.setReservedQuantity(0);
			inventoryRepository.save(newInventory);
		}
	}
}
