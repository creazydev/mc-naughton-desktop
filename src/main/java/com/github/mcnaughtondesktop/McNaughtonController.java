package com.github.mcnaughtondesktop;

import com.github.mcnaughtondesktop.model.GanttChart;
import com.github.mcnaughtondesktop.model.Machine;
import com.github.mcnaughtondesktop.model.McNaughtonResult;
import com.github.mcnaughtondesktop.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class McNaughtonController {
    TextField taskQuantityField = new TextField();
    TextField machineQuantityField = new TextField();

    VBox taskInputsWrapper = new VBox();

    Button executeButton = new Button();

    Text infoText = new Text();

    GanttChart<Number, String> chart = new GanttChart<>(new NumberAxis(), new CategoryAxis());

    McNaughtonController() {
        this.setTextFieldTypeNumber(this.taskQuantityField);
        this.addListRenderingListener(this.taskQuantityField);
        this.setTextFieldTypeNumber(this.machineQuantityField);

        this.executeButton.setOnMouseClicked((event) -> this.runAlgorithm());
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
            final int prevQuantity = this.taskInputsWrapper.getChildren().size() / 2;
            final ObservableList<Node> inputs = this.taskInputsWrapper.getChildren();

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

    private void runAlgorithm() {
        int machineCount = TextParser.toIntOr(this.machineQuantityField, 0);
        List<Task> tasks = getTasks();
        CmaxCalculator calculator = new CmaxCalculator(machineCount, tasks);
        McNaughtonScheduler scheduler = new McNaughtonScheduler(calculator.getResult(), machineCount, tasks);
        this.infoText.setText(calculator.getResultString());
        this.renderChart(new McNaughtonResult(calculator.getResult(), scheduler.schedule()));
    }

    private List<Task> getTasks() {
        AtomicInteger lastId = new AtomicInteger(1);
        return this.taskInputsWrapper.getChildren()
                .stream().sequential()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .map(field -> new Task(lastId.getAndIncrement(), TextParser.toInt(field.getText()).orElseThrow()))
                .collect(Collectors.toList());
    }

    private void renderChart(McNaughtonResult result) {
        this.chart.getData().clear();

        final CategoryAxis yAxis = (CategoryAxis) this.chart.getYAxis();

        yAxis.setCategories(FXCollections.observableArrayList(
            result.getMachines().stream().map(Machine::toString).collect(Collectors.toList())
        ));

        this.chart.setTitle("Przydział zadań");

        result.getMachines().stream().sequential().forEachOrdered(machine -> {
            XYChart.Series<Number, String> series = new XYChart.Series<>();

            series.getData().addAll(
                machine.getTasks()
                .stream().sequential()
                .map(task -> new XYChart.Data<Number, String>(0, machine.toString(), new GanttChart.ExtraData(task.getDuration(), "status-red")))
                .toList()
            );

            this.chart.getData().add(series);
        });
    }
}
