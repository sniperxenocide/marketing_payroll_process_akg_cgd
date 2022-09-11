package com.cgd.mkt.salary_process.service;

import com.cgd.mkt.salary_process.config.JwtTokenUtil;
import com.cgd.mkt.salary_process.model.master.User;
import com.cgd.mkt.salary_process.repository.master.ReUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class SeCommon {


    private final JwtTokenUtil jwtTokenUtil;
    private final ReUser reUser;

    @Autowired
    public SeCommon(JwtTokenUtil jwtTokenUtil, ReUser reUser)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.reUser = reUser;
    }

    public String getTokenFromCookie(HttpServletRequest request){
        if(request.getCookies()!=null){
            for (Cookie c:request.getCookies()) {
                if (c.getName().equals("Authorization")){
                    return "Bearer "+c.getValue();
                }
            }
        }
        return null;
    }

    public String getTokenFromHeader(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public String getToken(HttpServletRequest request){
        String tokenHeader = getTokenFromHeader(request);
        if(tokenHeader==null) tokenHeader = getTokenFromCookie(request);

        if(tokenHeader==null) return null;
        else if(tokenHeader.startsWith("Bearer")) return tokenHeader.substring(7);
        return null;
    }

    public User getUser(HttpServletRequest request){
        try {
            String token = getToken(request);
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            return reUser.findByUsername(userName).orElse(null);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
//
//    public User getUser(String userName){
//        try {
//            return userRepository.findByUserName(userName);
//        }catch (Exception e){e.printStackTrace();}
//        return null;
//    }
//
//    public User getUser(Long id){
//        return userRepository.findById(id).orElse(null);
//    }
//
//
//    public Role getUserRole(int userId){
//        UserRole userRole = userRoleRepository.findByUserId(userId).orElse(null);
//        if(userRole != null){
//            return roleRepository.findById((long)userRole.roleId).orElse(null);
//        }
//        return roleRepository.findById((long)2).orElse(null);
//    }
}
