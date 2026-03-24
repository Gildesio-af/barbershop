package com.barbeshop.api.repository;

import com.barbeshop.api.model.AppointmentProduct;
import com.barbeshop.api.repository.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentProductRepository extends JpaRepository<AppointmentProduct, Integer> {
    @Query(value = """
            SELECT p.id AS id,
                   p.p_name AS name,
                   p.image_url AS imageUrl,
                   app.price_at_moment AS price
            FROM appointment_products app
            JOIN product p ON app.product_id = p.id
            WHERE app.appointment_id = :appointmentId
    """, nativeQuery = true)
    List<ProductProjection> findProductsByAppointmentId(String appointmentId);
}
