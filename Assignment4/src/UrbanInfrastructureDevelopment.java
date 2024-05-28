import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for(int i = 0; i < projectList.size(); i++) {
            int[] earliestSchedule = projectList.get(i).getEarliestSchedule();
            projectList.get(i).printSchedule(earliestSchedule);
        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (int index=0; index< lines.size();index++ ){
                lines.set(index, lines.get(index).trim());
            }
            int i=0;
            while(i != lines.size()) {
                if(i >= lines.size()) {
                    break;
                }
                if(lines.get(i).equals("<Project>")) {
                    String projectName = "";
                    List<Task> tasks = new ArrayList<>();
                    i++;
                    while(!lines.get(i).equals("</Project>")) {
                        if(i >= lines.size()) {
                            break;
                        }
                        //System.out.println(i + " " + lines.get(i));
                        if(lines.get(i).startsWith("<Name>")) {
                            projectName = lines.get(i).substring(6, lines.get(i).length()-7);
                        }
                        i++;
                        if(i >= lines.size()) {
                            break;
                        }
                        if(lines.get(i).equals("<Tasks>")) {

                            i++;
                            while(!lines.get(i).equals("</Tasks>")) {
                                if(lines.get(i).equals("<Task>")) {
                                    int id = 0;
                                    String description = "";
                                    int duration = 0;
                                    List<Integer> dependencies = new ArrayList<>();
                                    i++;
                                    while(!lines.get(i).equals("</Task>")) {
                                        if(lines.get(i).startsWith("<TaskID>")) {
                                            id = Integer.parseInt(lines.get(i).substring(8, lines.get(i).length()-9));
                                        }else if(lines.get(i).startsWith("<Description>")) {
                                            description = lines.get(i).substring(13, lines.get(i).length()-14);
                                        }else if(lines.get(i).startsWith("<Duration>")) {
                                            duration = Integer.parseInt(lines.get(i).substring(10, lines.get(i).length()-11));
                                        }else if(lines.get(i).equals("<Dependencies>")) {
                                            i++;
                                            while(!lines.get(i).equals("</Dependencies>")) {
                                                dependencies.add(Integer.parseInt(lines.get(i).substring(17, lines.get(i).length()-18)));
                                                i++;
                                            }
                                        }
                                        i++;
                                    }
                                    Task task = new Task(id, description, duration, dependencies);
                                    tasks.add(task);
                                }
                                i++;
                            }
                        }
                        i++;
                    }
                    Project project = new Project(projectName, tasks);
                    projectList.add(project);
                }
                i++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return projectList;
    }
}
