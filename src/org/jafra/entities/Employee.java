/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jafra.entities;

import org.jafra.interfases.IConfigurable;

/**
 *
 * @author Administrador
 */
public class Employee implements IConfigurable {
    private Integer id;
    private String name;
    private String email;
    private String status;

    public Employee() {
        this.id = 0;
        this.status = _DISABLE;
    }

    public Employee(Integer id, String name, String email, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name + ", email=" + email + ", status=" + status + '}';
    }
    
    
}
