package com.crayons_2_0.controller;

import java.util.ArrayList; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.authentication.UserManager;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.view.login.LoginForm;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

@SpringComponent
public class RegisterFormListener2 implements Button.ClickListener {
        @Autowired
	    private UserManager userManager;

	    @Override
	    public void buttonClick(Button.ClickEvent event) {
	        try {
	            Button source = event.getButton();
	            LoginForm parent = (LoginForm) source.getParent();
	            String mail = parent.getTxtLogin().getValue();
	            String password = parent.getTxtPassword().getValue();
	            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		        authorities.add(new SimpleGrantedAuthority("CLIENT"));

				CrayonsUser user = new CrayonsUser("first", "last", mail, password, "German", true, true, false, false, authorities);

	            userManager.foo(user);
	            
	        } catch (Exception e) {
	            Notification.show("Registration failed: " + e.getMessage());
	        } 

	    }


}
