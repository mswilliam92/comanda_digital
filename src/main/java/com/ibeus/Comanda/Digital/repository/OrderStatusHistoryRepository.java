package com.ibeus.Comanda.Digital.repository;

import com.ibeus.Comanda.Digital.model.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    List<OrderStatusHistory> findByOrderId(Long orderId);
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderStatusHistory osh WHERE osh.order.id = :orderId")
    void deleteByOrderId(Long orderId);

}
