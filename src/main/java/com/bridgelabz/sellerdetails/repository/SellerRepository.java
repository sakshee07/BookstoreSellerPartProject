package com.bridgelabz.sellerdetails.repository;

import com.bridgelabz.sellerdetails.model.SellerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<SellerDetails, Long> {
    @Query(value = "SELECT * FROM seller_details WHERE email_address=:email", nativeQuery = true)
    SellerDetails findByEmailAddress(String email);
}
