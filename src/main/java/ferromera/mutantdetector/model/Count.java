package ferromera.mutantdetector.model;

public class Count {
    
    private boolean isCalled;
    private Long lastValue;
    
    public boolean isCalled() {
        return isCalled;
    }
    
    public void setCalled(boolean called) {
        isCalled = called;
    }
    
    public Long getLastValue() {
        return lastValue;
    }
    
    public void setLastValue(Long lastValue) {
        this.lastValue = lastValue;
    }
}
