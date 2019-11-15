package com.team.seller.commons;

import android.util.Base64;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IdGenerator {

    public static String GeneratRandomID(){
       return  Base64.encodeToString(GetRandomString().getBytes(),
               Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

    }


    public static String GetRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}
