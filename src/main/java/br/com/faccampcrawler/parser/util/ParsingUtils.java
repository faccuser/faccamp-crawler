package br.com.faccampcrawler.parser.util;

public class ParsingUtils {

    public static String removeWhitespace(String string) {
        return string.replaceAll("\u00a0", "");
    }
}
