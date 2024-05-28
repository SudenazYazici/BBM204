import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;
    
    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    /**
     * Function calculate the fastest route from the user's desired starting point to 
     * the desired destination point, taking into consideration the hyperloop train
     * network. 
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();

        List<RouteDirection> allRoutes = new ArrayList<>(); // firstly, defining all route directions

        double temp_distance = Math.sqrt(Math.pow(network.startPoint.coordinates.x - network.destinationPoint.coordinates.x, 2) +
                Math.pow(network.startPoint.coordinates.y - network.destinationPoint.coordinates.y, 2));

        RouteDirection temp_routeDirection = new RouteDirection("Starting Point", "Final Destination",
                temp_distance/network.averageWalkingSpeed, false);
        allRoutes.add(temp_routeDirection);

        for(int j=0; j<network.lines.get(0).trainLineStations.size(); j++ ) {
            double distance = Math.sqrt(Math.pow(network.startPoint.coordinates.x - network.lines.get(0).trainLineStations.get(j).coordinates.x, 2) +
                    Math.pow(network.startPoint.coordinates.y - network.lines.get(0).trainLineStations.get(j).coordinates.y, 2));

            RouteDirection routeDirection = new RouteDirection("Starting Point", network.lines.get(0).trainLineStations.get(j).description,
                    distance/network.averageWalkingSpeed, false);
            allRoutes.add(routeDirection);
        }
        for(int i=0; i<network.lines.size(); i++ ) { // between same train line named stations
            for(int j=0; j<network.lines.get(i).trainLineStations.size(); j++ ) {
                for(int k=0; k<network.lines.get(i).trainLineStations.size(); k++ ) {
                    if(j==k+1 || k==j+1) {
                        double distance = Math.sqrt(Math.pow(network.lines.get(i).trainLineStations.get(j).coordinates.x - network.lines.get(i).trainLineStations.get(k).coordinates.x, 2) +
                                Math.pow(network.lines.get(i).trainLineStations.get(j).coordinates.y - network.lines.get(i).trainLineStations.get(k).coordinates.y, 2));

                        RouteDirection routeDirection = new RouteDirection(network.lines.get(i).trainLineStations.get(j).description, network.lines.get(i).trainLineStations.get(k).description,
                                distance/network.averageTrainSpeed, true);
                        allRoutes.add(routeDirection);
                    }
                }
            }
        }
        for(int i=0; i<network.lines.size()-1; i++ ) { // between differently named stations
            for(int j=0; j<network.lines.get(i).trainLineStations.size(); j++ ) {
                for(int k=0; k<network.lines.get(i+1).trainLineStations.size(); k++ ) {
                    double distance = Math.sqrt(Math.pow(network.lines.get(i).trainLineStations.get(j).coordinates.x - network.lines.get(i+1).trainLineStations.get(k).coordinates.x, 2) +
                            Math.pow(network.lines.get(i).trainLineStations.get(j).coordinates.y - network.lines.get(i+1).trainLineStations.get(k).coordinates.y, 2));

                    RouteDirection routeDirection = new RouteDirection(network.lines.get(i).trainLineStations.get(j).description, network.lines.get(i+1).trainLineStations.get(k).description,
                            distance/network.averageWalkingSpeed, false);
                    allRoutes.add(routeDirection);
                }
            }
        }

        for(int i=0; i<network.lines.size(); i++ ) { // adding route between all stations and the final destination
            for(int j=0; j<network.lines.get(i).trainLineStations.size(); j++ ) {
                double distance = Math.sqrt(Math.pow(network.lines.get(i).trainLineStations.get(j).coordinates.x - network.destinationPoint.coordinates.x, 2) +
                        Math.pow(network.lines.get(i).trainLineStations.get(j).coordinates.y - network.destinationPoint.coordinates.y, 2));

                RouteDirection routeDirection = new RouteDirection(network.lines.get(i).trainLineStations.get(j).description, "Final Destination",
                        distance/network.averageWalkingSpeed, false);
                allRoutes.add(routeDirection);
            }
        }


        //Bellman-Ford Algorithm
        int numOfNodes = 0;
        Map<String, Integer> index = new HashMap<>();
        index.put("Starting Point", numOfNodes);
        for(int i=0; i<network.lines.size(); i++ ) {
            for(int j=0; j<network.lines.get(i).trainLineStations.size(); j++ ) {
                numOfNodes += 1;
                index.put(network.lines.get(i).trainLineStations.get(j).description, numOfNodes);
            }
        }
        numOfNodes += 1;
        index.put("Final Destination", numOfNodes);
        numOfNodes += 1;

        double[] distTo = new double[numOfNodes];
        String[] edgeTo = new String[numOfNodes];
        boolean[] onQ = new boolean[numOfNodes];

        for(int i=0; i<numOfNodes; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[0] = 0.0;
        Queue<String> queue = new LinkedList<>();

        for(int m=0; m<numOfNodes; m++) {
            queue.add("Starting Point");
            while(!queue.isEmpty()) {
                String station = queue.remove();
                onQ[index.get(station)] = false;
                for(int i=0; i<allRoutes.size(); i++) {
                    if(Objects.equals(allRoutes.get(i).startStationName, station)) {

                        //relaxation
                        if(distTo[index.get(allRoutes.get(i).endStationName)] > distTo[index.get(allRoutes.get(i).startStationName)] + allRoutes.get(i).duration) {
                            distTo[index.get(allRoutes.get(i).endStationName)] = distTo[index.get(allRoutes.get(i).startStationName)] + allRoutes.get(i).duration;
                            edgeTo[index.get(allRoutes.get(i).endStationName)] = allRoutes.get(i).startStationName;
                            if(!onQ[index.get(allRoutes.get(i).endStationName)]) {
                                queue.add(allRoutes.get(i).endStationName);
                                onQ[index.get(allRoutes.get(i).endStationName)] = true;
                            }
                        }
                    }
                }
            }
        }

        List<RouteDirection> tempDirections = new ArrayList<>();
        int temp = index.get("Final Destination");
        String curName = "Final Destination";
        while(!curName.equals("Starting Point")){
            String[] tempCurrent = curName.split(" ");
            String[] edgeTemp = edgeTo[temp].split(" ");

            if(tempCurrent[0].equals(edgeTemp[0])) {
                RouteDirection routeDirection = new RouteDirection(edgeTo[temp], curName, distTo[temp] - distTo[index.get(edgeTo[temp])], true );
                tempDirections.add(routeDirection);
            } else {
                RouteDirection routeDirection = new RouteDirection(edgeTo[temp], curName, distTo[temp] - distTo[index.get(edgeTo[temp])], false );
                tempDirections.add(routeDirection);
            }
            curName = edgeTo[temp];
            temp = index.get(curName);
        }
        Collections.reverse(tempDirections);
        routeDirections = tempDirections;

        return routeDirections;
    }

    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        
        double total = 0.0;
        for(int i=0; i<directions.size(); i++) {
            total += directions.get(i).duration;
        }
        DecimalFormat total_df = new DecimalFormat("0");
        System.out.println("The fastest route takes "+ total_df.format(total) +" minute(s).");
        System.out.println("Directions\n----------");
        DecimalFormat df = new DecimalFormat("0.00");
        for(int i=0; i<directions.size(); i++) {
            if(directions.get(i).trainRide) {
                System.out.println((i+1) + ". Get on the train from \"" + directions.get(i).startStationName + "\" to \"" +
                        directions.get(i).endStationName + "\" for "+ df.format(directions.get(i).duration) +" minutes.");
            } else {
                System.out.println((i+1) + ". Walk from \"" + directions.get(i).startStationName + "\" to \"" +
                        directions.get(i).endStationName + "\" for "+ df.format(directions.get(i).duration) +" minutes.");
            }
        }

    }
}