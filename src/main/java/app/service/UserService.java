package app.service;

import app.dao.RoleRepository;
import app.dao.UserRepository;
import app.model.Role;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public boolean insertUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(1L));
        user.setRoles(roles);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.insertUser(user);
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
        userRepository.deleteUser(id);
    }

    @Transactional
    public void updateUser(User user) {
        User userUpd = userRepository.selectUserById(user.getId());
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            userUpd.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        Set<Role> roleSet = new HashSet<>();
        for (Role role : user.getRoles()) {
            roleSet.add(roleRepository.findByName(role.getName()));
        }
        if (!user.getRoles().isEmpty()) {
            userUpd.setRoles(roleSet);
        }

        userUpd.setUsername(user.getUsername());
        userUpd.setEmail(user.getEmail());
        userRepository.updateUser(userUpd);

        updatePrincipal(user, roleSet);
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
