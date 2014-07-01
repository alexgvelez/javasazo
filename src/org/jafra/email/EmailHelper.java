package org.jafra.email;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.jafra.dao.EmailDao;
import org.jafra.entities.Email;
import org.jafra.interfases.IConfigurable;

public class EmailHelper implements Serializable, IConfigurable {

    public static final long serialVersionUID = 24362462L;
    private Email email;
    private String type;
    private String employee;
    private String employeeEmail;
    private final EmailDao emailDao;
    private InternetAddress[] multipleRecipients;
    private List<Email> listOfAddresses;
    
    EmailAttachment attachment = new EmailAttachment();
    
    protected Logger log = Logger.getLogger("org.jafra.claims.clases");

    public EmailHelper() {
        email = new Email();
        email.setSubject(_SUBJECT_MAIL);
        //emailDao = UserBean.getCtx().getBean("emailDao", EmailDao.class);
        emailDao = new EmailDao();
        listOfAddresses = new ArrayList<>();
    }

    public EmailHelper(String type, boolean isQRQCMailRequired) {
        this.type = type;
        email = new Email();
        email.setSubject(_SUBJECT_MAIL);
        emailDao = new EmailDao();
        listOfAddresses = new ArrayList<>();
    }

    public void sendEmailByUserType(String userId, String userName, String additionalInformation) {
        //this.getAddressByType(type);

        listOfAddresses = getApacheAddressesByType(type);
        this.getMessage(_CFDI_MESSAGE, userId, userName, additionalInformation);
        this.sendNoAttachment();

    }
    
    public void sendEmailWithCFDi( String additionalInformation) {

        this.getMessage(_CFDI_MESSAGE, employee, employeeEmail, additionalInformation);
        this.sendAttachedEmailForCFDi();

    }

    public void sendApacheEmail() {
        try {
            HtmlEmail emailA = new HtmlEmail();
            emailA.setHostName(SMTP_HOST_NAME);
            emailA.setFrom("desarrollo@jafra.com.mx", "QRQC SYSTEM");
            
            for (Email emailAddresses : listOfAddresses) {
                emailA.addTo(emailAddresses.getAddress(), emailAddresses.getAdressName());
            }
            emailA.setSubject(_SUBJECT_MAIL);
            
            // embed the image and get the content id
            URL url = new URL("http://" + _HOST_NAME + "/JafraClaims/images/company/jaframanufacturing.jpg");
            String cid = emailA.embed(url, "Jafra S.A. de C.V");
            
            StringBuilder htmlMsg = new StringBuilder().append("<html>").append(_BODY);
            htmlMsg.append(_TABLE).append("<TH><TD><img src=\"cid:").append(cid);
            htmlMsg.append("\" style='width:180px; height:100px;'>");
            htmlMsg.append("</TD><TD>");
            htmlMsg.append(_H1).append("QRQC Management System").append(_H1_CLOSED);
            htmlMsg.append("</TD></TH>").append(_TABLE_CLOSED);
            htmlMsg.append(_TABLE_BODY);
            htmlMsg.append(email.getMeesage()).append("</html>");
            
            // set the html message
            emailA.setHtmlMsg(htmlMsg.toString());            
            
            // set the alternative message
            emailA.setTextMsg("Your email client does not support HTML messages");
            
            emailA.send();
        } catch (EmailException ex) {
            Logger.getLogger(EmailHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EmailHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendAttachedEmailForCFDi() {
        try {
            HtmlEmail emailA = new HtmlEmail();
            emailA.setHostName(SMTP_HOST_NAME);
            emailA.setFrom("desarrollo@jafra.com.mx", "SISTEMA ENVIO CFDi JAFRA MNF");
            
            emailA.addTo(employeeEmail, employee);
            emailA.addTo(_COPY_EMAIL, "Recursos humanos Jafra");
            emailA.setSubject(_SUBJECT_MAIL);
            
            // embed the image and get the content id
            URL url = new URL("http://" + _HOST_NAME + "/JafraClaims/images/company/jaframanufacturing.jpg");
            String cid = emailA.embed(url, "Jafra S.A. de C.V");
            
            StringBuilder htmlMsg = new StringBuilder().append("<html>").append(_BODY);
            htmlMsg.append(_TABLE).append("<TH><TD><img src=\"cid:").append(cid);
            htmlMsg.append("\" style='width:180px; height:100px;'>");
            htmlMsg.append("</TD><TD>");
            htmlMsg.append(_H1).append("SISTEMA ENVIO CFDi JAFRA MNF").append(_H1_CLOSED);
            htmlMsg.append("</TD></TH>").append(_TABLE_CLOSED);
            htmlMsg.append(_TABLE_BODY);
            htmlMsg.append(email.getMeesage()).append("</html>");
            
            // set the html message
            emailA.setHtmlMsg(htmlMsg.toString());            
            emailA.attach(attachment);
            
            // set the alternative message
            emailA.setTextMsg("Your email client does not support HTML messages");
            
            emailA.send();
        } catch (EmailException ex) {
            Logger.getLogger(EmailHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EmailHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * Enviamos un correo al usuario sin attachments
     *
     */
    public void sendNoAttachment() {
        try {
            //Configuramos las propiedades del email
            Properties props = new Properties();

            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.setProperty("mail.smtp.port", Integer.toString(SMTP_HOST_PORT));
            props.put("mail.smtps.auth", "false");

            Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(false);
            Transport transport = mailSession.getTransport();

            //Definimos el mensaje
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(SMTP_AUTH_USER));

            message.setRecipients(Message.RecipientType.TO, this.multipleRecipients);

            message.setSubject(_SUBJECT_MAIL);

            //message.setContent("This is a test", "text/plain");
            message.setContent(email.getMeesage(), "text/html; charset=ISO-8859-1");
            /*
             // Create the message part
             BodyPart messageBodyPart = new MimeBodyPart();
            
             // Fill the message
             messageBodyPart.setText(this.getComment());
            
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(messageBodyPart);
            
             // Put parts in message
             message.setContent(multipart);
             *
             */

            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, "", "");

            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(EmailHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Enviamos correo con attachment
     *
     * @param fileName
     * @throws Exception
     */
    public void enviar(String fileName) throws Exception {
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
        // props.put("mail.smtps.quitwait", "false");

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        //Definimos el mensaje
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(SMTP_AUTH_USER));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(email.getsTo()));

        message.setSubject(email.getSubject());
            //message.setContent("This is a test", "text/plain");

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText(email.getMeesage());

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(fileName);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        //transport.connect
        //  (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Llnamos nuestro recipiente de correos con las dirección por tipo de
     * usuario
     *
     * @param type
     * @return
     */
    public int getAddressByType(String type) {
        int indice = 0;
        try {
            List<Email> addresses = emailDao.getAdressesByUserType(type);

            if (addresses.size() > 0) {
                this.multipleRecipients = new InternetAddress[addresses.size()];
            }

            for (Email address : addresses) {
                this.multipleRecipients[indice] = new InternetAddress(address.getAddress().trim(), address.getAdressName().trim());
                indice++;
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmailHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return indice;
    }

    public List<Email> getApacheAddressesByType(String type) {
        List<Email> addresses = emailDao.getAdressesByUserType(type);
        return addresses;
    }
    

    /**
     * Obtenemos la direccion de correo por usuario
     *
     * @param userId
     * @return
     */
    public int getAddressByUser(String userId) {
        int indice = 0;

        try {
            listOfAddresses.clear();
            
            email = emailDao.getAddressByUser(userId);
            listOfAddresses.add(email);
            
            if (!email.getAddress().equals("")) {
                this.multipleRecipients = new InternetAddress[1];
                this.multipleRecipients[indice] = new InternetAddress(email.getAddress().trim(), email.getAdressName().trim());
                indice++;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmailHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return indice;
    }

    /**
     * Obtenemos el mensaje de acuerdo a la accion
     *
     * @param topic
     * @param userId
     * @param userName
     * @param additionalInformation
     */
    public void getMessage(String topic, String userId, String userName, String additionalInformation) {
        StringBuilder msg;
        msg = new StringBuilder();

        if (topic.equals(_CFDI_MESSAGE)) {

            //Se realiza la requesición inicial de seteo de password
            msg.append("<TR><TD colspan='2'>");
            msg.append(_H2).append("Buen dia: ").append(userId).append(_H2_CLOSED);
            msg.append("</TD></TR>");
            msg.append("<TR><TD colspan='2'>");
            msg.append(_P).append("Le enviamos los xml o cfdi(Comprobante Fiscal Digital por internet)").append(_P_CLOSED);
            msg.append("</TD></TR>");
            msg.append(_P).append("Pertenecientes al periodo:").append(_P_CLOSED);
            msg.append("</TD><TD>");
            msg.append(_P).append(additionalInformation).append(_P_CLOSED);
            msg.append("</TD></TR>");

        } 
        msg.append(_TABLE_CLOSED).append(getFooter()).append(_BODY_CLOSED);
        email.setMeesage(msg.toString());
    }

    private String getFooter() {
        StringBuilder footer = new StringBuilder();

        footer.append("<BR><BR>");
        footer.append(_P).append("<B><I>").append("Agradecemos su atención <BR> Saludos cordiales </I></B>").append(_P_CLOSED);
        footer.append(_HR);
        footer.append(_P).append("Jafra Manufacturing S.A. de C.V.").append("<BR>");
        footer.append("Av. La Estacada 201, Parque Industrial Querétaro").append("<BR>");
        footer.append("Email: yomara_soto@jafra.com.mx, mariano_nieto@jafra.com.mx").append("<BR>");
        footer.append("Tel. +52 442 101 5000").append("<BR>");
        footer.append("Area: Recursos Humanos").append("<BR>");
        footer.append("<B>Por favor no contestar este correo</B>").append(_P_CLOSED);
        footer.append("<BR>").append(_P_ADVICE).append("This message is");
        footer.append("intended only for the use of the individual/entity to which it is ");
        footer.append("addressed. In case you are not the intended recipient please be so kind");
        footer.append("to return this message to us by e-mail. Thank you. This message may contain ");
        footer.append("confidential or privileged information. If you are not the intended recipient,");
        footer.append("we hereby notify you that any dissemination, distribution, copying or printing of");
        footer.append(" this communication is strictly prohibited.").append(_P_CLOSED);

        return footer.toString();
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public EmailAttachment getAttachment() {
        return attachment;
    }

    public void setAttachment(EmailAttachment attachment) {
        this.attachment = attachment;
    }
    
    
    
}
