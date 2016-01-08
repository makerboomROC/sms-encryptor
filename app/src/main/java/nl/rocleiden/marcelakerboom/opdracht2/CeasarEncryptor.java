package nl.rocleiden.marcelakerboom.opdracht2;

import java.lang.reflect.Array;

/**
 * Created by Marcel on 8-1-2016.
 */
public class CeasarEncryptor {

    public String decrypt(CharSequence target, int salt) {
        int[] salts = {salt};
        return decrypt(target, salts);
    }

    public String decrypt(CharSequence target, int[] salts) {
        int subjectLength = target.length();
        int saltsLength = salts.length;
        char[] result = new char[subjectLength];

        for (int i = 0; i < subjectLength; i++) {
            int salt = salts[i % saltsLength];
            result[i] = decryptChar(target.charAt(i), salt);
        }

        return new String(result);
    }

    public char decryptChar(char subject, int salt) {
        for (int i = 0; i < salt; i++) {
            if (subject > 'A' && subject <= 'Z') subject--;
            else if (subject == 'A') subject = 'Z';
            else if (subject > 'a' && subject <= 'z') subject--;
            else if (subject == 'a') subject = 'z';
        }
        return subject;
    }


    public String encrypt(CharSequence target, int salt) {
        int[] salts = {salt};
        return encrypt(target, salts);
    }

    public String encrypt(CharSequence subject, int[] salts) {
        int subjectLength = subject.length();
        int saltsLength = salts.length;
        char[] result = new char[subjectLength];

        for (int i = 0; i < subjectLength; i++) {
            int salt = salts[i % saltsLength];
            result[i] = encryptChar(subject.charAt(i), salt);
        }

        return new String(result);
    }

    public char encryptChar(char subject, int salt) {
        for (int i = 0; i < salt; i++) {
            if (subject >= 'A' && subject < 'Z') subject++;
            else if (subject == 'Z') subject = 'A';
            else if (subject >= 'a' && subject < 'z') subject++;
            else if (subject == 'z') subject = 'a';
        }
        return subject;
    }
}
