import java.util.*;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();

        List<Molecule> molecules = new ArrayList<>();
        for(int i = 0; i < humanStructures.size(); i++) {
            Molecule minMol = humanStructures.get(i).getMolecules().get(0);
            for(int j = 1; j < humanStructures.get(i).getMolecules().size(); j++) {
                if(humanStructures.get(i).getMolecules().get(j).getBondStrength() < minMol.getBondStrength()) {
                    minMol = humanStructures.get(i).getMolecules().get(j);
                }
            }
            molecules.add(minMol);
        }
        System.out.println("Typical human molecules selected for synthesis: " + molecules);

        List<Molecule> temp = new ArrayList<>();
        for(int i = 0; i < diffStructures.size(); i++) {
            Molecule minMol = diffStructures.get(i).getMolecules().get(0);
            for(int j = 1; j < diffStructures.get(i).getMolecules().size(); j++) {
                if(diffStructures.get(i).getMolecules().get(j).getBondStrength() < minMol.getBondStrength()) {
                    minMol = diffStructures.get(i).getMolecules().get(j);
                }
            }
            molecules.add(minMol);
            temp.add(minMol);
        }
        System.out.println("Vitales molecules selected for synthesis: " + temp);
        System.out.println("Synthesizing the serum...");

        List<Bond> allBonds = new ArrayList<>();
        for(int i = 0; i < molecules.size(); i++) {
            for(int j = 0; j < molecules.size(); j++) {
                if(i != j) {
                    double weight =  ((double) molecules.get(i).getBondStrength() + (double) molecules.get(j).getBondStrength())/2;
                    Bond bond = new Bond(molecules.get(j), molecules.get(i), weight);
                    allBonds.add(bond);
                }
            }
        }

        allBonds.sort(Comparator.comparingDouble(Bond::getWeight));

        List<Molecule> visited = new ArrayList<>();
        dfs(allBonds, serum, visited);

        return serum;
    }

    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {

        double total = 0;
        for(int i = 0; i < serum.size(); i++) {
            Integer from = Integer.parseInt(serum.get(i).getFrom().getId().substring(1));
            Integer to = Integer.parseInt(serum.get(i).getTo().getId().substring(1));
            if(from <= to) {
                System.out.println("Forming a bond between " + serum.get(i).getFrom() + " - " + serum.get(i).getTo() + " with strength " + String.format( "%.2f", serum.get(i).getWeight()));
            } else {
                System.out.println("Forming a bond between " + serum.get(i).getTo() + " - " + serum.get(i).getFrom() + " with strength " + String.format( "%.2f", serum.get(i).getWeight()));
            }
            total += serum.get(i).getWeight();
        }
        System.out.println("The total serum bond strength is " + String.format( "%.2f", total));

    }

    private void dfs(List<Bond> bonds, List<Bond> added, List<Molecule> visited) {
        for(int i = 0; i < bonds.size(); i++) {
            if(visited.contains(bonds.get(i).getFrom()) && visited.contains(bonds.get(i).getTo())) {
                continue;
            }
            added.add(bonds.get(i));
            visited.add(bonds.get(i).getFrom());
            visited.add(bonds.get(i).getTo());
        }
    }
}
