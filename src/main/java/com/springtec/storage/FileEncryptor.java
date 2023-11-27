package com.springtec.storage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class FileEncryptor {

   public static String encryptFileName(String originalFileName){
         // Generar un UUID único
         String uuid = UUID.randomUUID().toString();
         // Combinar UUID
         return uuid + ".wzt";
   }

}
