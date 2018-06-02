package ferromera.mutantdetector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class StatsDTO {
    
    @JsonProperty("count_mutant_dna")
    private Long countMutant;
    @JsonProperty("count_human_dna")
    private Long countHuman;
    private BigDecimal ratio;
    
    public Long getCountMutant() {
        return countMutant;
    }
    
    public void setCountMutant(Long countMutant) {
        this.countMutant = countMutant;
    }
    
    public Long getCountHuman() {
        return countHuman;
    }
    
    public void setCountHuman(Long countHuman) {
        this.countHuman = countHuman;
    }
    
    public BigDecimal getRatio() {
        return ratio;
    }
    
    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }
}
