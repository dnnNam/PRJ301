/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package namnd.registration;

import java.io.Serializable;

/**
 *
 * @author Hi
 */
public class RegistrationDAO implements Serializable{
    // first step implement serializable because all object inside server convert 
    // format bit stream , bytestream 
    
    // method check account have been existed 
    public boolean checkLogin(String username , String password){
        // create variable boolean to help finished function 
        boolean result = false;
        // connect BD by helperDB 
    }
}
