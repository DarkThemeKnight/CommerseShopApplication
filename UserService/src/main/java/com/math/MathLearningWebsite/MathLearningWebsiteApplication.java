package com.math.MathLearningWebsite;

import com.math.MathLearningWebsite.dao.ApplicationUserDao;
import com.math.MathLearningWebsite.entity.ApplicationUser;
import com.math.MathLearningWebsite.enumerations.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@Slf4j
public class MathLearningWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MathLearningWebsiteApplication.class, args);
	}
	@Autowired
	private ApplicationUserDao applicationUserDao;
	@Autowired
	private PasswordEncoder encoder;
	@Bean // for test purposes
	public CommandLineRunner generateSuperAdmin(){
		return args -> {
			String  username = "ayanfeoluwadafidi@gmail.com";
			String  password = "DaviDOMOTOLA1@";
			Optional<ApplicationUser> check = applicationUserDao.findByEmail(username);
			if (check.isPresent()){
				log.warn("Super admin =>{} already exists",username);
				return;
			}
			ApplicationUser superUser = ApplicationUser.builder()
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isEnabled(true)
					.isCredentialsNonExpired(true)
					.password(encoder.encode(password))
					.email(username)
					.userAuthorities(Role.ADMIN)
					.build();
			applicationUserDao.save(superUser);
			log.info("Super Admin => {} has been registered successfully",username);
		};
	}


}
