package com.tgd.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory")
public class Inventory {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false, unique = true)
    private Long id;

	@Column(name = "product_id",nullable = false)
    private Long productId;
	
	@Column(name = "stock_quantity",nullable = false)
    private Integer stockQuantity;
	
	@Column(name = "reserved_quantity",nullable = false)
    private Integer reservedQuantity;
}
