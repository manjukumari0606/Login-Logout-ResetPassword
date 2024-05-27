package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.UserDto;
import com.example.entity.PasswordResetToken;
import com.example.entity.User;
import com.example.repository.TokenRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/home")
	public String home1() {
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// handler method to handle home page request
	@GetMapping("/index")
	public String home() {
		return "index";
	}

	// handler method to handle user registration form request
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		// create model object to store form data
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "register";
	}

	// handler method to handle user registration form submit request
	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
		User existingUser = userService.findUserByEmail(userDto.getEmail());

		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null, "There is already an account registered with the same email");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", userDto);
			return "/register";
		}

		userService.saveUser(userDto);
		return "redirect:/register?success";
	}

	@GetMapping("/users")
	public String users(Model model) {
		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "users";
	}

	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}

	@PostMapping("/forgotPassword")
	public String forgotPassordProcess(@ModelAttribute UserDto userDTO) {
		String output = "";
		User user = userRepository.findByEmail(userDTO.getEmail());
		if (user != null) {
			output = userServiceImpl.sendEmail(user);
		}
		if (output.equals("success")) {
			return "redirect:/forgotPassword?success";
		}
		return "redirect:/forgotPassword?error";
	}
	

//	@GetMapping("/resetPassword/{token}")
//	public String resetPasswordForm(@PathVariable String token, Model model) {
//		
//		PasswordResetToken token_data = tokenRepository.findByToken(token);
//		
//		if (token_data != null && token_data.getUser() != null) { 		// Add null check for reset.getUser()
//			
//			if (userServiceImpl.hasExpired(token_data.getExpiryDateTime())) {
//				
//				model.addAttribute("email", token_data.getUser().getEmail());
//				System.out.println("Fetched email data" +token_data.getUser().getEmail());
//				
//				return "resetPassword";
//				//return "redirect:/forgotPassword?error=invalid_token";
//				
//			} else {
//				
//				return "redirect:/forgotPassword?error=expired";
//			}
//			
//		} else {
//			return "redirect:/forgotPassword?error=invalid_token";
//			//return "resetPassword";
//		}
//	}
	
	@GetMapping("/resetPassword/{token}")
	public String resetPasswordForm(@PathVariable String token, Model model) {
	    
	    PasswordResetToken tokenData = tokenRepository.findByToken(token);
	    
	  
	    if (tokenData == null) {
	       
	        System.out.println("Token not found in database: " + token);
	        return "redirect:/forgotPassword?error=invalid_token";
	    }
	    
	   
	    if (userServiceImpl.hasExpired(tokenData.getExpiryDateTime())) {
	        model.addAttribute("email", tokenData.getUser().getEmail());
	        return "resetPassword";
	    } else {
	       
	        System.out.println("Token is not expired: " + token);
	        return "redirect:/forgotPassword?error=expired";
	    }
	}


	@PostMapping("/resetPassword")
	public String passwordResetProcess(@ModelAttribute UserDto userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail());
		if (user != null) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userRepository.save(user);
		}
		return "redirect:/login";
	}

}
