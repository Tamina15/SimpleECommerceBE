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
    static Pattern pattern = Pattern.compile(regex);
    
    public static String UpperCaseAllFirstLetter(String s) {
        return pattern.matcher(s).replaceAll(matche -> matche.group(1).toUpperCase() + matche.group(2));
    }
    
    public static List<Long> toLongList(long[] array) {
        List<Long> list = new ArrayList(array.length);
        for (long l : array) {
            list.add((Long) l);
        }
        return list;
    }
}
