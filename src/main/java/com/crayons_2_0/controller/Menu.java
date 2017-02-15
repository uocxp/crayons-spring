package com.crayons_2_0.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.security.core.context.SecurityContextHolder;

import com.crayons_2_0.MyUI;
import com.crayons_2_0.authentication.AuthManager;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.view.login.LoginScreen;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Responsive navigation menu presenting a list of available views to the user.
 */
public class Menu extends CssLayout {

    /**
     * 
     */
    private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
    private Navigator navigator;
    private Map<String, Button> viewButtons = new HashMap<String, Button>();
    
    private MenuItem profileItem;
    private CssLayout menuItemsLayout;
    private CssLayout menuPart;
    
    /* User anzeigen 
    private User getCurrentUser() {
        return (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());
    }
    public void updateUserName(final ProfileUpdatedEvent event) {
        User user = getCurrentUser();
        settingsItem.setText(user.getFirstName() + " " + user.getLastName());
    }
    */
    public Menu(Navigator navigator) {
    	
    	ResourceBundle lang = LanguageService.getInstance().getRes();
    	
        this.navigator = navigator;
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        menuPart = new CssLayout();
        menuPart.addStyleName(ValoTheme.MENU_PART);

        // header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);
        top.setSpacing(true);
        Label title = new Label("Crayons 2.0");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.setSizeUndefined();
        //Image image = new Image(null, new ThemeResource("img/table-logo.png"));
        //image.setStyleName("logo");
        //top.addComponent(image);
        top.addComponent(title);
        menuPart.addComponent(top);
        //addComponent(buildUserMenu());

        // logout menu item
        MenuBar logoutMenu = new MenuBar();

        
        logoutMenu.addItem(lang.getString("Logout"), FontAwesome.SIGN_OUT, new Command() {

            private static final long serialVersionUID = 1L;
            
            @Override
            
            public void menuSelected(MenuItem selectedItem) {
                SecurityContextHolder.clearContext();
                AuthManager.setHasAuthority(false);
                MyUI.get().getPage().reload();
                MyUI.get().getSession().close();
                
            }
        });

        logoutMenu.addStyleName("user-menu");
        menuPart.addComponent(logoutMenu);

        // button for toggling the visibility of the menu when on a small screen
        final Button showMenu = new Button(lang.getString("Menu"), new ClickListener() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(final ClickEvent event) {
                if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE)) {
                    menuPart.removeStyleName(VALO_MENU_VISIBLE);
                } else {
                    menuPart.addStyleName(VALO_MENU_VISIBLE);
                }
            }
        });
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName(VALO_MENU_TOGGLE);
        showMenu.setIcon(FontAwesome.NAVICON);
        menuPart.addComponent(showMenu);

        // container for the navigation buttons, which are added by addView()
        menuItemsLayout = new CssLayout();
        menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
        menuPart.addComponent(menuItemsLayout);

        addComponent(menuPart);
    }
    /*
    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName(VALO_MENU_TOGGLE);
        //final User user = getCurrentUser();
        profileItem = settings.addItem("", new ThemeResource(
                "img/profile-pic-300px.jpg"), null);
        //updateUserName(null);
        profileItem.addSeparator();
        return settings;
            
        }
        */

    /**
     * Register a pre-created view instance in the navigation menu and in the
     * {@link Navigator}.
     *
     * @see Navigator#addView(String, View)
     *
     * @param view
     *            view instance to register
     * @param name
     *            view name
     * @param caption
     *            view caption in the menu
     * @param icon
     *            view icon in the menu
     */
    public void addView(View view, final String name, String caption,
            Resource icon) {
        navigator.addView(name, view);
        createViewButton(name, caption, icon);
    }

    /**
     * Register a view in the navigation menu and in the {@link Navigator} based
     * on a view class.
     *
     * @see Navigator#addView(String, Class)
     *
     * @param viewClass
     *            class of the views to create
     * @param name
     *            view name
     * @param caption
     *            view caption in the menu
     * @param icon
     *            view icon in the menu
     */
    public void addView(Class<? extends View> viewClass, final String name,
            String caption, Resource icon) {
        navigator.addView(name, viewClass);
        createViewButton(name, caption, icon);
    }

    private void createViewButton(final String name, String caption,
            Resource icon) {
        Button button = new Button(caption, new ClickListener() {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                navigator.navigateTo(name);

            }
        });
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        button.setIcon(icon);
        menuItemsLayout.addComponent(button);
        viewButtons.put(name, button);
    }

    /**
     * Highlights a view navigation button as the currently active view in the
     * menu. This method does not perform the actual navigation.
     *
     * @param viewName
     *            the name of the view to show as active
     */
    public void setActiveView(String viewName) {
        for (Button button : viewButtons.values()) {
            button.removeStyleName("selected");
        }
        Button selected = viewButtons.get(viewName);
        if (selected != null) {
            selected.addStyleName("selected");
        }
        menuPart.removeStyleName(VALO_MENU_VISIBLE);
    }
}