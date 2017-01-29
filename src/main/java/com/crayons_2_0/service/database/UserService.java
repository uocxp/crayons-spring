package com.crayons_2_0.service.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.UserDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ondrej Kvasnovsky
 */
public class UserService implements UserDetailsService {

	@Autowired
    private UserDAO userDAO;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // fetch user from e.g. DB
        
        /*
        if ("client".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("CLIENT"));
            User user = new User(username, "pass", true, true, false, false, authorities);
            return user;
        }
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            User user = new User(username, "pass", true, true, false, false, authorities);
            return user;
        } else {
            return null;
        }
        */
        
        
        // Part 2 mit DATENBANK
        CrayonsUser user = findByEMail(username);
        return user;
        
        
    }
    
    public List<CrayonsUser> findAll() {
	    List<CrayonsUser> res = userDAO.findAll();
	    return res;
	}
    
    public boolean insertUser(CrayonsUser user) {
    	
    	// Maybe Useless because something could change 
    	/* 
    	// Check if Exists, return false if exists
    	List<CrayonsUser> users = findAll();
    	for (CrayonsUser tmpUser : users) {
        	if (tmpUser.geteMail().equals(user.geteMail())) {
        		return false;
        	}
        }
        */
    	
    	// User exists not -> Save
    	userDAO.save2(user);
    	return true;
    }
    
    public boolean removeUser(CrayonsUser user) {
        return true;
    }
    
    
    /*
    public CrayonsUser findByUserId(long userId) {
        return null;
    }
    */
    
    
    /*
    public CrayonsUser findByEMail(String eMail) {
        return null;
    }
    */
    
    public List<CrayonsUser> findByName(String firstName, String lastName) {
        return null;
    }
    
    public CrayonsUser findByEMail(String eMail) {
    	
    	List<CrayonsUser> users = findAll();
        
    	for (CrayonsUser tmpUser : users) {
        	if (tmpUser.geteMail().equals(eMail)) {
        		return tmpUser;
        	}
        }
        throw new UsernameNotFoundException("User with mail:" + eMail + "doesnt exists!");
    }
}