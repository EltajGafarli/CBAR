package org.example.cbarcurrencyproject.util;

public class JavaMailSenderConstants {

    public static final String SEND_TO_USER_CONTENT = "Hi,user [[USER_KEY]],<br>"
            + "You have been registered by admin.<br>"
            + "Your admin is: [[ADMIN_KEY]].<br>"
            + "Please,ask the credentials from your admin.<br>"
            + "Thank you.<br>";
    public static final String ADMIN_REGISTRATION_CONTENT = "Hi,admin [[ADMIN_KEY]],<br>"
            + "Your verification code: ";
    public static final String USER_REGISTRATION_CONTENT = "Hi,user [[USER_KEY]],<br>"
            + "Your verification code: ";
    public static final String ADMIN_PASSWORD_RESET_TOKEN = "Hi,admin [[ADMIN_KEY]],<br>"
            + "Your token for reset password: ";
    public static final String USER_PASSWORD_RESET_TOKEN = "Hi,user [[USER_KEY]],<br>"
            + "Your token for reset password: ";
    public static final String SEND_TO_USER_SUBJECT = "Welcome!";
    public static final String SEND_TO_ADMIN_SUBJECT = "Welcome,admin!";
    public static final String USER_KEY = "[[USER_KEY]]";
    public static final String ADMIN_KEY = "[[ADMIN_KEY]]";
    /*Write your email here
    For example,i write my personal email here:
    * */
    public static final String SENDER_ADDRESS = "todolistorganization@gmail.com";
    public static final String SENDER_NAME = "Auth App";

    private JavaMailSenderConstants() {

    }
}