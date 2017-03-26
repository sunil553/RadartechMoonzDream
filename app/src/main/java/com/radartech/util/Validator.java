package com.radartech.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 25 Oct 2016 2:07 PM.
 *
 * @author PurpleTalk India Pvt. Ltd.
 */

public class Validator {

    private static final String SPACE = " ";
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[A-Za-z])(?=\\S+$).{8,15})";

    public static boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidUserName(String userName) {
        if (userName.length() < 6) {
            return false;
        }
        return true;
    }
}

