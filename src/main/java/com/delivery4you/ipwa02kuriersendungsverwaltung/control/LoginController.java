package com.delivery4you.ipwa02kuriersendungsverwaltung.control;

import com.delivery4you.ipwa02kuriersendungsverwaltung.dao.UserDAO;
import com.delivery4you.ipwa02kuriersendungsverwaltung.model.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable
{
    private String username;
    private String password;
    private UserDAO userDAO = new UserDAO();


    private static User loggedinUser;

    public String login() {
        this.reset();
        return "login";
    }

    /**
     * Calls the method to check if the login credentials are valid.
     * If they are, the User is logged in and redirected to the dashboard depending on his role.
     * If the credentials are nivalid the User will be redirected to the login page.
     *
     * @return
     */
    public String validate()
    {
        if (check()) {
            switch (this.getLoggedinUser().getRole()) {
                case CLIENT:
                    return "create-shipment";
                case COURIER:
                    return "collectable-shipments";
                case MANAGER:
                    return "shipments";
                default:
                    return "index";
            }
        } else {
            this.reset();

            FacesContext.getCurrentInstance().addMessage("login",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültige Nutzerdaten", ""));
        }
        return null;
    }

    public String logout() {
        this.reset();

        FacesContext.getCurrentInstance().addMessage("login",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolgreich ausgeloggt", ""));

        return "login";
    }

    private void reset() {
        this.loggedinUser = null;
        this.username = null;
        this.password = null;
    }

    private boolean check()
    {
        if (isValidUser(username, password)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * If a User with the given username exists it will be retrieved.
     * Then the saved  and the inputted passwords are compared, logging the User in if they match.
     *
     * @param username
     * @param password
     * @return
     */
    private boolean isValidUser(String username, String password)
    {
        User user = userDAO.findByName(username);

        if (user != null && user.getPassword().equals(password)) {
            this.loggedinUser = user;
            return true;
        }
        return false;

    }

    public static User getLoggedinUser()
    {
        return loggedinUser;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
