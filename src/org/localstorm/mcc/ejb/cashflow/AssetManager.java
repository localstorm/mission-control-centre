package org.localstorm.mcc.ejb.cashflow;

import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import java.util.Collection;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;


/**
 *
 * @author localstorm
 */

public interface AssetManager
{
    public static final String BEAN_NAME="AssetManagerBean";

    public Collection<Asset> getAssets(User user);

    public Collection<Asset> getArchivedAssets(User user);

    public Asset find(ValuableObject vo) throws ObjectNotFoundException;

    public Asset find(int assetId) throws ObjectNotFoundException;

    public void create(Asset newAsset, Cost assetCost);

    public void remove(Asset asset);

    public void update(Asset asset);
    
}
