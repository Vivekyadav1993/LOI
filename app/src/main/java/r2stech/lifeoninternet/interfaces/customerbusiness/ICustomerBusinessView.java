package r2stech.lifeoninternet.interfaces.customerbusiness;

import models.businesslist.BusinessListModel;

/**
 * Created by lenovo on 3/26/2018.
 */

public interface ICustomerBusinessView {


    void onCustomerBusinessSuccess(int pid, BusinessListModel businessListModel);

    void onRecruiterMasterdataError(int pid, String errorData);

}
