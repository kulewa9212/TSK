package com.mycompany.cps;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

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
    private TextField Dur;
    
    @FXML
    private Circle craft;

    @FXML
    private Circle craft2;

    @FXML
    private Circle planet;

    @FXML
    private TextField areaField;

    @FXML
    private TextField CdField;

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();

    @FXML
    private LineChart<Number, Number> lineChartF;

    @FXML
    private LineChart<Number, Number> lineChartFx;
    
    @FXML
    private LineChart<Number, Number> lineChartFy;

    @FXML
    private LineChart<Number, Number> lineChartV;

    double angle;
    double acceleration = 0.0;
    double vellocity = 0.0;
    double vI = 7190;

    @FXML
    public void checkStart() throws InterruptedException {
        doCheckStart(Double.parseDouble(massField.getText()),
                Double.parseDouble(strengthtField.getText()),
                Double.parseDouble(angleField.getText()),
                Double.parseDouble(areaField.getText()),
                Double.parseDouble(CdField.getText()));
    }

    public void doCheckStart(double mass, double Fn, double an, double area, double Cd) throws InterruptedException {
        angle = an;
        mass = mass * 1000;
        Fn = Fn * 1000000;
        double Fg = 10 * mass;
        double Fw = countFw(Fg, Fn, angle);
        double Fop = 0;
        boolean Suc = false;
        //  double Fw = Math.sqrt(Fg * Fg + Fn * Fn
        //         - 2 * Fg * Fn * Math.cos(Math.toRadians(angle)));
        double beta = Math.toDegrees(Math.asin(
                Math.sin(Math.toRadians(90 - angle)) * (Fg / Fw)));
        if (beta < angle) {
            Suc = true;
            result.setText("SUCCESS!");
        } else {
            result.setText("FAILURE!");
        }
        if (Suc == true) {
            acceleration = Fw / mass;
            System.out.println("a = " + acceleration);
            double Fwx = Fw * Math.cos(Math.toRadians(90 - beta));
            double Fwy = Fw * Math.sin(Math.toRadians(90 - beta));
            double sx = Math.abs(0.5 * Fwx / mass);
            double sy = Math.abs(0.5 * Fwy / mass);
            XYChart.Series Fseries = new XYChart.Series();
            XYChart.Series Fxseries = new XYChart.Series();
             XYChart.Series Fyseries = new XYChart.Series();
            XYChart.Series Vseries = new XYChart.Series();
            //  craft.setTranslateX(sx);
            // craft.setTranslateY(-sy);
            System.out.println("sx = " + sx + ", sy = " + sy);
            int count = 1;
            System.out.println("Counter = " + count);
            while (vellocity < vI) {
                vellocity += acceleration * 1; //co 1 minuta 
                Fop = 1.225 * vellocity * vellocity * area * Cd;
                System.out.println("Velovity = " + vellocity);
                Fw = countFw(Fw - Fop, Fg, angle);
                Fwx = Fw * Math.cos(Math.toRadians(90 - beta));
                Fwy = Fw * Math.sin(Math.toRadians(90 - beta));
                Fseries.getData().add(new XYChart.Data(Integer.toString(count), Fw));
                Fxseries.getData().add(new XYChart.Data(Integer.toString(count), Fwx));
                Fyseries.getData().add(new XYChart.Data(Integer.toString(count), Fwy));
                Vseries.getData().add(new XYChart.Data(Integer.toString(count), vellocity));
                acceleration = Fw / mass;
                count++;
                System.out.println("Counter = " + count);
            }
            Dur.setText(count-1+" s");
            lineChartF.getData().addAll(Fseries);
            lineChartFx.getData().addAll(Fxseries);
            lineChartFy.getData().addAll(Fyseries);
            lineChartV.getData().addAll(Vseries);
            TranslateTransition Tt = new TranslateTransition(Duration.seconds(count));
            Tt.setToX(100);
            Tt.setToY(-60);
            Tt.setNode(craft);

            Arc arc = new Arc(0, 0, Math.sqrt(100 * 100 + 153 * 153), Math.sqrt(100 * 100 + 153 * 153), 57, -360);
            Circle cr = new Circle(Math.sqrt(100 * 100 + 153 * 153));
            cr.setLayoutY(93);

            PathTransition Pt = new PathTransition(Duration.seconds(10), arc, craft2);
            //Pt.setDelay(Duration.seconds(10));
            //PathTransition Pt = new PathTransition(Duration.seconds(10), cr, craft);
            SequentialTransition St = new SequentialTransition(Tt, Pt);
            //St.
            St.play();
            // St.

        } else {
        }
    }

    double countFw(double F1, double F2, double an) {
        return Math.sqrt(F1 * F1 + F2 * F2
                - 2 * F1 * F2 * Math.cos(Math.toRadians(an)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        wykresDyskretny.setTitle("Prognoza ciśnienia");
        //  wykresDyskretny.setLegendVisible(true);
        //   wykresDyskretny.getXAxis().setAutoRanging(true);
    }
}
