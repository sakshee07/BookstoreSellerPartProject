package com.bridgelabz.sellerdetails.service;

import com.bridgelabz.sellerdetails.dto.*;
import com.bridgelabz.sellerdetails.exception.OtpException;
import com.bridgelabz.sellerdetails.exception.SellerException;
import com.bridgelabz.sellerdetails.model.OTPlogin;
import com.bridgelabz.sellerdetails.model.SellerDetails;
import com.bridgelabz.sellerdetails.repository.OTPRepo;
import com.bridgelabz.sellerdetails.repository.SellerRepository;
import com.bridgelabz.sellerdetails.utility.EmailSenderService;
import com.bridgelabz.sellerdetails.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SellerService implements ISellerService{
    @Autowired
    private TokenUtility tokenUtility;
    @Autowired
    private EmailSenderService emailSender;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private OTPRepo otpRepo;

    //Register Seller
    @Override
    public ResponseDTO registerSeller(SellerDTO sellerDTO) throws SellerException {
        LocalDateTime createdTime = LocalDateTime.now();
        boolean verify=false;
        SellerDetails sellerDetails = new SellerDetails(sellerDTO.getBusinessName(), sellerDTO.getSellerName(), sellerDTO.getGstn(), sellerDTO.getSellerWebsite(), sellerDTO.getEmailAddress(), sellerDTO.getPassword(), sellerDTO.getContactNumber(), verify, createdTime, null);
        sellerRepository.save(sellerDetails);
        String token = tokenUtility.createToken(sellerDetails.getId());
        //sending email
        emailSender.sendEmail(sellerDetails.getEmailAddress(), "Data Added!!!", "Your Account is registered! Please Click on the below link to verify."+"\n"+"http://localhost:7070/seller/verifySeller/"+token);
        ResponseDTO respDTO = new ResponseDTO(token, sellerDetails);
        return respDTO;
    }
    //Verify seller by email
    @Override
    public String verifySeller(String token) {
        boolean verify = true;
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = sellerRepository.findById(sellerId);
        sellerDetails.get().setVerify(verify);
        sellerRepository.save(sellerDetails.get());
        return "Seller Successfully Verified";
    }
//Sending otp to the seller email address
    @Override
    public String sendOTP(String email) {
        SellerDetails sellerDetails = sellerRepository.findByEmailAddress(email);
        if (sellerDetails != null && sellerDetails.isVerify()) {
            OTPlogin otplogin = new OTPlogin();
            Random random = new Random();
            Long otp = Long.valueOf(random.nextInt(900000) + 100000);
            otplogin.setOtp(otp);
            otplogin.setEmailAddress(email);
            otplogin.setOtpTime(LocalDateTime.now());
            otplogin.setOtpExpiredTime(LocalDateTime.now().plusMinutes(5));
            otpRepo.save(otplogin);
            //Sending Email
            emailSender.sendEmail(email, "OTP Login", "Here is the OTP for your login: " + otp + "\n This OTP is valid for 5 Minutes");
            return "OTP sent to the email address";
        } else
            throw new OtpException("Invalid Email Address");
    }
//login with OTP
    @Override
    public String loginWithOTP(OtpDTO otpDTO) {
        SellerDetails sellerDetails = sellerRepository.findByEmailAddress(otpDTO.getEmailAddress());
        OTPlogin otplogin = otpRepo.findByEmail(otpDTO.getEmailAddress());
        LocalDateTime checkOtpExpire = LocalDateTime.now();
        long noOfSeconds = Duration.between(otplogin.getOtpExpiredTime(), checkOtpExpire).toSeconds();
        if (sellerDetails != null && otplogin != null && otplogin.getOtp().equals(otpDTO.getOtp())) {
            //OTP valid for 5 minutes
            if(noOfSeconds<=300) {
                emailSender.sendEmail(otpDTO.getEmailAddress(), "OTP Login", "Login Successful");
                //Deleting the OTP after successful Login
                otpRepo.delete(otplogin);
                String token = tokenUtility.createToken(sellerDetails.getId());
                return "Login Successful\n"+token;
            }else
                //Deleting the expired OTP
                otpRepo.delete(otplogin);
            throw new OtpException("OTP expired");
        } else
            throw new OtpException("Invalid OTP");
    }

    //Get seller data by Token
    @Override
    public SellerDetails getSellerDataByToken(String token) {
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = sellerRepository.findById(sellerId);
        if(sellerDetails.isPresent()){
            return sellerDetails.get();
        }else
            throw new SellerException("Invalid Token");
    }

    //Get all Sellers list
    @Override
    public List<SellerDetails> getAllSellerData() {
        List<SellerDetails> sellerDetailsList = sellerRepository.findAll();
        if (sellerDetailsList.isEmpty()) {
            throw new SellerException("No Seller Registered yet!!!!");
        } else
            return sellerDetailsList;
    }
    //Get the Seller details by ID:
    @Override
    public SellerDetails getSellerDataById(Long id) {
        SellerDetails sellerDetails = sellerRepository.findById(id).orElse(null);
        if (sellerDetails != null) {
            return sellerDetails;
        } else
            throw new SellerException("ID: " + id + ", does not exist");
    }
    //Get Seller details by email address
    @Override
    public SellerDetails getSellerDataByEmailAddress(String email) {
        SellerDetails userDetails = sellerRepository.findByEmailAddress(email);
        if (userDetails != null) {
            return userDetails;
        } else
            throw new SellerException("Email Address: " + email + ", does not exist");
    }
    //Login check
    @Override
    public String loginUser(LoginDTO loginDTO) {
        Optional<SellerDetails> sellerDetails = Optional.ofNullable(sellerRepository.findByEmailAddress(loginDTO.emailAddress));
        if(sellerDetails.isPresent() && sellerDetails.get().isVerify()) {
            if(sellerDetails.get().getPassword().equals(loginDTO.getPassword())) {
                emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Login", "Login Successful!");
                String token = tokenUtility.createToken(sellerDetails.get().getId());
                return "Login Successful\n"+token;
            } else
                emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Login", "You have entered Invalid password!");
            throw new SellerException("Login Failed, Wrong Password!!!");
        }else
            throw new SellerException("Login Failed, Entered wrong email or password or Account not verified!!!");
    }
    //Change password
    @Override
    public String changePassword(ChangePasswordDTO changePasswordDTO) {
        Optional<SellerDetails> sellerDetails = Optional.ofNullable(sellerRepository.findByEmailAddress(changePasswordDTO.getEmailAddress()));
        String password = changePasswordDTO.getPassword();
        if(sellerDetails.isPresent() && sellerDetails.get().getPassword().equals(changePasswordDTO.getOldPassword()) && sellerDetails.get().isVerify()){
            sellerDetails.get().setPassword(password); //changing password
            //Sending Email
            emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Password Change!", "Password Changed Successfully!");
            sellerRepository.save(sellerDetails.get());
            return "Password Changed and email sent!";
        }else
            return "Invalid Email Address or Old Password";
    }
    //Sending email for the forgot password, will receive email of reset password
    @Override
    public String forgotPassword(String token) {
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = sellerRepository.findById(sellerId);
        if(sellerDetails.isEmpty()){
            throw new SellerException("Invalid Email Address");
        }else
            emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Forgot Password", "Please click on the below link to reset password"+"\nhttp://localhost:7070/seller/resetPassword/"+token);
            return "Password link shared to your email address";

//        if(sellerDetails != null){
//            emailSender.sendEmail(sellerDetails.getEmailAddress(), "Forgot Password", "Please click on the below link to reset password"+"\nhttp://localhost:7070/seller/resetPassword/"+token);
//            return "Password link shared to your email address";
//        }else
//            throw new SellerException("Invalid Email Address");
    }
    //reset password
    @Override
    public String resetPassword(ForgotPasswordDTO forgotPasswordDTO, String token) {
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = sellerRepository.findById(sellerId);
        if(sellerDetails.get().getEmailAddress().equals(forgotPasswordDTO.getEmailAddress()) && sellerDetails.isPresent() && sellerDetails.get().isVerify()){
            sellerDetails.get().setPassword(forgotPasswordDTO.getNewPassword());
            sellerRepository.save(sellerDetails.get());
            return "Password Reset successful!";
        }else
            throw new SellerException("Invalid Email Address or Verification is not done");
    }
    //Update data by Token
    @Override
    public SellerDetails updateDataByToken(SellerDTO sellerDTO, String email, String token) {
        Long sellerId = tokenUtility.decodeToken(token);
        Optional<SellerDetails> sellerDetails = Optional.ofNullable(sellerRepository.findByEmailAddress(email));
        if(sellerDetails.isPresent() && sellerDetails.get().getId().equals(sellerId) && sellerDetails.get().isVerify()){
            LocalDateTime updatedTimeStamp = LocalDateTime.now();
            sellerDetails.get().setBusinessName(sellerDTO.getBusinessName());
            sellerDetails.get().setSellerName(sellerDTO.getSellerName());
            sellerDetails.get().setGstn(sellerDTO.getGstn());
            sellerDetails.get().setSellerWebsite(sellerDTO.getSellerWebsite());
            sellerDetails.get().setEmailAddress(sellerDTO.getEmailAddress());
            sellerDetails.get().setPassword(sellerDTO.getPassword());
            sellerDetails.get().setContactNumber(sellerDTO.getContactNumber());
            sellerDetails.get().setUpdatedTimeStamp(updatedTimeStamp);
            //Savin the updated data
            sellerRepository.save(sellerDetails.get());
            //sending email
            emailSender.sendEmail(sellerDetails.get().getEmailAddress(), "Data Updated!!!", "Please Click on the below link for the updated details."+"\n"+"http://localhost:7070/seller/SellerData/"+token);
            return sellerDetails.get();
        } else
            throw new SellerException("Invalid Email Address: " + sellerDTO.getEmailAddress()+" Or Seller is not verified");
        }
    @Override
    public Optional<SellerDetails> deleteSellerData(String email, String token) {
        Optional<SellerDetails> sellerDetails = Optional.ofNullable(sellerRepository.findByEmailAddress(email));
        Long sellerId = tokenUtility.decodeToken(token);
        if(sellerDetails.isPresent() && sellerDetails.get().getId().equals(sellerId) && sellerDetails.get().isVerify()){
            sellerRepository.deleteById(sellerId);
            //sending email
            emailSender.sendEmail(email, "Data Deleted!!!", "Your Data deleted successfully from the BookStore Seller Application, with the Seller ID: "+sellerId);
        }else {
            throw new SellerException("Invalid email Address");
        }
        return sellerDetails;
        }
}
