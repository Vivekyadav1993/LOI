package r2stech.lifeoninternet.interfaces;

import models.addetailsEdit.DetailsEditModel;

/**
 * Created by Vivek on 3/26/2018.
 */

public interface IAddDetailsView {

    void addDetailsSuccess(int pid, DetailsEditModel detailsEditModel);

    void addDetailsError(int pid, String errorData);


}
