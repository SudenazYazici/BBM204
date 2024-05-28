import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Class representing the mission of Genesis
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {

        try {

            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (int index=0; index< lines.size();index++ ){
                lines.set(index, lines.get(index).replaceAll("\\s", ""));
            }
            int i=0;
            while(i != lines.size()) {
                if(lines.get(i).equals("<HumanMolecularData>")) {
                    List<Molecule> molecules = new ArrayList<>();
                    while(!lines.get(i).equals("</HumanMolecularData>")) {
                        i++;
                        if(lines.get(i).equals("<Molecule>")) {
                            String id = "";
                            int bond = 0;
                            List<String> bonds = new ArrayList<>();
                            i++;
                            while(!lines.get(i).equals("</Molecule>")) {
                                if(lines.get(i).startsWith("<ID>")) {
                                    id = lines.get(i).substring(4, lines.get(i).length()-5);

                                }else if(lines.get(i).startsWith("<BondStrength>")) {
                                    bond = Integer.parseInt(lines.get(i).substring(14, lines.get(i).length()-15));

                                }else if(lines.get(i).equals("<Bonds>")) {
                                    i++;
                                    while(!lines.get(i).equals("</Bonds>")) {
                                        bonds.add(lines.get(i).substring(12, lines.get(i).length()-13));
                                        i++;
                                    }
                                }
                                i++;
                            }
                            Molecule molecule = new Molecule(id, bond, bonds);
                            molecules.add(molecule);
                        }
                    }
                    molecularDataHuman = new MolecularData(molecules);

                }else if(lines.get(i).equals("<VitalesMolecularData>")) {
                    List<Molecule> molecules = new ArrayList<>();
                    while(!lines.get(i).equals("</VitalesMolecularData>")) {
                        i++;
                        if(lines.get(i).equals("<Molecule>")) {
                            String id = "";
                            int bond = 0;
                            List<String> bonds = new ArrayList<>();
                            i++;
                            while(!lines.get(i).equals("</Molecule>")) {
                                if(lines.get(i).startsWith("<ID>")) {
                                    id = lines.get(i).substring(4, lines.get(i).length()-5);

                                }else if(lines.get(i).startsWith("<BondStrength>")) {
                                    bond = Integer.parseInt(lines.get(i).substring(14, lines.get(i).length()-15));

                                }else if(lines.get(i).equals("<Bonds>")) {
                                    i++;
                                    while(!lines.get(i).equals("</Bonds>")) {
                                        bonds.add(lines.get(i).substring(12, lines.get(i).length()-13));
                                        i++;
                                    }
                                }
                                i++;
                            }
                            Molecule molecule = new Molecule(id, bond, bonds);
                            molecules.add(molecule);
                        }
                    }
                    molecularDataVitales = new MolecularData(molecules);
                }
                i++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
