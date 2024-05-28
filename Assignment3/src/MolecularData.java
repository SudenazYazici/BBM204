import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        ArrayList<MolecularStructure> structures = new ArrayList<>();

        for(int i = 0; i < molecules.size(); i++) {
            boolean hasMol = false;
            //boolean isStructureContains = true;
            for(int j = 0; j<structures.size(); j++) {
                if(!structures.get(j).hasMolecule(molecules.get(i).getId())) {
                    for(int k=0; k<molecules.get(i).getBonds().size(); k++) {
                        if(structures.get(j).hasMolecule(molecules.get(i).getBonds().get(k))) {
                            structures.get(j).addMolecule(molecules.get(i));
                            for(int q = 0; q < molecules.get(i).getBonds().size(); q++) { // adding all the bonds
                                for(int m = 0; m < molecules.size(); m++) {
                                    if(molecules.get(i).getBonds().get(q).equals(molecules.get(m).getId()) &&
                                            !structures.get(j).hasMolecule(molecules.get(i).getBonds().get(q))) {
                                        structures.get(j).addMolecule(molecules.get(m));
                                    }
                                }
                            }
                            hasMol = true;
                            break;
                        }
                    }
                } else {
                    for(int q = 0; q < molecules.get(i).getBonds().size(); q++) { // adding all the bonds
                        for(int m = 0; m < molecules.size(); m++) {
                            if(molecules.get(i).getBonds().get(q).equals(molecules.get(m).getId()) &&
                                    !structures.get(j).hasMolecule(molecules.get(i).getBonds().get(q))) {
                                structures.get(j).addMolecule(molecules.get(m));
                            }
                        }
                    }
                    hasMol = true;
                }
            }
            if(!hasMol) {
                MolecularStructure molecularStructure = new MolecularStructure();
                molecularStructure.addMolecule(molecules.get(i));
                for(int k = 0; k < molecules.get(i).getBonds().size(); k++) {
                    for(int m = 0; m < molecules.size(); m++) {
                        if(molecules.get(i).getBonds().get(k).equals(molecules.get(m).getId())) {
                            molecularStructure.addMolecule(molecules.get(m));
                        }
                    }
                }
                structures.add(molecularStructure);
            }
        }
        return structures;
    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {

        System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
        for(int i = 0; i<molecularStructures.size(); i++) {
            System.out.println("Molecules in Molecular Structure " +(i+1)+ ": " + molecularStructures.get(i));
        }

    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();
        
        for(int i = 0; i<targeStructures.size(); i++) {
            if(!sourceStructures.contains(targeStructures.get(i))) {
                anomalyList.add(targeStructures.get(i));
            }
        }

        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {

        System.out.println("Molecular structures unique to Vitales individuals:");
        for(int i = 0; i<molecularStructures.size(); i++) {
            System.out.println(molecularStructures.get(i));
        }
    }
}
