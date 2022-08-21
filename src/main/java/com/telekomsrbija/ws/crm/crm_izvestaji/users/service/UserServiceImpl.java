package com.crm.crm_izvestaji.users.service;

import com.crm.crm_izvestaji.users.dto.UserDtoImpl;
import com.crm.crm_izvestaji.users.repo.UserRepo;;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDtoImpl user = userRepo.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Korisnik sa unetim username ne postoji.");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public String getUserEmail (){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userRepo.findUserByUsername(username).getEmail();
        return email;
    }

    public String getUserEmailAppPassword (){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String pass = userRepo.findUserByUsername(username).getEmailAppPassword();
        return pass;
    }

    public String getUserName (){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = userRepo.findUserByUsername(username).getName();
        return name;
    }

    public String getUserQualifications(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String qualifications = userRepo.findUserByUsername(username).getQualifications();
        return qualifications;
    }

    public String getUserPosition(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String position = userRepo.findUserByUsername(username).getPosition();
        return position;
    }

    public String getUserEmployment(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String employment = userRepo.findUserByUsername(username).getEmployment();
        return employment;
    }

    public String getUserAddress(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String address = userRepo.findUserByUsername(username).getAddress();
        return address;
    }

    public String getUserPhoneNum(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String phoneNum = userRepo.findUserByUsername(username).getPhoneNumber();
        return phoneNum;
    }
}
