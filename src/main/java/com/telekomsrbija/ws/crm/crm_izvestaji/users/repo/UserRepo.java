package com.telekomsrbija.ws.crm.crm_izvestaji.users.repo;

import com.telekomsrbija.ws.crm.crm_izvestaji.users.dto.UserDtoImpl;
import com.telekomsrbija.ws.crm.crm_izvestaji.users.model.User;
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
        }else {
            return null;
        }
    }
}
