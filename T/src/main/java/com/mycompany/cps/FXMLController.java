package com.mycompany.cps;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.ComboBox;


public class FXMLController implements Initializable {

 

   
   
    @FXML
    private ComboBox Start;

    @FXML
    private ComboBox Koniec;

    @FXML
    private ComboBox A;

    @FXML
    private ComboBox Podzial;

    @FXML
    private ComboBox Rodzaj;

 
    @FXML
    private ComboBox s1Rodzaj;

    @FXML
    private ComboBox s1Okres;

    @FXML
    private ComboBox s1Amplituda;

    @FXML
    private ComboBox s1Wypełnienie;

    @FXML
    private ComboBox s2Rodzaj;

    @FXML
    private ComboBox s2Okres;

    @FXML
    private ComboBox s2Amplituda;

    @FXML
    private ComboBox s2Wypełnienie;


    @FXML
    private ScatterChart<Number, Number> wykresDzialania;

    @FXML
    private ScatterChart<Number, Number> wykresDyskretny;



   
   

    public void cleanDzialanie() {
        s1Amplituda.setValue(0.0);
        s2Amplituda.setValue(0.0);
        s1Okres.setValue(0.0);
        s2Okres.setValue(0.0);
        s1Rodzaj.setValue("");
        s2Rodzaj.setValue("");
        s1Wypełnienie.setValue(0.0);
        s2Wypełnienie.setValue(0.0);
        wykresDzialania.getData().clear();

    }

  
    private final ObservableList<Double> startValues = FXCollections.observableArrayList(0.0, 100.0, 1000.0, 2000.0);
    private final ObservableList<Double> koniecValues = FXCollections.observableArrayList(1000.0, 5000.0, 10000.0, 20000.0);
    private final ObservableList<Double> aValues = FXCollections.observableArrayList(1013.25);
    private final ObservableList<String> iloscProbek = FXCollections.observableArrayList("2", "3", "5", "10", "20", "50", "100", "200", "500", "1000", "sygnał ciągły");
    private final ObservableList<String> rodzajSygnalu = FXCollections.observableArrayList("wzór barometryczny");
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Start.setItems(startValues);
        Koniec.setItems(koniecValues);
        A.setItems(aValues);
        Podzial.setItems(iloscProbek);
        Rodzaj.setItems(rodzajSygnalu);
        wykresDyskretny.setTitle("Prognoza ciśnienia");
        wykresDyskretny.setLegendVisible(true);
       wykresDyskretny.getXAxis().setAutoRanging(true);
    }
}
