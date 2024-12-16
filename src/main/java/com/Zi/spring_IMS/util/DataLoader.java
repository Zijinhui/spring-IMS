package com.Zi.spring_IMS.util;

import com.Zi.spring_IMS.repository.AuthRepository;
import com.Zi.spring_IMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private UserRepository userRepository;
    private AuthRepository authRepository;

    private PasswordEncoder encoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder encoder, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authRepository = authRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Add initial data to the database
//        userRepository.save(new User(null, "Zi Zhuang", "zzhuang@gmail.com", encoder.encode("@Zz123456"), Role.ADMIN, LocalDateTime.now(), LocalDateTime.now(), Account_Status.ACTIVE));
//        userRepository.save(new User(null, "Wasabi Fang", "wfang@gmail.com", encoder.encode("@Fw123456"), Role.MANAGER, LocalDateTime.now(), LocalDateTime.now(), Account_Status.ACTIVE));
//        userRepository.save(new User(null, "Talulah Riley", "triley@gmail.com", encoder.encode("@Rt123456"), Role.STAFF, LocalDateTime.now(), LocalDateTime.now(), Account_Status.ACTIVE));
//
//        authRepository.save(new Auth(null, "zzhuang@gmail.com", encoder.encode("@Zz123456"), Role.ADMIN, Account_Status.ACTIVE));
//        authRepository.save(new Auth(null, "wfang@gmail.com", encoder.encode("@Fw123456"), Role.MANAGER, Account_Status.ACTIVE));
//        authRepository.save(new Auth(null, "triley@gmail.com", encoder.encode("@Rt123456"), Role.STAFF, Account_Status.ACTIVE));
    }
}
