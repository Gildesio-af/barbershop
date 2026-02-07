package com.barbeshop.api.repository;

import com.barbeshop.api.model.ServiceItem;
import com.barbeshop.api.model.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceItemRepository extends JpaRepository<ServiceItem,String> {
    Page<ServiceItem> findAllByType(ServiceType serviceType, Pageable pageable);

    @Modifying
    @Query("UPDATE ServiceItem s SET s.isDeleted = true WHERE s.id = :id AND s.isDeleted = false")
    int softDeleteById(String id);
}
