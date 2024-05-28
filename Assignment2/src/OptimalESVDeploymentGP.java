import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        // TODO: Your code goes here
        int minNumESVsToDeploy = 0;
        Collections.sort(maintenanceTaskEnergyDemands, Collections.reverseOrder());
        ArrayList<Integer> remainingEnCap = new ArrayList<>();
        for(int i=0; i<maxNumberOfAvailableESVs; i++) {
            remainingEnCap.add(maxESVCapacity);
        }
        for(int i=0; i<maxNumberOfAvailableESVs; i++) {
            maintenanceTasksAssignedToESVs.add(i, new ArrayList<>());
        }
        boolean canBeAccommodated = false;
        for(int i=0; i<maintenanceTaskEnergyDemands.size() ; i++) {
            canBeAccommodated = false;
            for(int j=0; j<remainingEnCap.size() ; j++) {
                if(maintenanceTaskEnergyDemands.get(i) <= remainingEnCap.get(j)) {
                    if(remainingEnCap.get(j) == maxESVCapacity) {
                        minNumESVsToDeploy++;
                    }
                    remainingEnCap.set(j, remainingEnCap.get(j) - maintenanceTaskEnergyDemands.get(i));
                    maintenanceTasksAssignedToESVs.get(j).add(maintenanceTaskEnergyDemands.get(i));
                    canBeAccommodated = true;
                    break;
                }
            }
            if(!canBeAccommodated) {
                return -1;
            }
        }
        return minNumESVsToDeploy;
    }
}
