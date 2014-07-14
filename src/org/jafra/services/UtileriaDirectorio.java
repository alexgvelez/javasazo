package org.jafra.services;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class UtileriaDirectorio {

    
//  private static String folderName = new String("R:\\Boveda Organizada");
    private String folderName = "C:\\Boveda Organizada\\";
    File folder = new File(folderName);

    public void crearCarpeta() {

        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Archivo creado en " + folder.getAbsolutePath());
            }
        }
    }

    public void listaDirectorio() throws IOException {   
        File dir = new File("R:\\Boveda Recibos CFDI 2014");

        for (File miFile : dir.listFiles()) {
            if (!miFile.isDirectory()) {
                Integer Point = miFile.getName().indexOf(".");
                String FileName = miFile.getName().substring(0, Point);

                if ((FileName.substring(0, 2).equals("SP")) || (FileName.substring(0, 2).equals("EP")) || (FileName.substring(0, 2).equals("SF")) || (FileName.substring(0, 2).equals("EF"))) {
                    File CreaNewFolder4 = new File(folderName + "\\" + (FileName.substring(0, 4)).trim() + "-" +(FileName.substring(4, 8)).trim());
                    CreaNewFolder4.mkdir();
                    //File newFile = new File(CreaNewFolder4 + "\\" + FileName + ".xml");
                    //miFile.renameTo(newFile);
                     FileUtils.copyFileToDirectory(miFile, CreaNewFolder4);
                } else {
                    File CreaNewFolder3 = new File(folderName + "\\" + (FileName.substring(0, 3)).trim() +  "-" + (FileName.substring(3, 7)).trim());
                    CreaNewFolder3.mkdir();
                    FileUtils.copyFileToDirectory(miFile, CreaNewFolder3);
                    //File newFile = new File(CreaNewFolder3 + "\\" + FileName + ".xml");
                    //miFile.renameTo(newFile);
                }
            }
        }
    }

  }

