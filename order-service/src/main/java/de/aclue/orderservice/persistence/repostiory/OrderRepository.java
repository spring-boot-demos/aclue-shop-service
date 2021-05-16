package de.aclue.orderservice.persistence.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;

import de.aclue.orderservice.persistence.entity.OrderEntity;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
