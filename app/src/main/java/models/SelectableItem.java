package models;

/**
 * Created by lenovo on 7/28/2018.
 */

public class SelectableItem extends ConsumerListData{
    private boolean isSelected = false;


    public SelectableItem(ConsumerListData item,boolean isSelected) {
          super(item.getId(),item.getService_name(),item.getCustomer_name(),item.getEstimate_time(),item.getStatus(),item.getAppointment_date(),
                item.getToken_id(),item.getMessage(),item.getBook_position(),item.getSub_date(),item.getService_id(),item.getAtpremise_all());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}