package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import static java.lang.Math.floor;

public class Utils {

    static int digitCount(int number) {
        int digits = 0;
        while (number != 0) {
            number = number / 10;
            ++digits;
        }
        return digits;
    }

    static String halfOfSpaces(String word, int maxLength) {
        String spaces = "";
        for (int i = 0; i < floor((maxLength - word.length())/2); i++) {
            spaces += " ";
        }
        return spaces;
    }

    static String spaces(String word, int maxLength) {
        String spaces = "";
        for (int i = 0; i <= maxLength - word.length(); i++) {
            spaces += " ";
        }
        return spaces;
    }

    static String date() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    public static int randomCode() {
        while (true) {
            Random rnd = new Random();
            int random = rnd.nextInt(999999);
            if (Item.findItem(Integer.parseInt(String.format("%06d", random))) == null
            && Order.findOrder(Integer.parseInt(String.format("%06d", random))) == null
            && Item.findDeletedItem(Integer.parseInt(String.format("%06d", random))) == null)
                return Integer.parseInt(String.format("%06d", random));
        }
    }

}
