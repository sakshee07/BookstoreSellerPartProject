package com.bridgelabz.sellerdetails.controller;

import com.bridgelabz.sellerdetails.dto.*;
import com.bridgelabz.sellerdetails.model.SellerDetails;
import com.bridgelabz.sellerdetails.service.ISellerService;
import com.bridgelabz.sellerdetails.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    ISellerService sellerService;
    @Autowired
    TokenUtility tokenUtility;
    //Register Seller
    @PostMapping("/registerSeller")
    public ResponseEntity<String> addSellerDetails(@Valid @RequestBody SellerDTO sellerDTO) {
        ResponseDTO response = sellerService.registerSeller(sellerDTO);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
    //Verify Seller, email will be sent to the seller for the verification
    @PostMapping("/verifySeller/{token}")
    public ResponseEntity<String> verifySeller(@PathVariable String token) {
        String response = sellerService.verifySeller(token);
        ResponseDTO respDTO = new ResponseDTO("Verification Status", response);
        return new ResponseEntity(respDTO, HttpStatus.CREATED);
    }
    //Get Seller Details by token
    @GetMapping("/sellerData/{token}")
    public ResponseEntity<String> getUserDetails(@PathVariable String token) {
        SellerDetails sellerDetails = sellerService.getSellerDataByToken(token);
        Long Userid = tokenUtility.decodeToken(token);
        ResponseDTO respDTO = new ResponseDTO("Data retrieved successfully for the ID: " + Userid, sellerDetails);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }
    //Get all seller list
    @GetMapping("/getAllSeller")
    public ResponseEntity<ResponseDTO> getAllUserDetails() {
        List<SellerDetails> sellerDetailsList = sellerService.getAllSellerData();
        ResponseDTO responseDTO = new ResponseDTO("All Sellers List, total count: " + sellerDetailsList.size(), sellerDetailsList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Seller Data by ID, return type is object as it is retrieved using rest template
    @GetMapping("/sellerId/{id}")
    public SellerDetails getUserDetailsById(@PathVariable Long id) {
        SellerDetails sellerDetails = sellerService.getSellerDataById(id);
        return sellerDetails;
    }

    //Get Seller Data by Email Address
    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDTO> getUserByEmail(@PathVariable String email) {
        SellerDetails sellerDetails = sellerService.getSellerDataByEmailAddress(email);
        ResponseDTO responseDTO = new ResponseDTO("Seller Details with the Email Address: " + email, sellerDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Login check
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        String response = sellerService.loginUser(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Status:", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Change password
    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        String response = sellerService.changePassword(changePasswordDTO);
        ResponseDTO responseDTO = new ResponseDTO("Password Status:", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Forgot Password
    @PostMapping("/forgotPassword/{token}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String token) {
        String response = sellerService.forgotPassword(token);
        ResponseDTO responseDTO = new ResponseDTO("Password Link Shared to email", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //reset password to the new password(forgot old password)
    @PostMapping("/resetPassword/{token}")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO, @PathVariable String token) {
        String response = sellerService.resetPassword(forgotPasswordDTO, token);
        ResponseDTO responseDTO = new ResponseDTO("Password Reset", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Updating the Seller Data using token
    @PutMapping("/updateSellerData/{email}/{token}")
    public ResponseEntity<ResponseDTO> updateSellerByToken(@PathVariable String token, @PathVariable String email, @Valid @RequestBody SellerDTO sellerDTO) {
        SellerDetails userData = sellerService.updateDataByToken(sellerDTO, email, token);
        ResponseDTO respDTO = new ResponseDTO("Data Update info", userData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //delete the Seller details using token
    @DeleteMapping("/deleteSellerData/{email}/{token}")
    public ResponseEntity<ResponseDTO> deleteSellerDataByToken(@PathVariable String email, @PathVariable String token) {
        Optional<SellerDetails> deletedData = sellerService.deleteSellerData(email, token);
        ResponseDTO respDTO = new ResponseDTO("Deleted Successfully with email address: "+email+", and e-mail sent, Below Data is deleted", deletedData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Send OTP to the email
    @PostMapping("/sendOTP/{email}")
    public ResponseEntity<ResponseDTO> sendOTP(@PathVariable String email) {
        String response = sellerService.sendOTP(email);
        ResponseDTO responseDTO = new ResponseDTO("OTP login", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Login with OTP
    @PostMapping("/loginWithOTP")
    public ResponseEntity<ResponseDTO> loginWithOTP(@RequestBody OtpDTO otpDTO) {
        String response = sellerService.loginWithOTP(otpDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Status", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
