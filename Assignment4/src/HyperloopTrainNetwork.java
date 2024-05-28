import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;

    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     * @return the result as int
     */
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]*)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*(\\d+.?\\d*)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Point p = new Point(0, 0);
        Pattern pt = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*" + "\\([\\t ]*" + "([0-9]+)[\\t ]*,[\\t ]*([0-9]+)[\\t ]*\\)");
        Matcher m = pt.matcher(fileContent);
        m.find();
        p.x = Integer.parseInt(m.group(1));
        p.y = Integer.parseInt(m.group(2));
        return p;
    } 

    /**
     * Function to extract the train lines from the fileContent by reading train line names and their 
     * respective stations.
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();

        Pattern pt = Pattern.compile("[\\t ]*" + "train_line_name" + "[\\t ]*=[\\t ]*\"([^\"]*)\"" +
                "[\\t ]*" + "train_line_stations" + "[\\t ]*=[\\t ]*" + "[\\([\\t ]*([0-9]+)[\\t ]*,[\\t ]*([0-9]+)[\\t ]*\\)]*" );
        Matcher m = pt.matcher(fileContent);
        while(m.find()) {
            String lineName = m.group(1);
            TrainLine trainLine = new TrainLine(lineName, new ArrayList<>());
            Pattern station_pt = Pattern.compile("[\\t ]*\\([\\t ]*([0-9]+)[\\t ]*,[\\t ]*([0-9]+)[\\t ]*\\)[\\t ]*");
            Matcher station_m = station_pt.matcher(m.group());
            int stationNum = 1;
            while(station_m.find()) {
                Point p = new Point(Integer.parseInt(station_m.group(1)), Integer.parseInt(station_m.group(2)));
                Station station = new Station(p,lineName + " Line Station " + stationNum);
                stationNum++;
                trainLine.trainLineStations.add(station);
            }
            trainLines.add(trainLine);
        }

        return trainLines;
    }

    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {

        try {
            List<String> readLines = Files.readAllLines(Paths.get(filename));
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < readLines.size(); i++) {
                str.append(readLines.get(i));
            }
            numTrainLines = getIntVar("num_train_lines", str.toString());
            averageTrainSpeed = getDoubleVar("average_train_speed", str.toString())*1000/60;
            Point startingPoint = getPointVar("starting_point", str.toString());
            startPoint = new Station(startingPoint, "Starting Point");
            Point destinatingPoint = getPointVar("destination_point", str.toString());
            destinationPoint = new Station(destinatingPoint, "Final Destination");
            lines = getTrainLines(str.toString());

        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}