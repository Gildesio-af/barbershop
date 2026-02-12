package com.barbeshop.api.repository;

import com.barbeshop.api.model.StoreSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreSettingRepository extends JpaRepository<StoreSetting, Integer> {
}
