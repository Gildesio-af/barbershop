package com.barbeshop.api.model.enums;

public enum ServiceType {
    HAIR("hair"),
    BEARD("beard"),
    EYEBROW("eyebrow"),
    OTHERS("others");

    final String name;

    ServiceType(String name) {
        this.name = name;
    }

    public static ServiceType toEnum(String name) {
        for (ServiceType type : ServiceType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid service type: " + name);
    }
}
