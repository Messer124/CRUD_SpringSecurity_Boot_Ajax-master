package app.service;

import app.dao.abstr.UserRepository;
import app.model.Role;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private MailService_gRPC mailService;

    @Autowired
    public RegistrationService(UserRepository userRepository, MailService_gRPC mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Transactional
    public boolean registerUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, "ROLE_USER"));
        user.setRoles(roles);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.insertUser(user);
        mailService.sendGreetingEmail(user.getEmail());
        return true;
    }
}
