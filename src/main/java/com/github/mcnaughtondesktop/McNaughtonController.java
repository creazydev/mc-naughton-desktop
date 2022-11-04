package com.github.mcnaughtondesktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.controlsfx.control.tableview2.filter.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

class McNaughtonController {
    TextField taskQuantityField = new TextField();
    TextField machineQuantityField = new TextField();

    VBox scrollableTaskExecutionTimeInputs = new VBox();

    Button executeButton = new Button();

    McNaughtonController() {
        this.setTextFieldTypeNumber(this.taskQuantityField);
        this.addListRenderingListener(this.taskQuantityField);
        this.setTextFieldTypeNumber(this.machineQuantityField);

        this.taskQuantityField.setOnInputMethodTextChanged((event) -> {
            Optional.ofNullable(this.taskQuantityField.getText())
                .filter(s -> !s.isBlank())
                .flatMap(TextParser::toInt)
                .filter(v -> v >= 0)
                .ifPresent(this::setTaskInputs);
        });

        this.executeButton.setOnMouseClicked((event) -> {
            this.taskQuantityField.setText("0");
        });
    }

    void setTaskInputs(int quantity) {

    }

    private void setTextFieldTypeNumber(final TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        textField.setText("0");
    }

    private void addListRenderingListener(final TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            final int prevQuantity = this.scrollableTaskExecutionTimeInputs.getChildren().size() / 2;
            final ObservableList<Node> inputs = this.scrollableTaskExecutionTimeInputs.getChildren();

            TextParser.toInt(newValue).ifPresentOrElse(
                newQuantity -> {

                    // Clear all
                    if (newQuantity <= 0) {
                        inputs.clear();

                    // Push items
                    } else if (prevQuantity < newQuantity) {
                        for (int i = prevQuantity + 1; i <= newQuantity; i++) {
                            Label label = new Label("Czas wykonania zadania " + i);
                            inputs.add(label);

                            TextField field = new TextField();
                            this.setTextFieldTypeNumber(field);
                            inputs.add(field);
                        }

                    // Pop items
                    } else if (prevQuantity > newQuantity) {
                        inputs.removeIf(child -> inputs.indexOf(child) / 2 > newQuantity - 1);
                    }
                },
                inputs::clear
            );
        });
    }
}
