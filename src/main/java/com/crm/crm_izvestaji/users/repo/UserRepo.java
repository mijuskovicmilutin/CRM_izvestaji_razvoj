package com.crm.crm_izvestaji.users.repo;

import com.crm.crm_izvestaji.users.dto.UserDtoImpl;
import com.crm.crm_izvestaji.users.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {

    public UserDtoImpl findUserByUsername(String username){
        User user = new User();
        user.setUsername("milutinm");
        user.setPassword("Dunav33.m");
        user.setEmail("mijuskovicmilutin@gmail.com");
        user.setEmailAppPassword("ivqgrlgktujnaiba");
        user.setName("Milutin Mijušković");
        user.setQualifications("dipl. Inž. Telekomunikacionog saobraćaja");
        user.setPosition("Softver inženjer junior");
        user.setEmployment("Direkcija za IT podršku i ICT servise");
        user.setAddress("Adresa: Bulevar Umetnosti 16a, 10070 Beograd");
        user.setPhoneNumber("m: +381 64 6506032");
        User user1 = new User();
        user1.setUsername("dejank");
        user1.setPassword("Dunav33.m");
        user1.setEmail("dejank@telekom.rs");
        user1.setEmailAppPassword("123");
        user1.setName("Milutin Mijušković");
        user1.setQualifications("dipl. Inž. Telekomunikacionog saobraćaja");
        user1.setPosition("Softver inženjer junior");
        user1.setEmployment("Direkcija za IT podršku i ICT servise");
        user1.setAddress("Adresa: Bulevar Umetnosti 16a, 10070 Beograd");
        user1.setPhoneNumber("m: +381 64 6506032");

        if (username.equals(user.getUsername())){
            return UserDtoImpl.builder().username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .emailAppPassword(user.getEmailAppPassword())
                    .name(user.getName())
                    .qualifications(user.getQualifications())
                    .position(user.getPosition())
                    .employment(user.getEmployment())
                    .address(user.getAddress())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
        }else if (username.equals(user1.getUsername())){
            return UserDtoImpl.builder().username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .emailAppPassword(user1.getEmailAppPassword())
                    .name(user.getName())
                    .qualifications(user.getQualifications())
                    .position(user.getPosition())
                    .employment(user.getEmployment())
                    .address(user.getAddress())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
        }else {
            return null;
        }
    }
}
