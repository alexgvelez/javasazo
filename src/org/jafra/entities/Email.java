/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jafra.entities;

import java.io.Serializable;


/**
 *
 * @author Administrador
 */
public class Email implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String sUser;
    private String sPassword;
    private String sReport;
    private String sDate;
    private String sRDescription;
    private String sSender;
    private String sRQuery;
    private String sTo;    
    private String Subject;
    private String meesage;
    
    private String address;
    private String adressName;

    public Email() {
    }

    
    public String getsUser() {
        return sUser;
    }

    public void setsUser(String sUser) {
        this.sUser = sUser;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getsReport() {
        return sReport;
    }

    public void setsReport(String sReport) {
        this.sReport = sReport;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsRDescription() {
        return sRDescription;
    }

    public void setsRDescription(String sRDescription) {
        this.sRDescription = sRDescription;
    }

    public String getsSender() {
        return sSender;
    }

    public void setsSender(String sSender) {
        this.sSender = sSender;
    }

    public String getsRQuery() {
        return sRQuery;
    }

    public void setsRQuery(String sRQuery) {
        this.sRQuery = sRQuery;
    }

    public String getsTo() {
        return sTo;
    }

    public void setsTo(String sTo) {
        this.sTo = sTo;
    }
   
    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getMeesage() {
        return meesage;
    }

    public void setMeesage(String meesage) {
        this.meesage = meesage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdressName() {
        return adressName;
    }

    public void setAdressName(String adressName) {
        this.adressName = adressName;
    }

    

    
    
    
    
}
