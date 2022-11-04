package com.github.mcnaughtondesktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

class McNaughtonController {
    TextField taskQuantityField = new TextField();
    TextField machineQuantityField = new TextField();

    VBox scrollableTaskExecutionTimeInputs = new VBox();
    List<TextField> tasksExecutionTimes = new ArrayList<>();

    Button executeButton = new Button();

    McNaughtonController() {
        this.setTextFieldTypeNumber(this.taskQuantityField);
        this.addListRenderingListener(this.taskQuantityField);
        this.setTextFieldTypeNumber(this.machineQuantityField);

        this.taskQuantityField.setOnInputMethodTextChanged((event) -> {
            setTaskInputs(5);
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
    }

    private void addListRenderingListener(final TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.scrollableTaskExecutionTimeInputs.getChildren().clear();
            Integer integer = Integer.valueOf(textField.getText());

            for (int i = 0; i < integer; i++) {
                Label machineQuantityLabel = new Label("Czas wykonania zadania " + (i + 1));
                this.scrollableTaskExecutionTimeInputs.getChildren().add(machineQuantityLabel);

                TextField machineQuantityField = new TextField();
                this.scrollableTaskExecutionTimeInputs.getChildren().add(machineQuantityField);
            }
        });
    }
}
