package ferromera.mutantdetector.model;

import java.io.Serializable;

public class DnaMessage implements Serializable {
    
    private String[] dna;
    private boolean mutant;
    
    public DnaMessage() {
    }
    
    public DnaMessage(String[] dna, boolean isMutant) {
        this.dna = dna;
        this.mutant = isMutant;
    }
    
    public String[] getDna() {
        return dna;
    }
    
    public void setDna(String[] dna) {
        this.dna = dna;
    }
    
    public boolean isMutant() {
        return mutant;
    }
    
    public void setMutant(boolean mutant) {
        this.mutant = mutant;
    }
}
