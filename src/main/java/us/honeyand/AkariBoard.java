package us.honeyand;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AkariBoard {
    private int row;
    private int column;
    private char board[][];
    private int varCount;
    private List<BlackCell> blackCellList;
    AkariBoard(String fileName, int row, int column) {
        String line;
        this.row = row;
        this.column = column;
        varCount = 0;
        board = new char[row][column];
        blackCellList = new ArrayList<BlackCell>();
        try {
            FileReader fileReader =
                    new FileReader("src/" + fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            int r = 0;
            while ((line = bufferedReader.readLine()) != null) {

                String[] words = line.split(" ");
                char[] allchar = new char[words.length];
                for (int i = 0; i < allchar.length; i++) {
                    allchar[i] = words[i].charAt(0);
                    if(allchar[i] == 'E')
                        varCount++;
                }
                board[r] = allchar;
                r++;
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
    }

    void printMatrix() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    void addToTheList(BlackCell blackCell) {
        blackCellList.add(blackCell);
    }

    void printBlackCellList() {
        for(int i = 0; i < blackCellList.size(); i++) {
            System.out.println(blackCellList.get(i));
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public int getVarCount() {
        return varCount;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    void printSolution() {

        char solution[][] = {{'E','E','L','E','E','E','B'}
                            ,{'E','L','4','L','E','E','E'}
                            ,{'0','E','L','E','1','B','L'}
                            ,{'E','E','E','1','L','E','E'}
                            ,{'L','B','1','E','E','L','B'}
                            ,{'E','E','L','E','B','E','L'}
                            ,{'1','L','E','E','E','E','E'}};

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(solution[i][j]+" ");
            }
            System.out.println();
        }
    }
}
