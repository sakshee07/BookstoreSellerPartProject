package com.bridgelabz.sellerdetails.service;


import com.bridgelabz.sellerdetails.dto.*;
import com.bridgelabz.sellerdetails.model.SellerDetails;

import java.util.List;
import java.util.Optional;

public interface ISellerService {
    ResponseDTO registerSeller(SellerDTO sellerDTO);
    SellerDetails getSellerDataByToken(String token);

    List<SellerDetails> getAllSellerData();

    SellerDetails getSellerDataById(Long id);

    SellerDetails getSellerDataByEmailAddress(String email);

    String loginUser(LoginDTO loginDTO);

    String changePassword(ChangePasswordDTO changePasswordDTO);

    String forgotPassword(String token);

    String resetPassword(ForgotPasswordDTO forgotPasswordDTO, String token);

    SellerDetails updateDataByToken(SellerDTO sellerDTO, String email, String token);

    Optional<SellerDetails> deleteSellerData(String email, String token);

    String verifySeller(String token);

    String sendOTP(String email);

    String loginWithOTP(OtpDTO otpDTO);
}
