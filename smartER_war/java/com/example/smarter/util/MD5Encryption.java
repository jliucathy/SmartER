package com.example.smarter.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MD5Encryption {
    public static void main(String[] args)
    {
        System.out.print("Enter the password:");
        Scanner sc = new Scanner(System.in);
        String passwordToHash = sc.nextLine();
        String generatedPassword = null;
        try {
            // MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //password bytes to digest
            md.update(passwordToHash.getBytes());
            //hash's bytes
            byte[] bytes = md.digest();
            //Convert it to hexadecimal
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
    }

}