package com.example.utazasbeadandojavafx;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;



public class ParhuzamosController {

    @FXML private Button startButton;
    @FXML private Label thread_one_txt;
    @FXML private Label thread_two_txt;

    @FXML
    private void onStartButtonClick() {
        thread_one_txt.setText("Első szál");
        thread_two_txt.setText("Második szál");
    }


}
