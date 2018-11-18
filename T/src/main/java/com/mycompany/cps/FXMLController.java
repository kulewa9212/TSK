package com.mycompany.cps;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {
 
    @FXML
    private ScatterChart<Number, Number> wykresDyskretny;

    @FXML
    private TextField massField;

    @FXML
    private TextField strengthtField;

    @FXML
    private TextField angleField;

    @FXML
    private TextField result;

    @FXML
    public void checkStart() {
        doCheckStart(Double.parseDouble(massField.getText()),
                Double.parseDouble(strengthtField.getText()),
                Double.parseDouble(angleField.getText()));
    }

    public void doCheckStart(double Fg, double Fn, double angle) {
        double Fw = Math.sqrt(Fg * Fg + Fn * Fn
                - 2 * Fg * Fn * Math.cos(Math.toRadians(angle)));
        double beta = Math.toDegrees(Math.asin(
                Math.sin(Math.toRadians(90 - angle)) * (Fg / Fw)));
        if (beta < angle) {
            result.setText("SUCCESS!");
        } else {
            result.setText("FAILURE!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        wykresDyskretny.setTitle("Prognoza ciśnienia");
        wykresDyskretny.setLegendVisible(true);
        wykresDyskretny.getXAxis().setAutoRanging(true);
    }
}
