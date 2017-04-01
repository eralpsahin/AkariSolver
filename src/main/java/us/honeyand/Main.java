package us.honeyand;


import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;

import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        AkariBoard akari = new AkariBoard("puzzle2.txt", 8, 8);
        akari.printMatrix();
        Model model = new Model("Akari (Light Up)");
        BoolVar[] vars = new BoolVar[akari.getVarCount()];

        //IntVar[] vars1 = new IntVar[akari.getVarCount()];

        int index = 0;
        for (int i = 0; i < akari.getBoard().length; i++) {
            for (int j = 0; j < akari.getBoard()[0].length; j++) {
                if (akari.getBoard()[i][j] == 'E') {
                    vars[index] = model.boolVar("Tile_" + i + j);
                    //vars1[index] = model.intVar("Tile_"+i+j,new int[]{0, 1});
                    index++;
                }
                if (akari.getBoard()[i][j] == 'B' || akari.getBoard()[i][j] == '0' || akari.getBoard()[i][j] == '1' || akari.getBoard()[i][j] == '2' || akari.getBoard()[i][j] == '3' || akari.getBoard()[i][j] == '4') {
                    int value = Character.getNumericValue(akari.getBoard()[i][j]);
                    akari.addToTheList(new BlackCell(value, i, j)); //11 Means Blocked without any given number
                }
            }
        }


        for (BlackCell cell : akari.getBlackCellList()) {
            System.out.println("\n" + cell);

            if (cell.value == 11)
                continue;
            List<String> neighbor = akari.getNeighborFromBlackCell(cell);

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
            model.sum(newArray, "=", cell.value).post();
        }
        for (int i = 0; i < index; i++) {
            List<String> sight = akari.getSightFromTile(vars[i].getName());

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
            model.sum(newArray,">",0).post();
            model.sum(newArray,"<",3).post();
        }

        for(int i =0;i<index;i++) {
            List<String> sight = akari.getSightRow(vars[i].getName());

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
            model.sum(newArray,"<",2).post();
        }
        for(int i =0;i<index;i++) {
            List<String> sight = akari.getSightColumn(vars[i].getName());

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
            model.sum(newArray,"<",2).post();
        }


        Solver solver = model.getSolver();
        solver.showStatistics();
        Solution solution = solver.findSolution();
        System.out.println(solution.toString());

    }

}
