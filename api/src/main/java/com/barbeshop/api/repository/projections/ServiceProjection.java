package com.barbeshop.api.repository.projections;

import com.barbeshop.api.model.enums.ServiceType;

public interface ServiceProjection {
    String getId();
    String getName();
    String getImageUrl();
    Double getPrice();
    ServiceType getType();
}
