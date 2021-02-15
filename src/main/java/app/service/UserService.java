package app.service;

import app.dao.RoleRepository;
import app.dao.UserRepository;
import app.model.Role;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;
    private MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Transactional
    public boolean insertUser(User user) {
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getName().equals("ROLE_USER")) role.setId(1L);
            if (role.getName().equals("ROLE_ADMIN")) role.setId(2L);
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.insertUser(user);
        mailService.sendGreetingEmail(user.getEmail());
        return true;
    }

    public User selectUserByUsername(String login) {
        return userRepository.selectUserByUsername(login);
    }

    public List<User> selectAllUsers() {
        return userRepository.selectAllUsers();
    }

    @Transactional
    public void deleteUser(Long id) {
        mailService.sendEmailOnDeletion(userRepository.selectUserById(id).getEmail());
        userRepository.deleteUser(id);
    }

    @Transactional
    public void updateUser(User user) {
        User userUpd = userRepository.selectUserById(user.getId());
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            userUpd.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        Set<Role> roleSet = user.getRoles();
        for (Role role : roleSet) {
            if (role.getName().equals("ROLE_USER")) role.setId(1L);
            if (role.getName().equals("ROLE_ADMIN")) role.setId(2L);
        }

        if (!user.getRoles().isEmpty()) {
            userUpd.setRoles(roleSet);
        }

        userUpd.setUsername(user.getUsername());
        userUpd.setEmail(user.getEmail());
        userRepository.updateUser(userUpd);

        updatePrincipal(user, roleSet);
        mailService.sendEmailOnUpdating(userUpd.getEmail());
    }

    public User selectUserById(Long id) {
        return userRepository.selectUserById(id);
    }

    private void updatePrincipal(User user, Set<Role> roleSet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        if (principal.getUsername().equals(user.getUsername())) {
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(roleSet);
            Authentication newAuth = new UsernamePasswordAuthenticationToken(principal, auth.getCredentials(), updatedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }

}

