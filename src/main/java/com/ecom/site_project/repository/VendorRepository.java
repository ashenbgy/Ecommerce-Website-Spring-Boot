package com.ecom.site_project.repository;

import com.ecom.site_project.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    Long countById(Integer id);

    @Query("SELECT v FROM Vendor v WHERE v.title = :title")
    Vendor findByTitle(@Param("title") String title);
}
