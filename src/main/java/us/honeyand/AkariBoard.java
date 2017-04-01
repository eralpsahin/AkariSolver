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

    public List<BlackCell> getBlackCellList() {
        return blackCellList;
    }

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
    /*
    Param: Blackcell
    Returns: String array consisted of Empty Cell coordinates of the cell neighbor.
     */
    List<String> getNeighborFromBlackCell(BlackCell cell) {
        List<String> result = new ArrayList<String>();

        if(cell.row != 0) { //Cant have empty cell on 1 up
            if(!isBlackCell(cell.row-1,cell.column)) {
                result.add((cell.row-1)+""+cell.column);
            }
        }
        if(cell.column != 0) { //Cant have empty cell on 1 left
            if(!isBlackCell(cell.row,cell.column-1)) {
                result.add(cell.row+""+(cell.column-1));
            }
        }
        if(cell.row != this.row-1) { //Cant have empty cell on 1 bottom
            if(!isBlackCell(cell.row+1,cell.column)) {
                result.add((cell.row+1)+""+cell.column);
            }
        }
        if(cell.column != this.column-1) { //Cant have emtpy cell on 1 right
            if(!isBlackCell(cell.row,cell.column+1)) {
                result.add(cell.row+""+(cell.column+1));
            }
        }
        cell.neighbor = result.size();
        return result;
    }

    boolean isBlackCell(int row, int column) {
        for (BlackCell cell: blackCellList) {
            if(cell.row == row && cell.column == column)
                return true;
        }
        return false;
    }

    List<String> getSightFromTile(String tile) {
        List<String> sightColumn = new ArrayList<String>();
        List<String> sightRow = new ArrayList<String>();

        System.out.println("\n"+tile);
        int row = tile.charAt(5)-48,column = tile.charAt(6)-48;

        sightColumn.addAll(getSightToTop(row,column));
        sightColumn.addAll(getSightToBottom(row,column));

        sightRow.addAll(getSightToLeft(row,column));
        sightRow.addAll(getSightToRight(row,column));

        List<String> result = new ArrayList<>();
        result.addAll(sightColumn);
        result.addAll(sightRow);
        result.add(row+""+column);
        for (String str: result) {
            System.out.println(str);
        }

        return result;
    }
    List<String> getSightColumn(String tile) {
        List<String> sightColumn = new ArrayList<String>();

        int row = tile.charAt(5)-48,column = tile.charAt(6)-48;

        sightColumn.addAll(getSightToTop(row,column));
        sightColumn.addAll(getSightToBottom(row,column));




        sightColumn.add(row+""+column);

        return sightColumn;
    }

    List<String> getSightRow(String tile) {
        List<String> sightRow = new ArrayList<String>();


        int row = tile.charAt(5)-48,column = tile.charAt(6)-48;

        sightRow.addAll(getSightToLeft(row,column));
        sightRow.addAll(getSightToRight(row,column));


        sightRow.add(row+""+column);

        return sightRow;
    }

    private List<String> getSightToTop(int row, int column) {
        List<String> result = new ArrayList<String>();
        row--;
        while(row != -1 && !isBlackCell(row,column))
        {
            result.add(row+""+column);
            row--;
        }
        return result;
    }
    private List<String> getSightToBottom(int row,int column) {
        List<String> result = new ArrayList<String>();
        row++;
        while(row !=this.row && !isBlackCell(row,column))
        {
            result.add(row+""+column);
            row++;
        }
        return result;
    }

    private List<String> getSightToLeft(int row,int column) {
        List<String> result = new ArrayList<String>();
        column--;
        while(column !=-1 && !isBlackCell(row,column))
        {
            result.add(row+""+column);
            column--;
        }
        return result;
    }
    private List<String> getSightToRight(int row,int column) {
        List<String> result = new ArrayList<String>();
        column++;
        while(column !=this.column && !isBlackCell(row,column))
        {
            result.add(row+""+column);
            column++;
        }
        return result;
    }
}
