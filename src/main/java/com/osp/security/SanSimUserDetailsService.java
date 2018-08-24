package com.osp.security;

import com.osp.model.User;
import com.osp.web.service.group.GroupService;
import com.osp.web.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/14/2017.
 */
public class SanSimUserDetailsService  implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userService.getByUsername(username).orElse(null);
        if (user != null) {
            List<GrantedAuthority> lstAuths = new ArrayList<GrantedAuthority>();
            List<String> list=groupService.loadListAuthorityOfUserByUsername(username).orElse(null);
            if(list!=null && list.size()>0){
                for(String authority:list){
                    lstAuths.add(new SimpleGrantedAuthority(authority));
                }
            }
            user.setGrantedAuths(lstAuths);
        }else{
            throw new UsernameNotFoundException("No user found for username '" + username +"'.");
        }
        return user;
    }
}
