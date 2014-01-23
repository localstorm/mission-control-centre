package org.localstorm.mcc.ejb.gtd.entity;

/**
 *
 * @author Alexey Kuznetsov
 */
public enum Effort 
{
    ELEMENTARY(1, "Elementary"),
    EASY(2, "Easy"),
    MEDIUM(3, "Medium"),
    DIFFICULT(4, "Difficult"),
    VERY_DIFFICULT(5, "Very difficult");

    private final int effort;
    private final String latinName;
            
    private Effort(int effort, String latinName) {
        this.effort = effort;
        this.latinName = latinName;
    }

    public int getEffort() {
        return effort;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getLatinName() {
        return latinName;
    }
    
    
}
