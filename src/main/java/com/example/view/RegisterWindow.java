package com.example.view;

import com.example.controller.RegisterFormListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
@SpringView(name = RegisterWindow.VIEW_NAME)
@UIScope

public class RegisterWindow extends Window implements View{
    
    /**
     * 
     */
    public static final String VIEW_NAME = "registerWindow";
    private static final long serialVersionUID = 1L;
    private TextField email = new TextField();
    private PasswordField password = new PasswordField();
    private TextField firstname = new TextField();
    private TextField lastname = new TextField();
    
    public RegisterWindow() {
        
        
        super("Create a new user"); // Set window caption
        setWidth(300.0f, Unit.PIXELS);

        // Some basic content for the window
      VerticalLayout content = new VerticalLayout();
      
      getEmail().setRequired(true);
      getPassword().setRequired(true);
      getFirstname().setRequired(true);
      getLastname().setRequired(true);
      content.addComponent(new Label("firstname"));
      
      content.addComponent(getFirstname());
      
      content.addComponent(new Label("lastname"));
      
      content.addComponent(getLastname());
      
      content.addComponent(new Label("eMail:"));
       
      content.addComponent(getEmail());
      
      content.addComponent(new Label(""));
      
      content.addComponent(new Label("password"));
      
      content.addComponent(getPassword());
      
      Button btnInsertUser = new Button("create a user");
      btnInsertUser.addClickListener(new RegisterFormListener());
//      

        // Disable the close button
        setClosable(true);

        // Trivial logic for closing the sub-window
       content.setMargin(true);
       content.setSpacing(true);
        content.addComponent(btnInsertUser);
        setContent(content);
        
    }

    public TextField getEmail() {
        return email;
    }

    public void setEmail(TextField email) {
        this.email = email;
    }

    public PasswordField getPassword() {
        return password;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    public TextField getFirstname() {
        return firstname;
    }

    public void setFirstname(TextField firstname) {
        this.firstname = firstname;
    }

    public TextField getLastname() {
        return lastname;
    }

    public void setLastname(TextField lastname) {
        this.lastname = lastname;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }
}