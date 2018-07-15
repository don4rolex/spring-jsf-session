/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Andrew
 */
@Service
@Transactional
public class UserService {

  @Autowired
  private SessionFactory sessionFactory;
  
  public User login(String username, String password) {
    final Session session = sessionFactory.getCurrentSession();
    final User user = (User) session.createQuery("FROM User u "
        + "WHERE u.username = :username "
        + "AND u.password = :password")
        .setParameter("username", username)
        .setParameter("password", password)
        .uniqueResult();
    
    return user;
  }
  
  public void saveUser(User user) {
    final Session session = sessionFactory.getCurrentSession();
    session.save(user);
  }
  
  public void updateUser(User user) {
    final Session session = sessionFactory.getCurrentSession();
    session.update(user);
  }
}
