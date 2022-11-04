package com.github.mcnaughtondesktop;

import java.util.Optional;

public class TextParser {

    public static Optional<Integer> toInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
