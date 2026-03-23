package com.barbeshop.api.repository;

import com.barbeshop.api.model.StoreSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreSettingRepository extends JpaRepository<StoreSetting, Integer> {
    Optional<StoreSetting> findTopByOrderByIdAsc();

    @Query("SELECT EXISTS(SELECT 1 FROM StoreSetting s WHERE s.id = :storeSettingsId AND s.isAppointmentsWeekly = true)")
    boolean isWeeklyLimitEnabled(@Param("storeSettingsId") Integer storeSettingsId);
}