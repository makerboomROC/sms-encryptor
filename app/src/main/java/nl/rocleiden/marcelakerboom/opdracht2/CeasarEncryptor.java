package nl.rocleiden.marcelakerboom.opdracht2;

/**
 * Created by Marcel on 8-1-2016.
 */
public class CeasarEncryptor {

    public String decrypt(CharSequence target, int salt) {
        int length = target.length();
        char[] result = new char[length];

        for (int i = 0; i < length; i++) {
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


    public String encrypt(CharSequence subject, int salt) {
        int length = subject.length();
        char[] result = new char[length];

        for (int i = 0; i < length; i++) {
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
