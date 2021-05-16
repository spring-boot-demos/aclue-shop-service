package de.aclue.orderservice.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@Entity
@Table(name = "ORDERS")
@Data
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id; 
	
	@Column(name = "ARTICLE_ID")
	private Long article;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.CREATED;
	
}
