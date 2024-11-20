package com.example.utazasbeadandojavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SoapController {
    @FXML private Button loadFileButton;

    @FXML
    private Label statusLabel;

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
                String content = Files.readString(((java.io.File) selectedFile).toPath());
                System.out.println("Fájl tartalma: " + content);
                statusLabel.setText("Állapot: Sikeresen betöltötte a fájlt!");
            } catch (IOException e) {
                statusLabel.setText("Állapot: Hiba a fájl betöltése közben - " + e.getMessage());
            }
        } else {
            statusLabel.setText("Állapot: Fájl kiválasztása megszűntetve.");
        }
    }

}
