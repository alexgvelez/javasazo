/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jafra.interfases;

import java.io.File;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ICFDi {
    public File findFolderByWeek(String week);

    /**
     *
     * @param folderWeek
     * @param employeeId
     * @return
     */
    public List<File>  readFolderByWeek(File folderWeek, String employeeId);
    
}
