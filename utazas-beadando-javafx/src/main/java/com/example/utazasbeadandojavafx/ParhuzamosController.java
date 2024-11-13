package com.example.utazasbeadandojavafx;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;



public class ParhuzamosController {

    @FXML private Button startButton;
    @FXML private Label thread_one_txt;
    @FXML private Label thread_two_txt;

    class Szal1 extends Thread {
        @Override
        public void run() {
            int counter1 = 0;
            while (true) {
                String message1 = "Egyik szál: " + counter1;
                Platform.runLater(() -> thread_one_txt.setText(message1));
                counter1++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Szal2 extends Thread {
        @Override
        public void run() {
            int counter2 = 0;
            while (true) {
                String message2 = "Másik szál: " + counter2;
                Platform.runLater(() -> thread_two_txt.setText(message2));
                counter2++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    private void onStartButtonClick() {
        Szal1 szal1 = new Szal1();
        Szal2 szal2 = new Szal2();

        szal1.start();
        szal2.start();
    }
}
