package controller;

import com.sun.javafx.charts.Legend;
import com.sun.javaws.progress.Progress;
import javafx.application.Platform;
import javafx.beans.binding.When;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maciek on 2017-02-11.
 */
public class SecondController implements ControllerInter, Initializable {
    @FXML
    public TextArea massageArea;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Label progress;

    public static JSONObject json;
    private Service<String> backgroundThread;


    public void back(ActionEvent actionEvent) {
        massageArea.getScene().setRoot(new FirstController().getContent());
    }

    public void end(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }


    public Parent getContent() {
        try {
            return FXMLLoader.load(getClass().getResource("/fxml/second.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SecondController setWhatIsProcessed(JSONObject massage) {
        this.json = massage;
        return this;
    }

    public void startThread() {
//To jest do poprawki nie sprawdzac
        backgroundThread = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    StringBuilder results = new StringBuilder();

                    @Override
                    protected String call() throws Exception {
                        long i = 1;
                        String s = null;
                        while (i < 90) {
                            if (isCancelled()) {
                                break;
                            }
                            double k = Math.sqrt(Math.pow(i, 2) / Math.sqrt(i));
                            results.append("i: ").append(i).append(" Count: ").append(k).append("\n");
                            updateValue(results.toString());
                            updateProgress((100 * i) / 90, 90);
                            Thread.sleep(100);
                            i++;
                        }

                        return results.toString();
                    }
                };
            }
        };
        massageArea.textProperty().bind(backgroundThread.valueProperty());
        progressBar.progressProperty().bind(backgroundThread.progressProperty());

        progress.textProperty().bind(new When(backgroundThread.progressProperty().isEqualTo(-1)).then("Unknown")
                .otherwise(backgroundThread.progressProperty().multiply(100).asString("%.2f%%")));
        backgroundThread.start();
        backgroundThread.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("cancel");
            }
        });
        backgroundThread.restart();


//        new Thread(new Runnable() {
//
//            public void run() {
//                Platform.runLater(() -> massageArea.appendText ("Wysłano: \t" + json));
//                for (int i = 0; i < 10; i++) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    final int iteration = i;
//                    Platform.runLater(() -> massageArea.appendText("dupa " + iteration + "\n"));
//                }
//            }
//        }).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startThread();
    }
}
