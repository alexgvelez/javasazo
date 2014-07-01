/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jafra.interfases;

import java.util.List;
import org.jafra.entities.Email;

/**
 *
 * @author Administrador
 */
public interface IEmailDao {

    /**
     * Obtenemos la lista de correos a partir del tipo de usuario
     *
     * @param userType
     * @return
     */
    List<Email> getAdressesByUserType(String userType);

    /**
     * Obtenemos la direccion de email del usuario
     *
     * @param userId
     * @return
     */
    Email getAddressByUser(String userId);

}
