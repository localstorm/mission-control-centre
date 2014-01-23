package org.localstorm.mcc.ejb.cashflow.impl;

import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
public class AssetManagerBean implements AssetManagerLocal
{

    public AssetManagerBean() {

    }

    @Override
    public void create(Asset newAsset, Cost assetCost) {
        ValuableObject vo = newAsset.getValuable();
        em.persist(vo);
        em.persist(newAsset);

        assetCost.setValuable(vo);
        em.persist(assetCost);

        Cost fake = new Cost(vo);
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(assetCost.getActuationDate());
            cal.add(Calendar.DATE, -1);

            fake.setActuationDate(cal.getTime());
            fake.setBuy(assetCost.getBuy());

            fake.setSell(BigDecimal.ZERO);
        }
        em.persist(fake);
    }

    @Override
    public void update(Asset asset) {
        em.merge(asset);
    }

    @Override
    public void remove(Asset asset) {
        asset = em.getReference(Asset.class, asset.getId());
        ValuableObject vo = asset.getValuable();
        em.remove(asset);
        em.remove(vo);
    }


    @Override
    public Collection<Asset> getAssets(User user) {
        Query uq = em.createNamedQuery(Asset.Queries.FIND_BY_OWNER);
        uq.setParameter(Asset.Properties.OWNER, user);

        return (List<Asset>) uq.getResultList();
    }

    @Override
    public Collection<Asset> getArchivedAssets(User user) {
        Query uq = em.createNamedQuery(Asset.Queries.FIND_ARCHIVED_BY_OWNER);
        uq.setParameter(Asset.Properties.OWNER, user);

        return (List<Asset>) uq.getResultList();
    }

    @Override
    public Asset find(ValuableObject vo) throws ObjectNotFoundException {
        Query uq = em.createNamedQuery(Asset.Queries.FIND_BY_VALUABLE);
        uq.setParameter(Asset.Properties.VALUABLE, vo);

        Asset a = (Asset) uq.getSingleResult();

        if (a == null) {
            throw new ObjectNotFoundException();
        }

        return a;
    }

    @Override
    public Asset find(int assetId) throws ObjectNotFoundException {
        Asset a = em.find(Asset.class, assetId);

        if (a == null) {
            throw new ObjectNotFoundException();
        }

        return a;
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
