package com.barbeshop.api.repository;

import com.barbeshop.api.model.BusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessHourRepository extends JpaRepository<BusinessHour, Integer> {
    List<BusinessHour> findByStoreSettingIdOrderByDayOfWeek(Integer storeSettingId);
}
