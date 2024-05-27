package com.example.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.UserDto;
import com.example.entity.PasswordResetToken;
import com.example.entity.User;
import com.example.repository.TokenRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;

@Service
public  class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
    private TokenRepository tokenRepository;
	
   
   @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        
        user.setRoles("ROLE_USER");
        

        // Save the user with associated roles
        userRepository.save(user);
    }


   @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

   
   @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

//    private Role checkRoleExist(){
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        return roleRepository.save(role);
//    }
    
    @Override
    public List<User> findAllUsersWithRoles() {
        List<User> users = userRepository.findAll();
        List<User> usersWithRoles = new ArrayList<>();
        for (User user : users) {
            user.setRoles(user.getRoles());
            usersWithRoles.add(user);
        }
        return usersWithRoles;
    }

    @Override
    public void updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setRoles(newRole);
            userRepository.save(user);
        }
    }

	@Override
	public String sendEmail(User user) {
		try {
			String resetLink = generateResetToken(user);
			
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("manjukri0606@gmail.com");
			msg.setTo(user.getEmail());
			msg.setSubject("This is for reset the password");
			msg.setText("Hello \n\n" + "Please click on this link to reset your password :"+ resetLink + ". \n\n"
					+ "Regard \n" + user.getName());
			javaMailSender.send(msg);
			
			return "success";
			
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		
	}

//	private String generateResetToken(User user) {
//	    UUID uuid = UUID.randomUUID();
//	    LocalDateTime currDateTime = LocalDateTime.now();
//	    LocalDateTime expiryDateTime = currDateTime.plusMinutes(30);
//
//	    PasswordResetToken passwordResetToken = new PasswordResetToken();
//	    passwordResetToken.setUser(user);
//	    passwordResetToken.setToken(uuid.toString());
//	    passwordResetToken.setExpiryDateTime(Timestamp.valueOf(expiryDateTime)); // Convert LocalDateTime to Timestamp
//
//	    PasswordResetToken savedToken = tokenRepository.save(passwordResetToken);
//	    if (savedToken != null) {
//	        String endPointUrl = "http://localhost:9095/resetPassword";
//	        return endPointUrl + "/" + savedToken.getToken();
//	    }
//	    return "";
//	}
	
	private String generateResetToken(User user) {
	    UUID uuid = UUID.randomUUID();
	    LocalDateTime currDateTime = LocalDateTime.now();
	    LocalDateTime expiryDateTime = currDateTime.plusMinutes(30);

	    PasswordResetToken passwordResetToken = new PasswordResetToken();
	    passwordResetToken.setUser(user);
	    passwordResetToken.setToken(uuid.toString());
	    passwordResetToken.setExpiryDateTime(Timestamp.valueOf(expiryDateTime));

	    PasswordResetToken savedToken = tokenRepository.save(passwordResetToken);
	    
	    if (savedToken != null) {
	        String endPointUrl = "http://localhost:9095/resetPassword";
	        return endPointUrl + "/" + savedToken.getToken();
	    }
	    return "";
	}


	public boolean hasExpired(LocalDateTime localDateTime) {
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    return currentDateTime.isBefore(localDateTime);
	}


	
}