/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cps;

/**
 *
 * @author Ewa Kulesza
 */
public class Sygnał {

    Sygnał() {
        f=0.0;
    }
    ;

    public double[][] getProbki() {
        return probki;
    }

    public void setProbki(double[][] probki) {
        this.probki = probki;
    }
    
      public double getf() {
        return f;
    }

    public void setf(double f) {
        this.f=f;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getWartosc_srednia() {
        return wartosc_srednia;
    }

    public void setWartosc_srednia(double wartosc_srednia) {
        this.wartosc_srednia = wartosc_srednia;
    }

    public double getMoc_srednia() {
        return moc_srednia;
    }

    public void setMoc_srednia(double moc_srednia) {
        this.moc_srednia = moc_srednia;
    }

      public double getOkres() {
        return okres;
    }

    public void setOkres(double T) {
        this.okres = T;
    }
    
    public double getWariancja() {
        return wariancja;
    }

    public void setWariancja(double wariancja) {
        this.wariancja = wariancja;
    }
    
       public String getFunkcja() {
        return funkcja;
    }

    public void setFunkcja(String f) {
        this.funkcja = f;
    }

    public double getWartosc_skuteczna() {
        return wartosc_skuteczna;
    }

    public void setWartosc_skuteczna(double wartosc_skuteczna) {
        this.wartosc_skuteczna = wartosc_skuteczna;
    }
    
    public double[][] probki;
    public String label;
    public double wartosc_srednia;
    public double moc_srednia;
    public double wariancja;
    public double wartosc_skuteczna;
    public double f;
    public String funkcja;
    public double okres;

}
