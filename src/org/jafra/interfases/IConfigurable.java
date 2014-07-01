package org.jafra.interfases;

import java.text.SimpleDateFormat;

public interface IConfigurable {

    String _COSFRA = "COSFRAF65T";
    String _BPCS = "BPCSF65TST";
    String _DESARROLLO = "DESARROLLO";
    SimpleDateFormat _SDFCHART = new SimpleDateFormat("yyyyMM");
    SimpleDateFormat _SDFCHARTLEGEND = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat _SDF = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat _SHF = new SimpleDateFormat("hhmmss");
    SimpleDateFormat _SGF = new SimpleDateFormat("yyyyMMdd hhmmss");
    String _BLANK = "";
    String _ROOT_IMG = "ROOTIMG";
    String _ROOT_CFDi = "S:\\Boveda Recibos CFDI 2014\\";
    String _COPY_EMAIL = "jm_nomina@jafra.com.mx";
    
    
    String _HOST_NAME = "172.16.36.12:8080";

    //Global status
    String _ENABLED = "A";
    String _DISABLE = "B";
    String _CLOSED = "C";
    String _PROGRESS = "P";

    //Estatus Usuarios
    String _USR_ENABLED = "A";
    String _USR_DISABLE = "B";

    //Email Configuration
    String SMTP_HOST_NAME = "172.16.21.15";
    int SMTP_HOST_PORT = 25;
    String SMTP_AUTH_USER = "QRQC-TEAM";
    String SMTP_AUTH_PWD = "desarrollo";
    String _SUBJECT_MAIL = "CFDi Recursos Humanos Jafra Mnf";
    String _CFDI_MESSAGE = "CM";


    //HTML CSS Email tags
    String _BODY = "<BODY style='font-size:12px; background-color: #ffffff;font-family:\'News Gothic Light\', \'Arial\',\'Arial Narrow\', serif !important;'>";
    String _BODY_CLOSED = "</BODY>";
    String _H1 = "<H1 style='border-bottom: solid 2px #ffffff; clear: both;font-weight: bold; letter-spacing: -1px; color: #9297CB;'>";
    String _H1_CLOSED = "</H1>";
    String _H2 = "<H2 style='font-weight: normal; letter-spacing: -1px; color: #9297CB;'>";
    String _H2_CLOSED = "</H2>";
    String _P = "<P style='color: rgb(170,115,177);'>";
    String _P_CLOSED = "</P>";
    String _TABLE = "<TABLE style='border:none'>";
    String _TABLE_BODY = "<TABLE style='border:none; margin-left:20%;'>";
    String _TABLE_CLOSED = "</TABLE>";
    String _HR = "<HR style='border: 0; height: 0; box-shadow: 0 1px 5px 1px purple;'>";
    String _P_ADVICE = "<P style='color: rgb(170,115,177); font-size:8px;'>";
    // Graficas
    String _LOW = "LOW";
    String _MEDIUM = "MEDIUM";
    String _HIGH = "HIGH";
    String _OPEN = "OPEN";
    String _PROG = "PROGRESS";
    String _VALID = "VALID";
    String _CLOSE = "CLOSED";
    
}
