/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author HP
 * @author Tamina
 */
public class Util {

    public static LocalDateTime minDateTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);

    public static ResponseObject message(String message) {
        return new ResponseObject(message);
    }
    static String regex = "\\b(.)(.*?)\\b";

    public static String UpperCaseFirstLetter(String s) {
        return Pattern.compile(regex).matcher(s).replaceAll(matche -> matche.group(1).toUpperCase() + matche.group(2));
    }

    public static long[] category_id = new long[0];

    public static void addCategory(long id) {
        long[] new_array = new long[category_id.length + 1];
        for (int i = 0; i < category_id.length; i++) {
            new_array[i] = category_id[i];
        }
        new_array[new_array.length - 1] = id;
        category_id = new_array;
    }

    public static void removeCategory(long id) {
        long[] new_array = new long[category_id.length - 1];
        int j = 0;
        for (int i = 0; i < category_id.length; i++) {
            if (category_id[i] != id) {
                new_array[j] = category_id[i];
                j++;
            }
        }
        category_id = new_array;
    }

    public static List<Long> toLongList(long[] array) {
        List<Long> list = new ArrayList(array.length);
        for (long l : array) {
            list.add((Long) l);
        }
        return list;
    }
}

record ResponseObject(String message) {

}
