package com.ecom.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.dao.ConfirmationTokenRepository;
import com.ecom.dao.RoleDao;
import com.ecom.dao.UserDao;
import com.ecom.dao.UserRepository;
import com.ecom.entity.ConfirmationToken;
import com.ecom.entity.Role;
import com.ecom.entity.User;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailService emailService;
    
    @Autowired
    UserRepository userRepository;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("rakesh@admin");
        adminUser.setUserPassword(getEncodedPassword("rakesh123"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

//        User user = new User();
//        user.setUserName("raj123");
//        user.setUserPassword(getEncodedPassword("raj@123"));
//        user.setUserFirstName("raj");
//        user.setUserLastName("sharma");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
    }

    public String registerNewUser(User user) {
//    	if (userRepository.existsByemailId(user.getEmailId())) {
//    		return new String("Error: Email is already in use!");
//    	}
    	//    	if (userRepository.existsByUserEmail(user.getEmailId())) {
    	//           // return ResponseEntity.badRequest().body("Error: Email is already in use!");
    	//    		 return 
    	//        }
    	Role role = null;
    	if(user.getSellerGst()!=null) {
    		role = roleDao.findById("Seller").get();
    	}
    	
    	System.out.println("user.getUserName()"+user.getUserName());
    	if(user.getEmailId()!=null && user.getUserName().contains("@Support")) {
    		
    			role = roleDao.findById("Support").get();
    		
    	}
    	else if(user.getEmailId()!=null && user.getUserName().contains("@Admin")) {
    		
    			role = roleDao.findById("Admin").get();
    		
    	}
    	else {
    		role = roleDao.findById("User").get();
    	}
    	Set<Role> userRoles = new HashSet<>();
    	userRoles.add(role);
    	user.setRole(userRoles);
    	user.setUserPassword(getEncodedPassword(user.getUserPassword()));
    	user.setEmailId(user.getEmailId());

    	ConfirmationToken confirmationToken = new ConfirmationToken(user);
    	System.out.println("confirmationToken in Service"+confirmationToken.getConfirmationToken()+"Token Id : "+confirmationToken.getTokenId());
    	userDao.save(user);
    	//confirmationToken.setConfirmationToken( UUID.randomUUID().toString());
    	confirmationTokenRepository.save(confirmationToken);
    	
    	

    	

    	SimpleMailMessage mailMessage = new SimpleMailMessage();
    	mailMessage.setTo(user.getEmailId());
    	mailMessage.setSubject("Complete Registration!");
    	mailMessage.setText("To confirm your account, please click here : "
    			+"http://localhost:9090/confirm-account?token="+confirmationToken.getConfirmationToken());
    	emailService.sendEmail(mailMessage);
    	return "Verify email by the link sent on your email address";
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
    	System.out.println("Email verification starts");
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
        	if(token.getUserEntity().getEmailId()!=null) {
            User user = userRepository.findByemailIdIgnoreCase(token.getUserEntity().getEmailId());
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully!");
        	}
        	else if(token.getUserEntity().getSellerEmailId()!=null) {

                User user = userRepository.findByemailIdIgnoreCase(token.getUserEntity().getSellerEmailId());
                user.setEnabled(true);
                userRepository.save(user);
                return ResponseEntity.ok("Email verified successfully!");
            	
        	}
        	else {
                return ResponseEntity.badRequest().body("Error: User created with no mail id ");

        	}
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }
}
