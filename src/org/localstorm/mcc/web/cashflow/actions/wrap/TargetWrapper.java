package org.localstorm.mcc.web.cashflow.actions.wrap;

import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.entity.Target;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;

import java.util.Comparator;

/**
 * @author localstorm
 */
public class TargetWrapper extends Target {

    private Target target;
    private Cost cost;
    private static final long serialVersionUID = 2612970717794392383L;

    public TargetWrapper(Target target,
                         Cost cost) {
        if (cost == null) {
            throw new NullPointerException("Cost is null!");
        }

        this.target = target;
        this.cost = cost;
    }

    @Override
    public Integer getId() {
        return target.getId();
    }

    @Override
    public String getName() {
        return target.getName();
    }

    @Override
    public ValuableObject getValuable() {
        return target.getValuable();
    }

    @Override
    public void setName(String name) {
        target.setName(name);
    }

    @Override
    public void setValuable(ValuableObject valuable) {
        target.setValuable(valuable);
    }

    @Override
    public boolean isArchived() {
        return target.isArchived();
    }

    @Override
    public void setArchived(boolean archived) {
        this.target.setArchived(archived);
    }

    public Cost getCurrentCost() {
        return this.cost;
    }

    public static Comparator<Target> getCurrentCostComparator() {
        return new Comparator<Target>() {
            @Override
            public int compare(Target o1, Target o2) {
                TargetWrapper tw1 = (TargetWrapper) o1;
                TargetWrapper tw2 = (TargetWrapper) o2;
                return tw1.getCurrentCost().getBuy().compareTo(tw2.getCurrentCost().getBuy());
            }
        };
    }
}
