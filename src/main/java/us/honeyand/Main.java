package us.honeyand;


import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;


public class Main {

    public static void main(String[] args) {
        AkariBoard akari = new AkariBoard("puzzle.txt",7,7);
        akari.printMatrix();
        Model model = new Model("Akari (Light Up)");
        BoolVar[] vars = new BoolVar[akari.getVarCount()];
        int index = 0;
        for(int i = 0; i<akari.getBoard().length;i++) {
            for(int j = 0; j<akari.getBoard()[0].length;j++) {
                if(akari.getBoard()[i][j] == 'E') {
                    vars[index] = model.boolVar("Tile_"+i+j);
                    index++;
                }
                if(akari.getBoard()[i][j] == 'B' || akari.getBoard()[i][j] == '0' || akari.getBoard()[i][j] == '1' || akari.getBoard()[i][j] == '2' || akari.getBoard()[i][j] == '3' || akari.getBoard()[i][j] == '4') {
                    int value = Character.getNumericValue(akari.getBoard()[i][j]);
                    akari.addToTheList(new BlackCell(value,i,j)); //11 Means Blocked without any given number
                }
            }
        }
        for (int i = 0; i <index ; i++) {
            System.out.println(vars[i].getName());
        }
        IntVar[] vars1 = model.intVarArray("Q", 10, 1, 10, false);
        for(int i  = 0; i < 10-1; i++){
            for(int j = i + 1; j < 10; j++){
                model.arithm(vars[i], "!=",vars[j]).post();
                model.arithm(vars[i], "!=", vars[j], "-", j - i).post();
                model.arithm(vars[i], "!=", vars[j], "+", j - i).post();
            }
        }
        model.post(
                model.allDifferent(vars)
        );
        Solver solver = model.getSolver();
        solver.showStatistics();
        solver.setSearch(Search.domOverWDegSearch(vars));
        Solution solution = solver.findSolution();
        akari.printBlackCellList();
        akari.printSolution();
    }
}
