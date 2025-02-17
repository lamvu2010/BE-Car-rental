package com.example.be_car_rental.Security.Jwt;

import com.example.be_car_rental.Models.TaiKhoan;
import com.example.be_car_rental.Repositories.TaiKhoanRepository;
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
    private TaiKhoanRepository taikhoanRepository;

private static PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<TaiKhoan> taikhoan = taikhoanRepository.findById(username);
        if(taikhoan.isEmpty() || !passwordEncoder.matches(password,taikhoan.get().getMatKhau())){
            throw new BadCredentialsException("Invalid username or password");
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
