package com.eralpsahin.controller;

import com.eralpsahin.helper.FileHelper;
import com.eralpsahin.model.AkariBoard;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Modified by eralpsahin on 03.03.2019.
 */
public class AppController {

    @FXML
    TextArea detailText;
    @FXML
    Button solveButton;
    @FXML
    private Pane akariPane;
    private StackPane[][] tile;
    private AkariBoard akari;

    public void initialize() {


    }

    @FXML
    protected void handleNewPuzzleMenuItem() {
        akariPane.getChildren().clear();
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(akariPane.getScene().getWindow());
        if (file == null)
            return;
        detailText.setText("");
        solveButton.setVisible(true);
        akari = FileHelper.readFile(file.toPath().toString());
        int row = akari.getRow();
        int col = akari.getColumn();
        double width = akariPane.prefWidthProperty().get() / col;
        double height = akariPane.prefHeightProperty().get() / row;
        char[][] board = akari.getBoard();
        akari.printMatrix();
        tile = new StackPane[row][col];
        for (int i = col - 1; i >= 0; i--)
            for (int j = row - 1; j >= 0; j--) {
                tile[j][i] = new StackPane();
                tile[j][i].setPrefSize(height, width);
                tile[j][i].relocate(i * width, j * width);

                tile[j][i].setStyle("-fx-border-color: black; -fx-border-width: 0.1");


                switch (board[j][i]) {
                    case 'B':
                        tile[j][i].setStyle("-fx-background-color: black;-fx-border-color: white; -fx-border-width: 0.1");
                        break;
                    case '0':
                        tile[j][i].setStyle("-fx-background-color: black;-fx-border-color: white; -fx-border-width: 0.1");
                        tile[j][i].getChildren().add(createText("0"));
                        break;

                    case '1':
                        tile[j][i].setStyle("-fx-background-color: black;-fx-border-color: white; -fx-border-width: 0.1");
                        tile[j][i].getChildren().add(createText("1"));
                        break;

                    case '2':
                        tile[j][i].setStyle("-fx-background-color: black;-fx-border-color: white; -fx-border-width: 0.1");
                        tile[j][i].getChildren().add(createText("2"));
                        break;

                    case '3':
                        tile[j][i].setStyle("-fx-background-color: black;-fx-border-color: white; -fx-border-width: 0.1");
                        tile[j][i].getChildren().add(createText("3"));
                        break;

                    case '4':
                        tile[j][i].setStyle("-fx-background-color: black;-fx-border-color: white; -fx-border-width: 0.1");
                        tile[j][i].getChildren().add(createText("4"));
                        break;
                }

                akariPane.getChildren().add(tile[j][i]);
            }
    }

    @FXML
    protected void handleQuitMenuItem() {
        Platform.exit();
    }

    @FXML
    protected void handleSolveButton() {

        //Solve in background thread
        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        String details = akari.solvePuzzle();
                        Platform.runLater(() -> {
                            detailText.setVisible(true);
                            detailText.setText(details);
                            double height = akariPane.prefHeightProperty().get() / akari.getRow();
                            double width = akariPane.prefWidthProperty().get() / akari.getColumn();

                            for (int i = 0; i < akari.getVarCount(); i++) {
                                int row = Integer.parseInt(akari.getVars()[i].getName().substring(akari.getVars()[i].getName().indexOf('_') + 1, akari.getVars()[i].getName().lastIndexOf('_')));
                                int column = Integer.parseInt(akari.getVars()[i].getName().substring(akari.getVars()[i].getName().lastIndexOf('_') + 1));

                                if (akari.getVars()[i].getValue() == 1) {
                                    Image image = new Image("/lightbulb.png", height, width, false, false);
                                    ImageView imgView = new ImageView(image);
                                    tile[row][column].getChildren().add(imgView);
                                }

                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    private Text createText(String n) {
        Text t = new Text(n);
        t.setStyle("}javafx_dummy_selector {-fx-font-size: 15px");
        t.setFill(Color.WHITE);
        return t;
    }
}
