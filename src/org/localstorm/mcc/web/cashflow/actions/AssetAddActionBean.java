package org.localstorm.mcc.web.cashflow.actions;

import org.localstorm.mcc.web.util.RoundUtil;
import java.math.BigDecimal;
import java.math.MathContext;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.MoneyMathContext;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import org.localstorm.mcc.ejb.users.*;
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @secure-by session (no security check)
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/cash/nil/AddAsset")
public class AssetAddActionBean extends AssetsEditActionBean {

    @Validate( required=true )
    private String name;

    @Validate( required=false )
    private String assetClass;

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() ) {
            super.filling();
        }
    }

    @Validate( required=true, minvalue=-9999999999L, maxvalue=9999999999L )
    private BigDecimal buy;

    @Validate( required=true, minvalue=-9999999999L, maxvalue=9999999999L )
    private BigDecimal sell;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    @DefaultHandler
    @Logged
    public Resolution addContext() {
        
        try {
            User user = super.getUser();
            
            Asset asset = new Asset();
            ValuableObject vo = new ValuableObject(user);

            asset.setName(name);
            asset.setAssetClass(assetClass);
            asset.setValuable(vo);
            
            MathContext rounding = MoneyMathContext.ROUNDING;
            
            Cost cost = new Cost(vo);
            cost.setBuy(RoundUtil.round(this.getBuy(), rounding));
            cost.setSell(RoundUtil.round(this.getSell(), rounding));

            super.getAssetManager().create(asset, cost);
            
            SessionUtil.clear(super.getSession(), CashflowSessionKeys.ASSETS);
            
        } catch(Exception e) 
        {
            e.printStackTrace();
        }
        
        return new RedirectResolution( AssetsEditActionBean.class );
    }
    
}