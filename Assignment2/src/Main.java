import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // TODO: Your code goes here
        // You are expected to read the file given as the first command-line argument to read 
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        // PowerGridOptimization object. You need to call GetOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.

        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        String[] strInfo = lines.get(0).split(" ");
        ArrayList<Integer> D = new ArrayList<>();
        int total=0;
        for(int i=0; i< strInfo.length; i++) {
            D.add(i, Integer.parseInt(strInfo[i]));
            total += D.get(i);
        }

        PowerGridOptimization powerGridOptimization = new PowerGridOptimization(D);
        OptimalPowerGridSolution optimalPowerGridSolution = powerGridOptimization.getOptimalPowerGridSolutionDP();

        System.out.println("The total number of demanded gigawatts: "+total);
        System.out.println("Maximum number of satisfied gigawatts: " +optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands());
        System.out.print("Hours at which the battery bank should be discharged: ");
        for(int i=0; i<optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().size(); i++) {
            if(i == optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().size()-1) {
                System.out.println(optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().get(i));
                break;
            }
            System.out.print(optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().get(i) + ", ");
        }
        System.out.println("The number of unsatisfied gigawatts: " + (total - optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands()));
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements 
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.

        List<String> lines2 = Files.readAllLines(Paths.get(args[1]));
        String[] strAvail = lines2.get(0).split(" ");
        ArrayList<Integer> availESV = new ArrayList<>();

        for(int i=0; i< strAvail.length; i++) {
            availESV.add(i, Integer.parseInt(strAvail[i]));
        }
        String[] strEnReq = lines2.get(1).split(" ");
        ArrayList<Integer> energyRequirement = new ArrayList<>();
        for(int i=0; i< strEnReq.length; i++) {
            energyRequirement.add(i, Integer.parseInt(strEnReq[i]));
        }

        OptimalESVDeploymentGP optimalESVDeploymentGP = new OptimalESVDeploymentGP(energyRequirement);
        int minESV = optimalESVDeploymentGP.getMinNumESVsToDeploy(availESV.get(0), availESV.get(1));
        if(minESV == -1) {
            System.out.println("Warning: Mission Eco-Maintenance Failed.");
        }else {
            System.out.println("The minimum number of ESVs to deploy: "+ minESV);
            ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = optimalESVDeploymentGP.getMaintenanceTasksAssignedToESVs();
            for(int i=0; i< minESV; i++) {
                System.out.println("ESV " + (i+1) + " tasks: " + maintenanceTasksAssignedToESVs.get(i));
            }
        }
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
}
