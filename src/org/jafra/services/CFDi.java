/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jafra.services;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import org.jafra.interfases.ICFDi;
import org.jafra.interfases.IConfigurable;

/**
 *
 * @author Administrador
 */
public class CFDi implements ICFDi, IConfigurable{

    @Override
    public File findFolderByWeek(String week) {
        
        File fileToFind = new File(_ROOT_CFDi);
        File[] list = fileToFind.listFiles((File dir, String name) -> name.matches(week));

        if(list.length > 0)
            return list[0];
        return null;
    }

    @Override
    public List<File> readFolderByWeek(File folderWeek, String employeeId) {
        File[] list = folderWeek.listFiles((File dir, String name) -> name.matches("[^_]*" + employeeId + ".xml"));
        List<File> listFromArray = Arrays.asList(list);
             
        return listFromArray;
    }
    
    
    
}
