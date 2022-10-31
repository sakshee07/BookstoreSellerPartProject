package com.bridgelabz.sellerdetails.repository;


import com.bridgelabz.sellerdetails.model.OTPlogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepo extends JpaRepository<OTPlogin, Long> {
    @Query(value = "SELECT * FROM otp WHERE email_address=:email", nativeQuery = true)
    OTPlogin findByEmail(String email);
}
