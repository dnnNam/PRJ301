/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.model;

import java.io.Serializable;

/**
 *
 * @author Hi
 */
public class TblUserDTO implements Serializable{
    private String username;
    private int password;
    private String fullName;
    private boolean isRole;

    public TblUserDTO() {
    }

    public TblUserDTO(String username, int password, String fullName, boolean isRole) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.isRole = isRole;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public int getPassword() {
        return password;
    }
    
    /**
     * @param password the password to set
     */
    public void setPassword(int password) {
        this.password = password;
    }
   
    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the isRole
     */
    public boolean isIsRole() {
        return isRole;
    }

    /**
     * @param isRole the isRole to set
     */
    public void setIsRole(boolean isRole) {
        this.isRole = isRole;
    }

    
    
    
}
