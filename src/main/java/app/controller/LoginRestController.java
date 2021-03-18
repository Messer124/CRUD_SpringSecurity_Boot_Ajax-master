package app.controller;

import app.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class LoginRestController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public LoginRestController(AuthenticationManager authenticationManager,
                               @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                               JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "authenticate")
    public ResponseEntity<String> createJwtToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getParameter("username"),
                request.getParameter("password")
        ));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getParameter("username"));
        final String jwt = jwtUtil.generateToken(userDetails, userDetails.getAuthorities());
        System.out.println(userDetails.getAuthorities());
        response.addHeader("Authorization", "Bearer " + jwt);
        System.out.println(jwt);

        return null;
    }

}
