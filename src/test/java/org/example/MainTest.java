package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {

    @Test
    void getDateFromStringTest() {
        String stringDate = "08.10 Воскресенье погода сегодня";
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        Matcher matcher = pattern.matcher(stringDate);
        if(matcher.find()) {
            String str = matcher.group();
            Assertions.assertEquals("08.10", str);
            return;
        }
        Assertions.assertFalse(matcher.find());
    }

}
