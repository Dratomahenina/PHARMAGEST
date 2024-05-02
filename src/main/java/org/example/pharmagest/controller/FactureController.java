package org.example.pharmagest.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class FactureController {

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    public void setFacturePath(String facturePath) {
        webEngine = webView.getEngine();
        webEngine.load("file:///" + facturePath);
    }
}