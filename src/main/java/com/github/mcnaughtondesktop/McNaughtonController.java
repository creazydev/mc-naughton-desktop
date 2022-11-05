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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

class McNaughtonController {
    private final Random random = new Random();

    TextField taskQuantityField = new TextField();
    TextField machineQuantityField = new TextField();

    VBox taskInputsWrapper = new VBox();

    Button executeButton = new Button();

    Text infoText = new Text();

    GanttChart<Number, String> chart = new GanttChart<>(new NumberAxis(), new CategoryAxis());

    ObservableList<String> labels = FXCollections.observableArrayList(new ArrayList<>());
    McNaughtonController() {
        this.setTextFieldTypeNumber(this.taskQuantityField, 30);
        this.addListRenderingListener(this.taskQuantityField);
        this.setTextFieldTypeNumber(this.machineQuantityField, 20);

        this.executeButton.setOnMouseClicked((event) -> this.runAlgorithm());
    }

    private void setTextFieldTypeNumber(final TextField textField, int maxValue) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                final String value = newValue.replaceAll("[^\\d]", "");

                TextParser.toInt(value)
                    .filter(v -> v > 0)
                    .map(v -> v > maxValue ? maxValue : v)
                    .map(Integer::toUnsignedString)
                    .ifPresentOrElse(textField::setText, () -> textField.setText("1"));
            } else if (Objects.equals(newValue, "0")) {
                textField.setText("1");
            } else {
                TextParser.toInt(newValue)
                    .map(v -> v > maxValue ? maxValue : v)
                    .map(Integer::toUnsignedString)
                    .ifPresent(textField::setText);
            }
        });

        textField.setText("1");
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
                            this.setTextFieldTypeNumber(field, 1000);
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
        final CategoryAxis yAxis = (CategoryAxis) this.chart.getYAxis();
        this.labels.clear();
        this.chart.getData().clear();

        this.labels.addAll(result.getMachines().stream().map(Machine::toString).collect(Collectors.toList()));
        this.chart.setTitle("Przydział zadań");

        Map<Integer, String> tasksColors = result.getMachines().stream()
            .map(Machine::getTasks)
            .flatMap(Collection::parallelStream)
            .map(Task::getId)
            .collect(Collectors.toSet())
            .stream()
            .collect(Collectors.toMap(v -> v, v -> this.getRandomColor()));

        List<List<XYChart.Data<Number, String>>> machines = result.getMachines().stream()
            .map(machine -> {
                AtomicReference<Double> counter = new AtomicReference<>(0.0);

                return machine.getTasks()
                    .stream().sequential()
                    .map(task -> {
                        XYChart.Data<Number, String> obj = new XYChart.Data<>(
                            counter.get(),
                            machine.toString(),
                            new GanttChart.ExtraData(task.getDuration(), tasksColors.get(task.getId())));

                        counter.getAndUpdate(val -> val + task.getDuration());
                        return obj;
                    })
                    .collect(Collectors.toList());
            })
            .collect(Collectors.toList());


        machines.stream().sequential().forEachOrdered(machineTasks -> {
            XYChart.Series<Number, String> series = new XYChart.Series<>();
            series.getData().addAll(machineTasks);
            this.chart.getData().add(series);
        });
    }

    private String getRandomColor() {
        int nextInt = random.nextInt(0xffffff + 1);
        return String.format("#%06x", nextInt);
    }
}
