package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.dashboard.actions.DashboardActionBean;

/**
 *
 * @author Alexey Kuznetsov
 */
public class NextDestinationUtil 
{

    public static RedirectResolution getRedirection(ReturnPageBean rpb) {
        if (rpb==null) {
            return NextDestinationUtil.getDefaultRedirection();
        }

        Pages rp = Pages.valueOf(rpb.getPageToken());

        RedirectResolution rr;
        switch(rp)
        {
            case LIST_VIEW:
                rr = new RedirectResolution(ListViewActionBean.class);
                break;
            case TASK_SEARCH_SUBMIT:
                rr = new RedirectResolution(TaskSearchSubmitActionBean.class);
                break;
            case BMS_VIEW:
                rr = new RedirectResolution(BattleMapSupportActionBean.class);
                break;
            case DL_REPORT:
                rr = new RedirectResolution(DeadlineReportActionBean.class);
                break;
            case OLD_REPORT:
                rr = new RedirectResolution(OldTasksReportActionBean.class);
                break;
            case DASH:
                rr = new RedirectResolution(DashboardActionBean.class);
                break;
            case FPV:
                rr = new RedirectResolution(FlightPlanViewActionBean.class);
                break;
            default:
                rr = NextDestinationUtil.getDefaultRedirection();
                break;
        }

        rpb.appendParams(rr);
        return rr;
    }

    public static RedirectResolution getDefaultRedirection() {
          return new RedirectResolution(FlightPlanViewActionBean.class);
    }
}
