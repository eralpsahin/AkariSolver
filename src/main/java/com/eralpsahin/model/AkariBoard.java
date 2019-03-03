package com.eralpsahin.model;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AkariBoard {
    private int row;
    private int column;
    private char board[][];
    private int varCount;
    private List<BlackCell> blackCellList;
    BoolVar[] vars;
    public List<BlackCell> getBlackCellList() {
        return blackCellList;
    }

    public AkariBoard(String fileName, int row, int column) {
        String line;
        this.row = row;
        this.column = column;
        varCount = 0;
        board = new char[row][column];

        blackCellList = new ArrayList<>();
        try {
            FileReader fileReader =
                    new FileReader(fileName);

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

    public void printMatrix() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("********************");
    }
    public void addToTheList(BlackCell blackCell) {
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

    /*
    Param: Blackcell
    Returns: String array consisted of Empty Cell coordinates of the cell neighbor.
     */
    List<String> getNeighborFromBlackCell(BlackCell cell) {
        List<String> result = new ArrayList<String>();

        if(cell.row != 0) { //Cant have empty cell on 1 up
            if(!isBlackCell(cell.row-1,cell.column)) {
                result.add((cell.row-1)+"_"+cell.column);
            }
        }
        if(cell.column != 0) { //Cant have empty cell on 1 left
            if(!isBlackCell(cell.row,cell.column-1)) {
                result.add(cell.row+"_"+(cell.column-1));
            }
        }
        if(cell.row != this.row-1) { //Cant have empty cell on 1 bottom
            if(!isBlackCell(cell.row+1,cell.column)) {
                result.add((cell.row+1)+"_"+cell.column);
            }
        }
        if(cell.column != this.column-1) { //Cant have emtpy cell on 1 right
            if(!isBlackCell(cell.row,cell.column+1)) {
                result.add(cell.row+"_"+(cell.column+1));
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
        int row = Integer.parseInt(tile.substring(tile.indexOf('_')+1,tile.lastIndexOf('_')));
        int column = Integer.parseInt(tile.substring(tile.lastIndexOf('_')+1));

        System.out.println(row+"**"+column);
        sightColumn.addAll(getSightToTop(row,column));
        sightColumn.addAll(getSightToBottom(row,column));

        sightRow.addAll(getSightToLeft(row,column));
        sightRow.addAll(getSightToRight(row,column));

        List<String> result = new ArrayList<>();
        result.addAll(sightColumn);
        result.addAll(sightRow);
        result.add(row+"_"+column);
        for (String str: result) {
            System.out.println(str);
        }

        return result;
    }
    List<String> getSightColumn(String tile) {
        List<String> sightColumn = new ArrayList<String>();

        int row = Integer.parseInt(tile.substring(tile.indexOf('_')+1,tile.lastIndexOf('_')));
        int column = Integer.parseInt(tile.substring(tile.lastIndexOf('_')+1));

        sightColumn.addAll(getSightToTop(row,column));
        sightColumn.addAll(getSightToBottom(row,column));




        sightColumn.add(row+"_"+column);

        return sightColumn;
    }

    List<String> getSightRow(String tile) {
        List<String> sightRow = new ArrayList<String>();


        int row = Integer.parseInt(tile.substring(tile.indexOf('_')+1,tile.lastIndexOf('_')));
        int column = Integer.parseInt(tile.substring(tile.lastIndexOf('_')+1));

        sightRow.addAll(getSightToLeft(row,column));
        sightRow.addAll(getSightToRight(row,column));


        sightRow.add(row+"_"+column);

        return sightRow;
    }

    private List<String> getSightToTop(int row, int column) {
        List<String> result = new ArrayList<String>();
        row--;
        while(row != -1 && !isBlackCell(row,column))
        {
            result.add(row+"_"+column);
            row--;
        }
        return result;
    }
    private List<String> getSightToBottom(int row,int column) {
        List<String> result = new ArrayList<String>();
        row++;
        while(row !=this.row && !isBlackCell(row,column))
        {
            result.add(row+"_"+column);
            row++;
        }
        return result;
    }

    private List<String> getSightToLeft(int row,int column) {
        List<String> result = new ArrayList<String>();
        column--;
        while(column !=-1 && !isBlackCell(row,column))
        {
            result.add(row+"_"+column);
            column--;
        }
        return result;
    }
    private List<String> getSightToRight(int row,int column) {
        List<String> result = new ArrayList<String>();
        column++;
        while(column !=this.column && !isBlackCell(row,column))
        {
            result.add(row+"_"+column);
            column++;
        }
        return result;
    }
    public String solvePuzzle() {
        Model model = new Model("Akari (Light Up)");
        vars = new BoolVar[getVarCount()];

        int index = 0;
        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[0].length; j++) {
                if (getBoard()[i][j] == 'E') {
                    vars[index] = model.boolVar("Tile_" + i + "_"+ j);
                    index++;
                }
                if (getBoard()[i][j] == 'B' || getBoard()[i][j] == '0' || getBoard()[i][j] == '1' || getBoard()[i][j] == '2' || getBoard()[i][j] == '3' || getBoard()[i][j] == '4') {
                    int value = Character.getNumericValue(getBoard()[i][j]);
                    addToTheList(new BlackCell(value, i, j)); //11 Means Blocked without any given number
                }
            }
        }


        for (BlackCell cell : getBlackCellList()) {
            System.out.println("\n" + cell);

            if (cell.getValue() == 11)
                continue;
            List<String> neighbor = getNeighborFromBlackCell(cell);

            BoolVar[] newArray = Arrays.copyOfRange(vars, 0, cell.getNeighbor());
            int n = 0;
            for (String str : neighbor) {

                for (int i = 0; i < vars.length; i++) {
                    if (vars[i].getName().equals("Tile_" + str)) {
                        System.out.println(i + ": " + vars[i].getName());
                        newArray[n] = vars[i];
                        n++;
                    }
                }
            }
            model.sum(newArray, "=", cell.getValue()).post();
        }
        for (int i = 0; i < index; i++) {
            List<String> sight = getSightFromTile(vars[i].getName());

            BoolVar[] newArray = Arrays.copyOfRange(vars, 0, sight.size());
            int n = 0;
            for (String str : sight) {

                for (int j = 0; j < vars.length; j++) {
                    if (vars[j].getName().equals("Tile_" + str)) {

                        newArray[n] = vars[j];
                        System.out.println("NewArray Eleman" + n + ": " + vars[j].getName());
                        n++;
                        break;
                    }
                }
            }
            model.sum(newArray, ">", 0).post();
            model.sum(newArray, "<", 3).post();
        }

        for (int i = 0; i < index; i++) {
            List<String> sight = getSightRow(vars[i].getName());

            BoolVar[] newArray = Arrays.copyOfRange(vars, 0, sight.size());
            int n = 0;
            for (String str : sight) {

                for (int j = 0; j < vars.length; j++) {
                    if (vars[j].getName().equals("Tile_" + str)) {

                        newArray[n] = vars[j];
                        System.out.println("NewArray Eleman" + n + ": " + vars[j].getName());
                        n++;
                        break;
                    }
                }
            }
            model.sum(newArray, "<", 2).post();
        }
        for (int i = 0; i < index; i++) {
            List<String> sight = getSightColumn(vars[i].getName());

            BoolVar[] newArray = Arrays.copyOfRange(vars, 0, sight.size());
            int n = 0;
            for (String str : sight) {

                for (int j = 0; j < vars.length; j++) {
                    if (vars[j].getName().equals("Tile_" + str)) {

                        newArray[n] = vars[j];
                        System.out.println("NewArray Eleman" + n + ": " + vars[j].getName());
                        n++;
                        break;
                    }
                }
            }
            model.sum(newArray, "<", 2).post();
        }

        Solver solver = model.getSolver();
        solver.findSolution();

        return solver.getMeasures().toString();
    }

    public BoolVar[] getVars() {
        return vars;
    }
}
