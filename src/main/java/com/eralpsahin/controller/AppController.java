package com.eralpsahin.controller;

import com.eralpsahin.helper.FileHelper;
import com.eralpsahin.model.AkariBoard;
import com.eralpsahin.view.Tile;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.FileSystems;

public class AppController {

    @FXML
    TextArea detailText;
    @FXML
    Button solveButton;
    @FXML
    private Pane akariPane;
    private StackPane[][] tile;
    private AkariBoard akari;

    public void initialize() {}

    @FXML
    protected void handleNewPuzzleMenuItem() {
        akariPane.getChildren().clear();
        detailText.setText("");

        final FileChooser fileChooser = new FileChooser();
        // Open project root as default directory
        File root = new File(FileSystems.getDefault().getPath("").toAbsolutePath().toString());
        fileChooser.setInitialDirectory(root);
        File file = fileChooser.showOpenDialog(akariPane.getScene().getWindow());
        if (file == null)
            return;

        akari = FileHelper.readFile(file.toPath().toString());
        int row = akari.getRow();
        int col = akari.getColumn();
        char[][] board = akari.getBoard();
        Pane puzzle = createPuzzle(row, col, board);
        akariPane.getChildren().add(puzzle);
        tile = new StackPane[row][col];
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                tile[x][y] = (StackPane) puzzle.getChildren().get(x * col + y);
            }
        }
        solveButton.setVisible(true);
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

                            for (int i = 0; i < akari.getVarCount(); i++) {
                                int row = Integer.parseInt(akari.getVars()[i].getName().substring(akari.getVars()[i].getName().indexOf('_') + 1, akari.getVars()[i].getName().lastIndexOf('_')));
                                int column = Integer.parseInt(akari.getVars()[i].getName().substring(akari.getVars()[i].getName().lastIndexOf('_') + 1));

                                if (akari.getVars()[i].getValue() == 1) {
                                    Image image = new Image("/lightbulb.png", 40, 40, false, false);
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
    private Pane createPuzzle(int x_tiles, int y_tiles, char[][] board) {
        Pane root = new Pane();
        for (int x = 0; x < x_tiles; x++) {
            for (int y = 0; y < y_tiles; y++) {
                Tile tile = new Tile(y, x, board, x_tiles, y_tiles);
                root.getChildren().add(tile);
            }
        }
        return root;
    }
}
