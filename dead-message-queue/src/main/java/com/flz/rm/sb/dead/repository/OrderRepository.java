package com.flz.rm.sb.dead.repository;

import com.flz.rm.sb.shared.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
