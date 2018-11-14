package com.mycompany.cps;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    private final List<Sygnał> magazyn = new ArrayList();

    @FXML
    private Label label;

    @FXML
    private Tab sygnalDyskretny;

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
    private TextField srednia;

    @FXML
    private TextField wSkuteczna;

    @FXML
    private TextField wariancja;

    @FXML
    private ComboBox Okres;

    @FXML
    private ComboBox wypelnienie;

    @FXML
    private TextField sredniaMoc;

    @FXML
    private Button generujWykres;

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
    private Button wyczyscDzialanie;

    @FXML
    private ComboBox dzialanie;

    @FXML
    private ComboBox dzialanie1;

    @FXML
    private ScatterChart<Number, Number> wykresDzialania;

    @FXML
    private ScatterChart<Number, Number> wykresDyskretny;

    @FXML
    private ScatterChart<Number, Number> wykresPrezentacji;

    @FXML
    private TextField nazwa;

    @FXML
    private ListView<String> ListaDostepnych;

    @FXML
    private TextField prezentacjaWsK;

    @FXML
    private TextField prezentacjaSrednia;

    @FXML
    private TextField prezentacjaMoc;

    @FXML
    private TextField prezentacjaWariancja;

    @FXML
    private TextField newName;

    @FXML
    private TextField probkowanieNazwa;

    @FXML
    private TextField poziomProbkowania;

    @FXML
    private TextField wzorzec;

    @FXML
    private TextField MSE;

    @FXML
    private TextField MAX;

    @FXML
    private TextField SNR;

    @FXML
    private TextField PSNR;

    @FXML
    private TextArea logs;

    @FXML
    private ListView<String> listaWybranych;

    @FXML
    private BarChart<String, Number> historgram;

    //@FXML
    // private Button dodajDoDzialania;
    public void f1(double a, double b, double c, String size, String rodzaj, String nazwa) throws InvalidName {

        Sygnał helpSygnal = new Sygnał();
        int howMany = 1000;
        // int helpSize=Integer.parseInt(size)-1;
        if (!size.equals("sygnał ciągły")) {
            howMany = Integer.parseInt(size) + 1;
        };
        double jump = Math.abs(b - a) / howMany;
        XYChart.Series series1 = new XYChart.Series();
        double[][] tablica;
        tablica = new double[2][howMany];
        double[][] tablicaMagazyn = new double[2][1000];
        tablica[0][0] = a;
        for (int i = 1; i < tablica[0].length; i++) {
            tablica[0][i] = round(tablica[0][i - 1] + jump, 2);
            switch (rodzaj) {
                case "wzór barometryczny":
                    //   tablica[1][i] = c * (Math.sin((2 * Math.PI * (tablica[0][i] - a)) / T));
                    tablica[1][i] = c * (Math.exp((-1) * ((0.0289644 * 9.81 * tablica[0][i] - a) / (8.3144 * 288.15))));
                    System.out.println((-1) * ((0.0289644 * 9.81 * tablica[0][i] - a) / (8.3144 * 288.15)));
                    break;
               
            }
            series1.getData().add(new XYChart.Data(Double.toString(tablica[0][i]), tablica[1][i]));
            series1.setName("Sygnał");
        }

        tablicaMagazyn[0][0] = 0;

        helpSygnal.setProbki(tablica);
        helpSygnal.setFunkcja(rodzaj);
        //     helpSygnal.setOkres(T);
        helpSygnal.setMoc_srednia(round(wyznaczSredniaMoc(tablicaMagazyn[1]), 2));
        helpSygnal.setWariancja(round(wyznaczWariancja(tablicaMagazyn[1]), 2));
        helpSygnal.setWartosc_skuteczna(round(Math.sqrt(wyznaczWartoscSkuteczna(tablicaMagazyn[1])), 2));
        helpSygnal.setWartosc_srednia(round(wyznaczSrednia(tablicaMagazyn[1]), 2));
        helpSygnal.setLabel(nazwa);
        List<Sygnał> listaSyg2 = new ArrayList<Sygnał>();
        //   System.out.println("ROZMIAR LISTY 0 =  "+listaSyg2.size());
        listaSyg2.add(zwrocSygnal(helpSygnal.getLabel()));
        //  System.out.println("ROZMIAR LISTY 1 =  "+listaSyg2.size());
        if (magazyn.size() > 0 && listaSyg2.size() > 0) {
            for (int i = 0; i < listaSyg2.size(); i++) {
                try {
                    if (listaSyg2.get(i).getLabel().equals(helpSygnal.getLabel())) {
                        listaSyg2.remove(listaSyg2.get(i));
                    }
                    throw new InvalidName();
                } catch (NullPointerException e) {
                }
            }

        }

        magazyn.add(helpSygnal);
        ListaDostepnych.getItems().add(nazwa);

        wykresDyskretny.getData().add(series1);
        //  wykresDyskretny.getXAxis().setVisible(false);

        wykresDyskretny.getXAxis().setVisible(true);
    }

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

    public double wyznaczSrednia(double[] table) {
        double suma = 0;
        for (int i = 0; i < table.length; i++) {
            suma += table[i];
        }
        return suma / table.length;
    }

    public double wyznaczSredniaMoc(double[] table) {
        double suma = 0;
        for (int i = 0; i < table.length; i++) {
            suma += table[i] * table[i];
        }
        return suma / table.length;
    }

    public double wyznaczWariancja(double[] table) {
        double srednia = wyznaczSrednia(table);
        double suma = 0;
        for (int i = 0; i < table.length; i++) {
            suma += (table[i] - srednia) * (table[i] - srednia);
        }
        return suma / table.length;
    }

    public double wyznaczWartoscSkuteczna(double[] table) {
        double srednia = wyznaczSrednia(table);
        double suma = 0;
        for (int i = 0; i < table.length; i++) {
            suma += (table[i] - srednia) * (table[i] - srednia);
        }
        return Math.sqrt(suma / table.length);
    }

    private ObservableList<Double> startValues = FXCollections.observableArrayList(0.0, 100.0, 1000.0, 2000.0);
    private ObservableList<Double> koniecValues = FXCollections.observableArrayList(1000.0, 5000.0, 10000.0, 20000.0);
    private ObservableList<Double> aValues = FXCollections.observableArrayList(1013.25);
    private ObservableList<String> iloscProbek = FXCollections.observableArrayList("2", "3", "5", "10", "20", "50", "100", "200", "500", "1000", "sygnał ciągły");
    private ObservableList<Double> okres = FXCollections.observableArrayList(0.5, 1.0, 1.5, 2.0, 2 * 3.14);
    private ObservableList<String> rodzajSygnalu = FXCollections.observableArrayList("wzór barometryczny");
    private ObservableList<Double> wWypelnienia = FXCollections.observableArrayList(0.1, 0.3, 0.5, 1.0);


    public void plusClick() {
        f2((String) dzialanie.getValue(), (double) s1Amplituda.getValue(), (double) s1Okres.getValue(), (String) s1Rodzaj.getValue(), (double) s1Wypełnienie.getValue(), (double) s2Amplituda.getValue(), (double) s2Okres.getValue(), (String) s2Rodzaj.getValue(), (double) s2Wypełnienie.getValue());
    }

    public void f2(String znak, double amplituda1, double T1, String rodzaj1, double wypelnienie1, double amplituda2, double T2, String rodzaj2, double wypelnienie2) {
        int howMany = 1000;
        double jump = 0.01;
        System.out.println(jump);
        XYChart.Series series1 = new XYChart.Series();
        double[][] tablica;
        tablica = new double[3][howMany];
        tablica[0][0] = 0;
        for (int i = 1; i < tablica[0].length; i++) {
            tablica[0][i] = round(tablica[0][i - 1] + jump, 2);
            switch (rodzaj1) {
                case "sinus 1p":
                    tablica[1][i] = round(0.5 * amplituda1 * (Math.sin((2 * Math.PI * (tablica[0][i])) / T1) + Math.abs(Math.sin((2 * Math.PI * (tablica[0][i])) / T1))), 2);
                    break;
                case "sinus":
                    tablica[1][i] = amplituda1 * (Math.sin((2 * Math.PI * (tablica[0][i])) / T1));
                    break;
                case "sinus 2p":
                    tablica[1][i] = amplituda1 * Math.abs(Math.sin((2 * Math.PI * (tablica[0][i])) / T1));
                    break;
                case "szum gauss.":
                    tablica[1][i] = (1 / Math.sqrt(2 * Math.PI)) * Math.exp(-(1) * ((tablica[0][i] * tablica[0][i]) / 2));
                    break;
                case "prostokątny":
                    for (int k = -50; k < 50; k++) {
                        if (tablica[0][i] > T1 * k && tablica[0][i] < wypelnienie1 * T1 + T1 * k) {
                            tablica[1][i] = amplituda1;
                            break;
                        }
                        if (tablica[0][i] > wypelnienie1 * T1 + T1 * k && tablica[0][i] < T1 + T1 * k) {
                            tablica[1][i] = 0;
                        }
                    }
            }
            switch (rodzaj2) {
                case "sinus 1p":
                    tablica[2][i] = round(0.5 * amplituda2 * (Math.sin((2 * Math.PI * (tablica[0][i])) / T2) + Math.abs(Math.sin((2 * Math.PI * (tablica[0][i])) / T2))), 2);
                    break;
                case "sinus":
                    tablica[2][i] = amplituda2 * (Math.sin((2 * Math.PI * (tablica[0][i])) / T2));
                    break;
                case "sinus 2p":
                    tablica[2][i] = amplituda2 * Math.abs(Math.sin((2 * Math.PI * (tablica[0][i])) / T2));
                    break;
                case "szum gauss.":
                    tablica[2][i] = (1 / Math.sqrt(2 * Math.PI)) * Math.exp(-(1) * ((tablica[0][i] * tablica[0][i]) / 2));
                    break;
                case "prostokątny":
                    for (int k = -50; k < 50; k++) {
                        if (tablica[0][i] > T2 * k && tablica[0][i] < wypelnienie2 * T2 + T2 * k) {
                            tablica[2][i] = amplituda2;
                            break;
                        }
                        if (tablica[0][i] > wypelnienie2 * T2 + T2 * k && tablica[0][i] < T2 + T2 * k) {
                            tablica[2][i] = 0;
                        }
                    }
            }
            switch (znak) {
                case "+":
                    series1.getData().add(new XYChart.Data(Double.toString(tablica[0][i]), tablica[1][i] + tablica[2][i]));
                    break;
                case "-":
                    series1.getData().add(new XYChart.Data(Double.toString(tablica[0][i]), tablica[1][i] - tablica[2][i]));
                    break;
                case "*":
                    series1.getData().add(new XYChart.Data(Double.toString(tablica[0][i]), tablica[1][i] * tablica[2][i]));
                    break;

            }

        }
        wykresDzialania.getData().add(series1);

    }

    public void click() throws InvalidName {
        f1((double) Start.getValue(), (double) Koniec.getValue(), (double) A.getValue(), (String) Podzial.getValue(), (String) Rodzaj.getValue(), nazwa.getText());
       // generujWykres.setDisable(true);
        wykresDyskretny.getXAxis().setVisible(false);

    }

    public void podgladSygnalu() {
        //   System.out.println(ListaDostepnych.getSelectionModel().getSelectedItem());
        znajdzSygnal(listaWybranych.getSelectionModel().getSelectedItem());

        Sygnał podglad = zwrocSygnal(listaWybranych.getSelectionModel().getSelectedItem());
        int ilePoziomow = 5;
        double max = maxTablicy(podglad.getProbki()[1]);
        double min = minTablicy(podglad.getProbki()[1]);
        double[][] doHist = new double[ilePoziomow][ilePoziomow + 1];
        for (int j = 0; j < ilePoziomow + 1; j++) {
            doHist[0][j] = min + ((max - min) / ilePoziomow) * j;
            doHist[1][j] = 0;
        }

        for (int i = 0; i < podglad.getProbki()[0].length; i++) {
            for (int k = 1; k < ilePoziomow + 1; k++) {

                if (podglad.getProbki()[1][i] >= doHist[0][k - 1] && podglad.getProbki()[1][i] <= doHist[0][k]) {
                    doHist[1][k]++;
                }
            }

        }

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("liczebność próbek");
        for (int j = 1; j < ilePoziomow + 1; j++) {
            series1.getData().add(new XYChart.Data(Double.toString(round(doHist[0][j - 1], 2)) + " - " + Double.toString(round(doHist[0][j], 2)), doHist[1][j]));
            // System.out.println("Wartosc ="+wartosciHist[j]+",   Liczebnosc =  "+liczebnosciHist[j]);
        }

        historgram.getData().addAll(series1);

    }

    public void probkowanie() {
        double poziomProbek = Double.parseDouble(poziomProbkowania.getText());
        String lab = wykresPrezentacji.getData().get(0).getName();  //pobieramy nazwy sygnalu z wykresu
        Sygnał S = zwrocSygnal(lab);
        double minimumProbki = minTablicy(S.getProbki()[0]);
        double maximumProbki = maxTablicy(S.getProbki()[0]);
        int ileProbek1 = (int) ((int) (maximumProbki - minimumProbki) / poziomProbek);
        double[][] sprobkowane = new double[ileProbek1][ileProbek1];
        // System.out.println("Ile próbek  === " + ileProbek1);
        double x = 0;
        double y = 0;
        for (int i = 0; i < ileProbek1; i++) {
            double hp = 1 / (double) ileProbek1;
            double index = i * hp * S.getProbki()[0].length;
            // System.out.println("Y HELP  !! === " + i * hp * S.getProbki()[0].length);
            x = S.getProbki()[0][(int) index];
            // System.out.println("index - ");
            y = S.getProbki()[1][(int) index];
            sprobkowane[0][i] = x;
            sprobkowane[1][i] = y;
        }
        sprobkowane[1][ileProbek1 - 1] = S.getProbki()[1][S.getProbki()[0].length - 1];
        Sygnał S1 = new Sygnał();
        S1.setf(poziomProbek);
        S1.setProbki(sprobkowane);
        S1.setLabel(probkowanieNazwa.getText());
        S1.setMoc_srednia(round(wyznaczSrednia(sprobkowane[1]), 2));
        S1.setWariancja(round(wyznaczWariancja(sprobkowane[1]), 2));
        S1.setWartosc_skuteczna(round(wyznaczWartoscSkuteczna(sprobkowane[1]), 2));
        S1.setWartosc_srednia(round(wyznaczSrednia(sprobkowane[1]), 2));
        magazyn.add(S1);
        ListaDostepnych.getItems().add(S1.getLabel());
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(probkowanieNazwa.getText());
        for (int j = 0; j < ileProbek1; j++) {
            series2.getData().add(new XYChart.Data(Double.toString(round(sprobkowane[0][j], 2)), sprobkowane[1][j]));
            // System.out.println("Wartosc ="+wartosciHist[j]+",   Liczebnosc =  "+liczebnosciHist[j]);
        }

        wykresPrezentacji.getData().addAll(series2);

    }

    public void kwantyzacja1() {
        String lab = wykresPrezentacji.getData().get(0).getName();  //pobieramy nazwy sygnalu z wykresu
        Sygnał S = zwrocSygnal(lab);
        Sygnał S1 = new Sygnał();
        double[][] kwantowane = new double[S.getProbki()[0].length * 10][S.getProbki()[0].length * 10];
        for (int i = 0; i < S.getProbki()[0].length; i++) {
            for (int j = 10 * i; j < 10 * (i + 1); j++) {
                kwantowane[0][j] = S.getProbki()[0][i] + 0.1 * j;
                kwantowane[1][j] = S.getProbki()[1][i];
            }
        }
        //   S1.setProbki(kwantowane);
        S1.setLabel(probkowanieNazwa.getText());
        XYChart.Series series3 = new XYChart.Series();
        series3.setName(probkowanieNazwa.getText());
        for (int j = 0; j < kwantowane[0].length; j++) {
            series3.getData().add(new XYChart.Data(Double.toString(round(kwantowane[0][j], 2)), kwantowane[1][j]));
            // System.out.println("Wartosc ="+wartosciHist[j]+",   Liczebnosc =  "+liczebnosciHist[j]);
        }
        // Sygnał S1 = new Sygnał();
        S1.setProbki(kwantowane);
        // S1.setLabel(probkowanieNazwa.getText());
        S1.setMoc_srednia(round(wyznaczSrednia(kwantowane[1]), 2));
        S1.setWariancja(round(wyznaczWariancja(kwantowane[1]), 2));
        S1.setWartosc_skuteczna(round(wyznaczWartoscSkuteczna(kwantowane[1]), 2));
        S1.setWartosc_srednia(round(wyznaczSrednia(kwantowane[1]), 2));
        magazyn.add(S1);
        ListaDostepnych.getItems().add(S1.getLabel());
        wykresPrezentacji.getData().remove(0, wykresPrezentacji.getData().size());
        wykresPrezentacji.getData().addAll(series3);

    }

    public void kwantyzacja2() {
        String lab = wykresPrezentacji.getData().get(0).getName();  //pobieramy nazwy sygnalu z wykresu
        Sygnał S = zwrocSygnal(lab);
        Sygnał S1 = new Sygnał();
        double[][] kwantowane = new double[S.getProbki()[0].length * 10][S.getProbki()[0].length * 10];
        for (int i = 0; i < S.getProbki()[0].length - 1; i++) {
            for (int j = 10 * i; j < 10 * (i + 1); j++) {

                kwantowane[0][j] = S.getProbki()[0][i] + 0.1 * j;
                if (j < 10 * i + 5) {
                    kwantowane[1][j] = S.getProbki()[1][i];
                } else {
                    kwantowane[1][j] = S.getProbki()[1][i + 1];
                }
            }
        }
        S1.setProbki(kwantowane);
        S1.setLabel(probkowanieNazwa.getText());
        S1.setMoc_srednia(round(wyznaczSrednia(kwantowane[1]), 2));
        S1.setWariancja(round(wyznaczWariancja(kwantowane[1]), 2));
        S1.setWartosc_skuteczna(round(wyznaczWartoscSkuteczna(kwantowane[1]), 2));
        S1.setWartosc_srednia(round(wyznaczSrednia(kwantowane[1]), 2));
        magazyn.add(S1);
        XYChart.Series series3 = new XYChart.Series();
        series3.setName(probkowanieNazwa.getText());
        for (int j = 0; j < kwantowane[0].length; j++) {
            series3.getData().add(new XYChart.Data(Double.toString(round(kwantowane[0][j], 2)), kwantowane[1][j]));
            // System.out.println("Wartosc ="+wartosciHist[j]+",   Liczebnosc =  "+liczebnosciHist[j]);
        }
        wykresPrezentacji.getData().remove(0, wykresPrezentacji.getData().size());
        wykresPrezentacji.getData().addAll(series3);

    }

    public void OR_1() {

        Sygnał S1 = zwrocSygnal(wzorzec.getText());   //sygnal wzorcowy
        Sygnał S2 = new Sygnał();  //sygnal wynikowy - odzyskany
        double[][] S_probki = new double[S1.getProbki()[0].length][S1.getProbki()[0].length];
        Sygnał S3 = zwrocSygnal(wykresPrezentacji.getData().get(0).getName());   //sygnał próbkowany 
        for (int i = 0; i < S1.getProbki()[0].length; i++) {
            S_probki[0][i] = S1.getProbki()[0][i];
            for (int j = 0; j < S3.getProbki()[0].length - 1; j++) {
                //  System.out.println("I =="+i);
                //  System.out.println("J =="+j);
                if (S_probki[0][i] >= S3.getProbki()[0][j] && S_probki[0][i] < S3.getProbki()[0][j + 1]) {
                    S_probki[1][i] = ((S3.getProbki()[1][j + 1] - S3.getProbki()[1][j]) / (S3.getProbki()[0][j + 1] - S3.getProbki()[0][j])) * S_probki[0][i] + (S3.getProbki()[1][j + 1] - S3.getProbki()[0][j + 1] * ((S3.getProbki()[1][j + 1] - S3.getProbki()[1][j]) / (S3.getProbki()[0][j + 1] - S3.getProbki()[0][j])));
                }
            }
        }
        XYChart.Series series3 = new XYChart.Series();
        series3.setName(probkowanieNazwa.getText());
        for (int j = 0; j < S_probki[0].length - 10; j++) {
            series3.getData().add(new XYChart.Data(Double.toString(round(S_probki[0][j], 2)), S_probki[1][j]));
            // System.out.println("Wartosc ="+wartosciHist[j]+",   Liczebnosc =  "+liczebnosciHist[j]);
        }
        wykresPrezentacji.getData().remove(0, wykresPrezentacji.getData().size());
        wykresPrezentacji.getData().addAll(series3);
        S2.setLabel(probkowanieNazwa.getText());
        S2.setProbki(S_probki);
        S2.setMoc_srednia(round(wyznaczSrednia(S_probki[1]), 2));
        S2.setWariancja(round(wyznaczWariancja(S_probki[1]), 2));
        S2.setWartosc_skuteczna(round(wyznaczWartoscSkuteczna(S_probki[1]), 2));
        S2.setWartosc_srednia(round(wyznaczSrednia(S_probki[1]), 2));
        magazyn.add(S2);
        ListaDostepnych.getItems().add(S2.getLabel());
        wyznaczMSE(S1, S2);
        wyznaczMAX(S1, S2);
        wyznaczSNR(S1, S2);
        wyznaczPSNR(S1, S2);

    }

    public void OR_0() {

        Sygnał S1 = zwrocSygnal(wzorzec.getText());   //sygnal wzorcowy
        Sygnał S2 = new Sygnał();  //sygnal wynikowy - odzyskany
        double[][] S_probki = new double[S1.getProbki()[0].length][S1.getProbki()[0].length];
        Sygnał S3 = zwrocSygnal(wykresPrezentacji.getData().get(0).getName());   //sygnał próbkowany 
        for (int i = 0; i < S1.getProbki()[0].length; i++) {
            S_probki[0][i] = S1.getProbki()[0][i];
            for (int j = 0; j < S3.getProbki()[0].length - 1; j++) {
                //  System.out.println("I =="+i);
                //  System.out.println("J =="+j);
                if (S_probki[0][i] >= S3.getProbki()[0][j] && S_probki[0][i] < S3.getProbki()[0][j + 1]) {
                    S_probki[1][i] = S3.getProbki()[1][j];
                }
            }
        }
        XYChart.Series series3 = new XYChart.Series();
        series3.setName(probkowanieNazwa.getText());
        for (int j = 0; j < S_probki[0].length - 10; j++) {
            series3.getData().add(new XYChart.Data(Double.toString(round(S_probki[0][j], 2)), S_probki[1][j]));
            // System.out.println("Wartosc ="+wartosciHist[j]+",   Liczebnosc =  "+liczebnosciHist[j]);
        }
        wykresPrezentacji.getData().remove(0, wykresPrezentacji.getData().size());
        wykresPrezentacji.getData().addAll(series3);
        S2.setProbki(S_probki);
        S2.setLabel(probkowanieNazwa.getText());
        S2.setMoc_srednia(round(wyznaczSrednia(S_probki[1]), 2));
        S2.setWariancja(round(wyznaczWariancja(S_probki[1]), 2));
        S2.setWartosc_skuteczna(round(wyznaczWartoscSkuteczna(S_probki[1]), 2));
        S2.setWartosc_srednia(round(wyznaczSrednia(S_probki[1]), 2));
        magazyn.add(S2);
        ListaDostepnych.getItems().add(S2.getLabel());
        wyznaczMSE(S1, S2);
        wyznaczMAX(S1, S2);
        wyznaczSNR(S1, S2);
        wyznaczPSNR(S1, S2);
    }

    public void OR_2() {

        Sygnał S1 = zwrocSygnal(wzorzec.getText());   //sygnal wzorcowy
        Sygnał S2 = new Sygnał();  //sygnal wynikowy - odzyskany
        double[][] S_probki = new double[S1.getProbki()[0].length][S1.getProbki()[0].length];
        Sygnał S3 = zwrocSygnal(wykresPrezentacji.getData().get(0).getName());   //sygnał próbkowany 
        for (int i = 0; i < S1.getProbki()[0].length; i++) {
            S_probki[0][i] = S1.getProbki()[0][i];
            for (int n = -1000; n < 1000; n++) {
                // System.out.println("F =="+S3.getf());
                switch (S1.getFunkcja()) {
                    case "sinus":
                        S_probki[1][i] += maxTablicy(S1.getProbki()[1]) * Math.sin((2 * Math.PI * (S_probki[0][i] - minTablicy(S1.getProbki()[0]))) / S1.getOkres()) * sinc((S_probki[0][i] / S3.getf()) - n);
                        break;
                    case "sinus 2p":
                        S_probki[1][i] += maxTablicy(S1.getProbki()[1]) * Math.abs(Math.sin((2 * Math.PI * (S_probki[0][i] - minTablicy(S1.getProbki()[0]))) / S1.getOkres())) * sinc((S_probki[0][i] / S3.getf()) - n);
                        break;
                    case "sinus 1p":
                        S_probki[1][i] += round(0.5 * maxTablicy(S1.getProbki()[1]) * (Math.sin((2 * Math.PI * (S_probki[0][i] - minTablicy(S1.getProbki()[0]))) / S1.getOkres()) + Math.abs(Math.sin((2 * Math.PI * (S1.getProbki()[0][i] - minTablicy(S1.getProbki()[0]))) / 6.28))), 2) * sinc((S_probki[0][i] / S3.getf()) - n);
                        break;
                }

                //  S_probki[1][i]+=Math.sin(n*S3.getf())*sinc((S_probki[0][i]/S3.getf())-n);
            }

        }
        XYChart.Series series3 = new XYChart.Series();
        series3.setName(probkowanieNazwa.getText());
        for (int j = 0; j < S_probki[0].length - 10; j++) {
            series3.getData().add(new XYChart.Data(Double.toString(round(S_probki[0][j], 2)), S_probki[1][j]));
            // System.out.println("Wartosc ="+wartosciHist[j]+",   Liczebnosc =  "+liczebnosciHist[j]);
        }
        wykresPrezentacji.getData().remove(0, wykresPrezentacji.getData().size());
        wykresPrezentacji.getData().addAll(series3);
        S2.setProbki(S_probki);
        S2.setLabel(probkowanieNazwa.getText());
        S2.setMoc_srednia(round(wyznaczSrednia(S_probki[1]), 2));
        S2.setWariancja(round(wyznaczWariancja(S_probki[1]), 2));
        S2.setWartosc_skuteczna(round(wyznaczWartoscSkuteczna(S_probki[1]), 2));
        S2.setWartosc_srednia(round(wyznaczSrednia(S_probki[1]), 2));
        magazyn.add(S2);
        ListaDostepnych.getItems().add(S2.getLabel());
        wyznaczMSE(S1, S2);
        wyznaczMAX(S1, S2);
        wyznaczSNR(S1, S2);
        wyznaczPSNR(S1, S2);
    }

    public double sinc(double x) {
        if (x == 0) {
            return 1;
        } else {
            return (Math.sin(Math.PI * x)) / (Math.PI * x);
        }
    }

    public void wyznaczMSE(Sygnał S1, Sygnał S2) {

        double help = 0.0;
        for (int i = 0; i < S1.getProbki()[0].length - 10; i++) {
            help += (S1.getProbki()[1][i] - S2.getProbki()[1][i]) * (S1.getProbki()[1][i] - S2.getProbki()[1][i]);
        }
        MSE.setText(Double.toString(round(help / (S1.getProbki()[1].length - 10), 2)));

    }

    public void wyznaczMAX(Sygnał S1, Sygnał S2) {

        double help = 0.0;
        for (int i = 0; i < S1.getProbki()[0].length - 10; i++) {
            if (help < Math.abs(S1.getProbki()[1][i] - S2.getProbki()[1][i])) {
                help = Math.abs(S1.getProbki()[1][i] - S2.getProbki()[1][i]);
            }
        }
        MAX.setText(Double.toString(round(help, 2)));

    }

    public void wyznaczSNR(Sygnał S1, Sygnał S2) {

        double help = 0.0;
        double help1 = 0.0;
        for (int i = 0; i < S1.getProbki()[0].length - 10; i++) {
            help += (S1.getProbki()[1][i] * S1.getProbki()[1][i]);
            help1 += (S1.getProbki()[1][i] - S2.getProbki()[1][i]) * (S1.getProbki()[1][i] - S2.getProbki()[1][i]);
        }
        double SNR_res = 10 * Math.log10(help / help1);
        SNR.setText(Double.toString(round(SNR_res, 2)));

    }

    public void wyznaczPSNR(Sygnał S1, Sygnał S2) {

        double help = 0.0;
        for (int i = 0; i < S1.getProbki()[0].length - 10; i++) {
            help += (S1.getProbki()[1][i] - S2.getProbki()[1][i]) * (S1.getProbki()[1][i] - S2.getProbki()[1][i]);
        }
        double MSE_res = round(help / (S1.getProbki()[1].length), 2);
        PSNR.setText(Double.toString(round(10 * Math.log10(maxTablicy(S1.getProbki()[1]) / MSE_res), 2)));

    }

    public double maxTablicy(double[] table) {
        double wynik = table[0];
        for (double p : table) {
            if (p > wynik) {
                wynik = p;
            }
        }
        return wynik;
    }

    public double minTablicy(double[] table) {
        double wynik = table[0];
        for (double p : table) {
            if (p < wynik) {
                wynik = p;
            }
        }
        return wynik;
    }

    public int sprawdzWystapienie(double[] table, double value) {
        int wynik = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] == value) {
                wynik++;
            }

        }

        return wynik;

    }

    public void znajdzSygnal(String label) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(label);
        for (Sygnał s : magazyn) {
            if (s.getLabel().equals(label)) {
                prezentacjaMoc.setText(Double.toString(s.getMoc_srednia()));
                prezentacjaSrednia.setText(Double.toString(s.getWartosc_srednia()));
                prezentacjaWariancja.setText(Double.toString(s.getWariancja()));
                prezentacjaWsK.setText(Double.toString(s.getWartosc_skuteczna()));

                for (int i = 0; i < s.getProbki()[0].length; i++) {
                    series1.getData().add(new XYChart.Data(Double.toString(s.getProbki()[0][i]), s.getProbki()[1][i]));
                }
            }
            wykresPrezentacji.getData().add(series1);
            // series1.getData().add(new XYChart.Data(Double.toString(tablica[0][i]), tablica[1][i]));

        }
    }

    public Sygnał zwrocSygnal(String label) {
        Sygnał result = new Sygnał();
        //  System.out.println("LABEL -  " + label);
        //   XYChart.Series series1 = new XYChart.Series();
        for (Sygnał s : magazyn) {
            if (s.getLabel().equals(label)) {
                result = s;
            }
        }
        return result;
    }

    public void dzialanie() {
        //  List<String> StringHelp=new ArrayList<String>();
        //   StringHelp.add("s1");
        //  ListaDostepnych.getSelectionModel().getSelectedItems() 
        dzialaj(listaWybranych.getItems(), newName.getText(), dzialanie1.getValue().toString());
    }

    public void dzialaj(List<String> nazwy, String nN, String znak) {
        int ileSygnalow = nazwy.size();
        List<Sygnał> sygnalki = new ArrayList<Sygnał>();
        List<double[][]> wartosciProbek = new ArrayList<>();
        for (int i = 0; i < ileSygnalow; i++) {
            Sygnał help = new Sygnał();
            help = zwrocSygnal(nazwy.get(i));
            sygnalki.add(help);
//            System.out.println("Ile próbek  ===========  " + help.getProbki()[0].length);
            // System.out.println("Ile próbek  ===========  "+help.getLabel());
            wartosciProbek.add(help.getProbki());
        }
        System.out.println("Pobrano próbek = " + wartosciProbek.size());
        double[][] wynikDzialania = new double[2][wartosciProbek.get(0)[0].length];
        wynikDzialania[0] = wartosciProbek.get(0)[0];
        // double[] helpWD={0.5,0.6};
        //  wynikDzialania[0]=helpWD;
        // System.out.println(" WYNIK DZIAŁANIA  ===========  " + wynikDzialania[1].length);
        switch (znak) {
            case "+":
                for (int i = 0; i < wynikDzialania[0].length; i++) {
                    wynikDzialania[1][i] = 0;
                    for (double[][] p : wartosciProbek) {
                        wynikDzialania[1][i] += p[1][i];
                    }

                }
                break;
            case "*":
                for (int i = 0; i < wynikDzialania[0].length; i++) {
                    wynikDzialania[1][i] = 1;
                    for (double[][] p : wartosciProbek) {
                        //               System.out.println("TEST PRÓBKI = "+wynikDzialania[1][i]);
                        wynikDzialania[1][i] *= p[1][i];
                    }

                }
            case "splot":
                wynikDzialania = new double[2][wartosciProbek.get(0)[0].length + wartosciProbek.get(1)[0].length - 1];
                //2 * wartosciProbek.get(0)[0].length + 2
                logs.setText("---------------Splatanie---------------");
                wynikDzialania[0] = wartosciProbek.get(0)[0];
                int licznik = 0;
                for (int i = 0; i < wynikDzialania[1].length; i++) {
                    wynikDzialania[1][i] = 0;
                    for (int k = 0; k <= wartosciProbek.get(0)[1].length - 1; k++) {
                        if (k < wartosciProbek.get(0)[1].length && i - k >= 0 && i - k < wartosciProbek.get(1)[1].length) {
                            // licznik++;
                            int h = i - k;
                            logs.appendText("\nk = " + k + ", h=" + h + " \n ");
                            wynikDzialania[1][i] += wartosciProbek.get(0)[1][k] * wartosciProbek.get(1)[1][h];
                        }

                    }
                    licznik++;
                    logs.appendText("\n" + licznik + "-------------------------------------\n");
                    //   System.out.println("Splot = " + wynikDzialania[1][i]);
                }
        }

        Sygnał S = new Sygnał();
        S.setProbki(wynikDzialania);
        S.setLabel(nN);
        S.setWariancja(round(wyznaczWariancja(S.getProbki()[1]), 2));
        S.setMoc_srednia(round(wyznaczSredniaMoc(S.getProbki()[1]), 2));
        S.setWartosc_srednia(round(wyznaczSrednia(S.getProbki()[1]), 2));
        S.setWartosc_skuteczna(round(wyznaczWartoscSkuteczna(S.getProbki()[1]), 2));
        //  S.setMoc_srednia(ileSygnalow);
        //    System.out.println("NAZWA NOWA  = " + S.getLabel());
        magazyn.add(S);
        ListaDostepnych.getItems().add(S.getLabel());

        //double[][] probki = new double[ileSygnalow][]
    }

    public void cleanClick() {
        Start.setValue(0.0);
        Koniec.setValue(0.0);
        A.setValue(0.0);
        Podzial.setValue(0.0);
        Rodzaj.setValue("");
        for (int i = 0; i < wykresDyskretny.getData().size(); i++) {
            wykresDyskretny.getData().remove(i);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void przeniesSygnal() {
        sygnalDoDzialania(ListaDostepnych.getSelectionModel().getSelectedItems());
    }

    public void cofnijSygnal() {
        usunDzialania(listaWybranych.getSelectionModel().getSelectedItems());
    }

    public void sygnalDoDzialania(List<String> listaNazw) {
        for (String s : listaNazw) {
            listaWybranych.getItems().add(s);
        }

    }

    public void usunDzialania(List<String> listaNazw) {
        for (String s : listaNazw) {
            listaWybranych.getItems().remove(s);
            magazyn.remove(zwrocSygnal(s));
        }

    }

    public void wyczyscPrezentacje() {
        for (int i = 0; i < wykresPrezentacji.getData().size(); i++) {
            wykresPrezentacji.getData().remove(i);
        }
    }

    public void usunDostepny() {
        usunDostepne(ListaDostepnych.getSelectionModel().getSelectedItems());
    }

    public void usunDostepne(List<String> listaNazw) {
        for (String s : listaNazw) {
            ListaDostepnych.getItems().remove(s);
        }

    }

    public void zapiszDoPliku() throws InvalidFile {
        PrintWriter zapis = null;
        try {
            //Sygnał s_do_pliku = new Sygnał();
            Sygnał s_do_pliku = zwrocSygnal(listaWybranych.getSelectionModel().getSelectedItem());
            zapis = new PrintWriter(s_do_pliku.getLabel() + ".txt");
            zapis.print(s_do_pliku.getLabel());
            zapis.println();
            for (int i = 0; i < s_do_pliku.getProbki()[0].length; i++) {
                zapis.println(round(s_do_pliku.getProbki()[0][i], 2) + " ; " + s_do_pliku.getProbki()[1][i] + "  ");
                // zapis.p
            }
            zapis.close();
        } catch (FileNotFoundException ex) {
            throw new InvalidFile();
        } finally {
            zapis.close();
        }

    }

    public void czytajZpliku() throws InvalidName, InvalidFile {
        FileChooser fc = new FileChooser();
        File sF = fc.showOpenDialog(new Stage());
        System.out.println(sF.getAbsolutePath());
        Sygnał s1 = new Sygnał();
        // FileReader fR=new FileReader(sF.getAbsolutePath());
        // BufferedReader bR=new BufferedReader(fR);
        Scanner sR;
        try {
            sR = new Scanner(sF);
        } catch (FileNotFoundException ex) {
            throw new InvalidFile();
        }
        s1.setLabel(sR.nextLine());
        s1.setFunkcja(sR.nextLine());
        s1.setOkres(Double.parseDouble(sR.nextLine()));
        List<Double> helpProbkiLista0 = new ArrayList<>();
        List<Double> helpProbkiLista1 = new ArrayList<>();
        //   System.out.println(sR.nextLine().split(",")[0]);
        //   int i=0;
        while (sR.hasNextLine()) {
            String next = sR.nextLine();
            helpProbkiLista0.add(Double.parseDouble((String) next.split(";")[0]));
            helpProbkiLista1.add(Double.parseDouble((String) next.split(";")[1]));

        }
        double[][] helpProbki = new double[2][helpProbkiLista0.size()];
        for (int i = 0; i < helpProbkiLista0.size(); i++) {
            helpProbki[0][i] = helpProbkiLista0.get(i);
            helpProbki[1][i] = helpProbkiLista1.get(i);
        }
        s1.setProbki(helpProbki);
        s1.setWariancja(round(wyznaczWariancja(s1.getProbki()[1]), 2));
        s1.setWartosc_srednia(round(wyznaczSrednia(s1.getProbki()[1]), 2));
        s1.setMoc_srednia(round(wyznaczSredniaMoc(s1.getProbki()[1]), 2));
        s1.setWartosc_skuteczna(round(wyznaczWartoscSkuteczna(s1.getProbki()[1]), 2));
        List<Sygnał> listaSyg2 = new ArrayList<Sygnał>();
        //   System.out.println("ROZMIAR LISTY 0 =  "+listaSyg2.size());
        listaSyg2.add(zwrocSygnal(s1.getLabel()));
        //  System.out.println("ROZMIAR LISTY 1 =  "+listaSyg2.size());
        if (magazyn.size() > 0 && listaSyg2.size() > 0) {
            for (int i = 0; i < listaSyg2.size(); i++) {
                try {
                    if (listaSyg2.get(i).getLabel().equals(s1.getLabel())) {
                        listaSyg2.remove(listaSyg2.get(i));
                    }
                    throw new InvalidName();
                } catch (NullPointerException e) {
                }
            }

        }

        magazyn.add(s1);
        ListaDostepnych.getItems().add(s1.getLabel());
    }

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
