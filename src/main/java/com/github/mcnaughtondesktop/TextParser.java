package com.github.mcnaughtondesktop;

import javafx.scene.control.TextField;

import java.util.Optional;

public class TextParser {

    public static Optional<Integer> toInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Integer toIntOr(TextField textField, int def) {
        return Optional.ofNullable(textField)
            .map(TextField::getText)
            .filter(s -> !s.isBlank())
            .flatMap(TextParser::toInt)
            .orElse(def);
    }
}
