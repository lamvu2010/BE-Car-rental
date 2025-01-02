package com.example.be_car_rental.security.Jwt;

import com.example.be_car_rental.models.Taikhoan;
import com.example.be_car_rental.repositories.TaikhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private TaikhoanRepository taikhoanRepository;

private static PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Taikhoan> taikhoan = taikhoanRepository.findById(username);
        if(taikhoan.isEmpty() || !passwordEncoder.matches(password,taikhoan.get().getMatkhau())){
            throw new BadCredentialsException("Invalid username or password");
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
