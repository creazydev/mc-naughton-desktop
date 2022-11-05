package com.github.mcnaughtondesktop;

import com.github.mcnaughtondesktop.model.GanttChart;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        vBox.setPrefHeight(900);
        vBox.setPrefWidth(1200);
        vBox.setPadding(new Insets(20));

        HBox infoBox = new HBox();
        infoBox.getStyleClass().add("info-panel");
        infoBox.setPadding(new Insets(50));
        infoBox.setPrefHeight(50);

        Text infoText = this.controller.infoText;
        infoText.setWrappingWidth(1000);
        infoBox.getChildren().add(infoText);
        vBox.getChildren().add(infoBox);

        GanttChart<Number, String> ganttChart = this.controller.chart;
        ganttChart.setLegendVisible(false);
        ganttChart.setMinHeight(600);
        ganttChart.setMinWidth(1100);
        ganttChart.getStylesheets().add(getClass().getResource("css/ganttchart.css").toExternalForm());

        final NumberAxis xAxis = (NumberAxis) ganttChart.getXAxis();
        final CategoryAxis yAxis = (CategoryAxis) ganttChart.getYAxis();
        yAxis.setCategories(this.controller.labels);

        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);


        ScrollPane chartScrollPanel = new ScrollPane();
        chartScrollPanel.setPadding(new Insets(20, 0, 0, 0));
        chartScrollPanel.setMinWidth(600);
//        chartScrollPanel.setFitToWidth(Boolean.TRUE);
        chartScrollPanel.setPrefHeight(700);
//        chartScrollPanel.setFitToHeight(Boolean.TRUE);
        chartScrollPanel.setContent(ganttChart);
        vBox.getChildren().add(chartScrollPanel);

        return vBox;
    }

    public VBox getTaskExecutionTimeFields() {
        VBox box = this.controller.taskInputsWrapper;

        box.setSpacing(10);
        box.setFillWidth(Boolean.TRUE);
        box.setPrefWidth(345);
        box.getStyleClass().add("left-panel");

        return box;
    }
}
