package component;

public class Transliterator {

    public static final String TRANSLITERATE_ID = "Any-Latin; Latin-ASCII";
    public static String NORMALIZE_ID = "[:Punctuation:] Remove";

    public static String transliterate(String text){
        return com.ibm.icu.text.Transliterator
                .getInstance(TRANSLITERATE_ID)
                .transliterate(text);
    }

    public static String searchNormalize(String text){
        return com.ibm.icu.text.Transliterator
                .getInstance(TRANSLITERATE_ID + "; " + NORMALIZE_ID)
                .transliterate(text)
                .trim()
                .replaceAll(" +", " ")
                .toLowerCase()
                .replaceAll("\\b\\w{1,2}\\b\\s?", "");
    }
}
