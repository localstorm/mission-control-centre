package org.localstorm.mcc.web.cashflow.actions.wrap;

import java.util.ArrayList;
import java.util.Collection;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.entity.Target;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import org.localstorm.mcc.ejb.cashflow.OperationManager;

/**
 *
 * @author localstorm
 */
public class WrapUtil {

    public static Asset wrapAsset(Asset ass, 
                                  OperationManager om)
    {
        ValuableObject vo = ass.getValuable();

        return new AssetWrapper(ass, 
                                om.getTotalAmount(vo),
                                om.getCurrentCost(vo),
                                om.getInvestmentsCost(vo),
                                om.getBalance(vo),
                                om.getRevenuAmount(vo));
    }

    public static Collection<Asset> wrapAssets(Collection<Asset> assets,
                                               OperationManager om)
    {
        Collection<Asset> result = new ArrayList<Asset>(assets.size());
        for (Asset ass: assets)
        {
            result.add(wrapAsset(ass, om));
        }

        return result;
    }

     public static Target wrapTarget(Target tgt,
                                    OperationManager om)
    {
        ValuableObject vo = tgt.getValuable();
        return new TargetWrapper(tgt, om.getCurrentCost(vo));
    }

    public static Collection<Target> wrapTargets(Collection<Target> targets,
                                                 OperationManager om)
    {
        Collection<Target> result = new ArrayList<Target>(targets.size());
        for (Target tgt: targets)
        {
            result.add(wrapTarget(tgt, om));
        }

        return result;
    }
}
