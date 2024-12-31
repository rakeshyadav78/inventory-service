package com.tgd.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgd.inventory.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/inventory")
public class InventoryController {

	 @Autowired
	    private InventoryService inventoryService;

	    // Get the available stock for a product
	    @GetMapping("/{productId}")
	    public ResponseEntity<Integer> getStockForProduct(@PathVariable Long productId) {
	        Integer stock = inventoryService.getStockForProduct(productId);
	        return ResponseEntity.ok(stock);
	    }

	    // Reserve stock for an order
	    @PostMapping("/reserve")
	    public ResponseEntity<String> reserveInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
	        boolean reserved = inventoryService.reserveStock(productId, quantity);
	        if (reserved) {
	            return ResponseEntity.ok("Stock reserved successfully.");
	        }
	        return ResponseEntity.status(400).body("Not enough stock available.");
	    }

	    // Release previously reserved stock
	    @PostMapping("/release")
	    public ResponseEntity<String> releaseInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
	        boolean released = inventoryService.releaseReservedStock(productId, quantity);
	        if (released) {
	            return ResponseEntity.ok("Stock released successfully.");
	        }
	        return ResponseEntity.status(400).body("Error releasing stock.");
	    }
}
