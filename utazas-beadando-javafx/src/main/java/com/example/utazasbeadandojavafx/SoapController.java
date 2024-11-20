package com.example.utazasbeadandojavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoapController {
    @FXML private Button loadFileButton;

    @FXML private Label statusLabel;

    @FXML private Label graphStatusLabel;

    @FXML private LineChart<String, Number> lineChart;

    private ObservableList<XYChart.Data<String, Number>> exchangeRateData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadFileButton.setOnAction(event -> loadExchangeRatesFile());
    }

    private void loadExchangeRatesFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Válaszd ki a fájlt!");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(loadFileButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                String content = Files.readString(selectedFile.toPath());
                System.out.println("Fájl tartalma: " + content);

                parseAndPlotData(content);

                statusLabel.setText("Állapot: Sikeresen betöltötte a fájlt!");
                graphStatusLabel.setText("Grafikon állapot: Sikeresen frissítve.");
            } catch (IOException e) {
                statusLabel.setText("Állapot: Hiba a fájl betöltése közben - " + e.getMessage());
                graphStatusLabel.setText("Grafikon állapot: Hiba történt.");
            }
        } else {
            statusLabel.setText("Állapot: Fájl kiválasztása megszűntetve.");
            graphStatusLabel.setText("Grafikon állapot: Nincs frissítés.");
        }
    }

    private void parseAndPlotData(String content) {
        lineChart.getData().clear();

        Pattern pattern = Pattern.compile("<Day date=\"(\\d{4}-\\d{2}-\\d{2})\"><Rate unit=\"1\" curr=\"([A-Z]+)\">([\\d,.]+)</Rate></Day>");
        Matcher matcher = pattern.matcher(content);

        List<XYChart.Data<String, Number>> dataList = new ArrayList<>();
        String currency = null;

        while (matcher.find()) {
            String date = matcher.group(1);
            currency = matcher.group(2);
            String rateString = matcher.group(3).replace(",", ".");
            double rate = Double.parseDouble(rateString);

            dataList.add(new XYChart.Data<>(date, rate));
        }

        Collections.reverse(dataList);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Árfolyam");
        series.getData().addAll(dataList);

        lineChart.getData().add(series);

        if (currency != null) {
            lineChart.getYAxis().setLabel(currency + "/HUF");
        } else {
            lineChart.getYAxis().setLabel("Árfolyam");
        }
    }
}