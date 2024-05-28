import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        // TODO: YOUR CODE HERE
        int[] SOL = new int[amountOfEnergyDemandsArrivingPerHour.size()+1];
        SOL[0] = 0;
        ArrayList<ArrayList<Integer>> HOURS = new ArrayList<>();
        HOURS.add( new ArrayList<Integer>());
        ArrayList<Integer> E = new ArrayList<>();
        for(int i=0; i< amountOfEnergyDemandsArrivingPerHour.size(); i++) {
            E.add(i, (i+1)*(i+1));
        }

        for(int j=1; j<amountOfEnergyDemandsArrivingPerHour.size()+1; j++) {
            ArrayList<Integer> maxSOL = new ArrayList<>();
            maxSOL.add(0);
            int tempi = 0;
            for(int i=0; i<j; i++) {
                int tempSOL = SOL[i] + Math.min(amountOfEnergyDemandsArrivingPerHour.get(j-1), E.get(j-i-1));
                if(tempSOL > maxSOL.get(maxSOL.size()-1)) {
                    tempi = i;
                    maxSOL.add(tempSOL);
                }
            }
            ArrayList<Integer> temp = new ArrayList<>();
            temp.addAll(HOURS.get(tempi));
            temp.add(j);
            HOURS.add(temp);
            SOL[j] = maxSOL.get(maxSOL.size()-1);
        }
        OptimalPowerGridSolution optimalPowerGridSolution = new OptimalPowerGridSolution(SOL[SOL.length-1], HOURS.get(HOURS.size()-1));
        return optimalPowerGridSolution;
    }
}
