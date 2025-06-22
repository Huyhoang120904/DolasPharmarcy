package com.hoanghocdev.dolaspharmacy.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugUtils {

    private static final Pattern WHITESPACE= Pattern.compile("[\\s]");
    private static final Pattern NONLATIN= Pattern.compile("[^\\w-]");

    public static String toSlug(String name) {
        String noWhiteSpace = WHITESPACE.matcher(name.toLowerCase(Locale.ROOT)).replaceAll("-");
        String normalizedName = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        normalizedName = normalizedName.replaceAll("[đĐ]", "d");

        String nonLatin = NONLATIN.matcher(normalizedName).replaceAll("");
        return nonLatin;
    }
}
