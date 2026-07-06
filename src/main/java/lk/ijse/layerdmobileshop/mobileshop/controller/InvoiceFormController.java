package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class InvoiceFormController {

    @FXML
    private WebView webView;

    public void loadInvoice(String html){

        WebEngine engine = webView.getEngine();

        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState)->{

            if(newState == Worker.State.SUCCEEDED){

                Platform.runLater(()->{

                    PrinterJob job = PrinterJob.createPrinterJob();

                    if(job != null){

                        if(job.showPrintDialog(webView.getScene().getWindow())){

                            engine.print(job);
                            job.endJob();

                        }

                    }

                });

            }

        });

        engine.loadContent(html);

    }

}