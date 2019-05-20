package com.eralpsahin.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    private static final double TILE_SIZE = 40;

    public Tile(int x, int y, char[][] board, int x_tiles, int y_tiles) {

        Rectangle border = new Rectangle(TILE_SIZE, TILE_SIZE, Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        getChildren().add(border);
        setTranslateX(x * TILE_SIZE - TILE_SIZE * (y_tiles - 1) / 2);
        setTranslateY(y * TILE_SIZE - TILE_SIZE * x_tiles / 2);
        setStyle("-fx-background-color: black;");
        switch (board[y][x]) {
            case 'B':
                break;
            case '0':
                getChildren().add(createText("0"));
                break;

            case '1':
                getChildren().add(createText("1"));
                break;

            case '2':
                getChildren().add(createText("2"));
                break;

            case '3':
                getChildren().add(createText("3"));
                break;

            case '4':
                getChildren().add(createText("4"));
                break;
            default:
                setStyle("-fx-background-color: lightgrey;");
                break;

        }
    }

    private Text createText(String n) {
        Text t = new Text(n);
        t.setStyle("-fx-font-size: 18px");
        t.setFill(Color.WHITE);
        return t;
    }

}
