package com.github.mcnaughtondesktop;

import com.dlsc.formsfx.model.structure.IntegerField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class McNaughtonDesktopApplication extends Application {
    private final McNaughtonController controller = new McNaughtonController();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        pane.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

        HBox hBox = new HBox();
        hBox.setFillHeight(Boolean.TRUE);
        pane.getChildren().add(hBox);

        hBox.getChildren().add(this.getLeftPanel());
        hBox.getChildren().add(this.getRightPanel());

        Scene scene = new Scene(pane, 1600, 900);
        stage.setTitle("McNaughton Algorithm");
        stage.setScene(scene);
        stage.show();

        stage.show();
    }

    public VBox getLeftPanel() {
        VBox vBox = new VBox();

        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
        vBox.getStyleClass().add("left-panel");
        vBox.setPrefHeight(900);
        vBox.setPrefWidth(400);

        Label taskQuantityLabel = new Label("Ilość zadań");
        vBox.getChildren().add(taskQuantityLabel);

        vBox.getChildren().add(this.controller.taskQuantityField);

        Label machineQuantityLabel = new Label("Ilość maszyn");
        vBox.getChildren().add(machineQuantityLabel);

        vBox.getChildren().add(this.controller.machineQuantityField);

        ScrollPane taskExecutionTimeFields = new ScrollPane();
        taskExecutionTimeFields.getStyleClass().add("left-panel");
        taskExecutionTimeFields.setFitToWidth(Boolean.TRUE);
        taskExecutionTimeFields.setContent(this.getTaskExecutionTimeFields());
        vBox.getChildren().add(taskExecutionTimeFields);

        Button button = this.controller.executeButton;
        button.setText("WYKONAJ");
        button.setPadding(new Insets(10));
        button.setPrefWidth(360);
        vBox.getChildren().add(button);

        return vBox;
    }

    public VBox getRightPanel() {
        VBox vBox = new VBox();
        vBox.getStyleClass().add("right-panel");
        vBox.setPrefHeight(900);
        vBox.setPrefWidth(1200);
        vBox.setPadding(new Insets(20));

        HBox infoBox = new HBox();
        infoBox.getStyleClass().add("info-panel");
        infoBox.setPadding(new Insets(50));
        infoBox.setPrefHeight(50);

        Text text = new Text();
        text.setText("Cmax = max{max{1, 2, 4, 4, 2, 2}, 15 / 3}} = max{4, 5} = 5");
        infoBox.getChildren().add(text);
        vBox.getChildren().add(infoBox);

        String[] machines = new String[]{"Machine 1", "Machine 2", "Machine 3"};
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number, String> chart = new GanttChart<Number, String>(xAxis, yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(machines)));

        chart.setTitle("Machine Monitoring");
        chart.setLegendVisible(false);
        chart.setBlockHeight(50);
        String machine;

        machine = machines[0];
        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data(0, machine, new GanttChart.ExtraData(1, "status-red")));
        series1.getData().add(new XYChart.Data(1, machine, new GanttChart.ExtraData(1, "status-green")));
        series1.getData().add(new XYChart.Data(2, machine, new GanttChart.ExtraData(1, "status-red")));
        series1.getData().add(new XYChart.Data(3, machine, new GanttChart.ExtraData(1, "status-green")));

        machine = machines[1];
        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data(0, machine, new GanttChart.ExtraData(1, "status-green")));
        series2.getData().add(new XYChart.Data(1, machine, new GanttChart.ExtraData(1, "status-green")));
        series2.getData().add(new XYChart.Data(2, machine, new GanttChart.ExtraData(2, "status-red")));

        machine = machines[2];
        XYChart.Series series3 = new XYChart.Series();
        series3.getData().add(new XYChart.Data(0, machine, new GanttChart.ExtraData(1, "status-blue")));
        series3.getData().add(new XYChart.Data(1, machine, new GanttChart.ExtraData(2, "status-red")));
        series3.getData().add(new XYChart.Data(3, machine, new GanttChart.ExtraData(1, "status-green")));

        chart.getData().addAll(series1, series2, series3);

        chart.getStylesheets().add(getClass().getResource("css/ganttchart.css").toExternalForm());


        ScrollPane chartScrollPanel = new ScrollPane();
        chartScrollPanel.setPadding(new Insets(20, 0, 0, 0));
        chartScrollPanel.setFitToWidth(Boolean.TRUE);
        chartScrollPanel.setFitToHeight(Boolean.TRUE);
        chartScrollPanel.setContent(chart);
        vBox.getChildren().add(chartScrollPanel);

        return vBox;
    }

    public VBox getTaskExecutionTimeFields() {
        VBox box = this.controller.scrollableTaskExecutionTimeInputs;

        box.setSpacing(10);
        box.setFillWidth(Boolean.TRUE);
        box.setPrefWidth(345);
        box.getStyleClass().add("left-panel");

        return box;
    }
}
