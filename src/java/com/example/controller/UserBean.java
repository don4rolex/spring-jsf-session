/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.utils.SessionUtils;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andrew
 */
@Named
@SessionScoped
public class UserBean implements Serializable {

  @Autowired
  private UserService userService;

  private String firstName;
  private String lastName;
  private String username;
  private String password;

  public String saveUser() {
    User user = new User(firstName, lastName, username, password);
    userService.saveUser(user);

    return "login";
  }

  public String login() {
    User user = userService.login(username, password);
    if (user != null) {
      HttpSession session = SessionUtils.getSession();
      session.setAttribute("userId", user.getId());
      setUserBean(user);
      
      return "user";
    } else {
      FacesContext.getCurrentInstance().addMessage(
          null,
          new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid login", "Please enter correct Username and Password"));
      
      return "login";
    }
  }

  public String updateUser() {
    int userId = Integer.valueOf(SessionUtils.getUserId());
    User user = new User(firstName, lastName, username, password);
    user.setId(userId);
    userService.updateUser(user);

    return "login";
  }
  
  private void setUserBean(User user) {
    setFirstName(user.getFirstName());
    setLastName(user.getLastName());
    setUsername(user.getUsername());
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
