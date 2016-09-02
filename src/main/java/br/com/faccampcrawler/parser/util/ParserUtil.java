package br.com.faccampcrawler.parser.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserUtil {

    public static String removeWhitespace(String string) {
        return string.replaceAll("\u00a0", "");
    }

    public static Date stringToDate(String dateAsString) {
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.parse(dateAsString);
        } catch (ParseException e) {
            //TODO: Criar os logs
            System.out.println("Could not parse date [" + dateAsString + "].\nException: " + e.getMessage());
            return null;
        }
    }
}
