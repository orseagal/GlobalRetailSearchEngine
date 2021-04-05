package com.websiteSearch.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.websiteSearch.dto.AuthenticationResponse;
import com.websiteSearch.dto.LoginRequest;
import com.websiteSearch.dto.RefreshTokenRequest;
import com.websiteSearch.dto.RegisterRequest;
import com.websiteSearch.exceptionHander.WebsiteSearchException;
import com.websiteSearch.service.AuthService;
import com.websiteSearch.service.RefreshTokenService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "false")
public class AuthController {

	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	
	 @PostMapping("/signup")
	    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) throws WebsiteSearchException {
	     authService.signup(registerRequest);
	        return new ResponseEntity<>("User Registration Successful",HttpStatus.OK);
	    }
	 
	
	 @GetMapping("accountVerification/{token}")
	 public ResponseEntity<String> verifyAccount(@PathVariable String token){
		 
		 authService.verifyAccount(token);
		return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
		 
	 }
	 
	 @PostMapping("/login")
	 public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
		return authService.login(loginRequest);
		 
	 }
	 
	 @PostMapping("/refresh/token")
	 public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
		 System.out.println("inside refresh token");
		return authService.refreshToken(refreshTokenRequest);
		 
	 }
	 
	 @PostMapping("/logout")
	 public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
		 refreshTokenService.deleteToken(refreshTokenRequest.getRefreshToken());
		 return  ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
		 
	 }
	 
}
