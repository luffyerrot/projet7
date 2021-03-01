package fr.pierre.api.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.pierre.api.services.UserService;
import fr.pierre.api.entities.User;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	public UserService serviceUser;
	
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = serviceUser.getUserEmail(userName);
        if (user.toString().isEmpty()) {
        	new UsernameNotFoundException("Email " + userName + " not found");
        }
         return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
         getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}