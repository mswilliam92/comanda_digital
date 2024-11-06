package com.ibeus.Comanda.Digital.repository;

import com.ibeus.Comanda.Digital.model.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
}

