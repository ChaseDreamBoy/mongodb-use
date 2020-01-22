package com.xh.mongodb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JavaTest
 *
 * @author xiaohe
 */
public class JavaTest {

    public static void main(String[] args) {
        convertDate();
    }

    private static void convertDate() {
        String dateStr = "2019-12-31T10:10:10.675Z";
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        System.out.println("parse to localDateTime is : " + localDateTime);
        String pattern2 = "yyyy-MM-dd HH:mm:ss";
        String s = localDateTime.format(DateTimeFormatter.ofPattern(pattern2));
        System.out.println("format result is : " + s);
    }

}
