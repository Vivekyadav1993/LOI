package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import frags.MyAdsFrag;
import models.StaffListData;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.interfaces.UpdateListData;
import r2stech.lifeoninternet.utils.Utils;

import static frags.MyAdsFrag.address_id;
import static frags.MyAdsFrag.business_id;

/**
 * Created by ROINET on 2/21/2018.
 */

public class LiveAdap extends BaseAdapter {
    private Activity activity;

    private ArrayList<StaffListData> data = new ArrayList<>();
    private StaffListData set;
    private UpdateListData updateListData;
    Holder holder;

    public LiveAdap(Activity act, ArrayList<StaffListData> _data, UpdateListData _updateListData) {
        activity = act;
        data = _data;
        updateListData = _updateListData;
        Collections.sort(data);
    }

    static class Holder {

        TextView staff_name, service_name, serve_customer_name, customer_est, start_service;
        Button serve_btn, unhold_btn;
        Spinner hold_spinner;
        RelativeLayout mStartService, mStartServiceDetails;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            view = inflater.inflate(R.layout.staff_list_item_screen, parent, false);
            holder = new Holder();
            holder.staff_name = (TextView) view.findViewById(R.id.staff_name);
            holder.service_name = (TextView) view.findViewById(R.id.service_name);
            holder.serve_customer_name = (TextView) view.findViewById(R.id.serving_customer_name);
            holder.customer_est = (TextView) view.findViewById(R.id.serving_customer_eft);
            holder.serve_btn = (Button) view.findViewById(R.id.serve_btn);
            holder.unhold_btn = (Button) view.findViewById(R.id.unhold_btn);
            holder.hold_spinner = (Spinner) view.findViewById(R.id.hold_spinner);
            holder.start_service = (TextView) view.findViewById(R.id.ads_start_staffwise_service_tv);
            holder.mStartService = (RelativeLayout) view.findViewById(R.id.staff_list_start_service_rl);
            holder.mStartServiceDetails = (RelativeLayout) view.findViewById(R.id.staff_list_full_details_rl);
            holder.serve_btn.setTag(position);
            holder.unhold_btn.setTag(position);
            holder.hold_spinner.setTag(position);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        Log.d("LAAA", "size" + data.get(position).getCustomer_name());

        set = data.get(position);
        holder.staff_name.setText(data.get(position).getStaff_name());
        final Calendar calendar = Calendar.getInstance();

        try {
            if (data.get(position).getStaff_service_started().equalsIgnoreCase("Yes")) {

                holder.mStartServiceDetails.setVisibility(View.VISIBLE);
                holder.mStartService.setVisibility(View.GONE);
                holder.start_service.setVisibility(View.GONE);
                if (data.get(position).getService_name().equals("")) {

                    // holder.absent_btn.setVisibility(View.GONE);
                    holder.service_name.setVisibility(View.INVISIBLE);
                    holder.serve_customer_name.setVisibility(View.INVISIBLE);
                    holder.customer_est.setVisibility(View.INVISIBLE);
                    if (data.get(position).getStaff_status().equalsIgnoreCase("Hold")) {
                        holder.hold_spinner.setVisibility(View.GONE);
                        holder.unhold_btn.setVisibility(View.VISIBLE);
                        holder.serve_btn.setVisibility(View.VISIBLE);
                        holder.serve_btn.setClickable(false);
                        holder.serve_btn.setEnabled(false);
                        holder.serve_btn.setText("No customer found!");
                    } else {
                        holder.hold_spinner.setVisibility(View.GONE);
                        holder.unhold_btn.setVisibility(View.GONE);
                        holder.serve_btn.setVisibility(View.VISIBLE);
                        holder.serve_btn.setClickable(false);
                        holder.serve_btn.setEnabled(false);
                        holder.serve_btn.setText("No customer found!");

                    }


                } else {
                    Log.e("status", data.get(position).getStatus());
                    holder.serve_customer_name.setVisibility(View.VISIBLE);
                    holder.serve_btn.setEnabled(true);
                    //   holder.customer_est.setVisibility(View.VISIBLE);
                    holder.serve_btn.setClickable(true);
                    holder.service_name.setText("Service :" + data.get(position).getService_name());
                    holder.customer_est.setText("Estimate Time : " + data.get(position).getEstimate_time());

                    if (data.get(position).getStatus().contains("In")) {
                        holder.serve_customer_name.setText("Will serve :" + data.get(position).getCustomer_name());
                        holder.serve_customer_name.setText("Serving : " + data.get(position).getCustomer_name());
                        holder.serve_btn.setText("FINISH");
                        holder.serve_btn.setVisibility(View.VISIBLE);
                        holder.hold_spinner.setVisibility(View.GONE);
                        holder.unhold_btn.setVisibility(View.GONE);


                    } else if (data.get(position).getStatus().equals("At Permise")) {

                        holder.serve_customer_name.setText("Will serve :" + data.get(position).getCustomer_name());
                        if (set.getStaff_status().equalsIgnoreCase("Hold")) {
                            holder.hold_spinner.setVisibility(View.GONE);
                            holder.unhold_btn.setVisibility(View.VISIBLE);
                            holder.serve_btn.setVisibility(View.INVISIBLE);
                        } else {
                            holder.hold_spinner.setVisibility(View.VISIBLE);
                            holder.unhold_btn.setVisibility(View.GONE);
                            holder.serve_btn.setText("Serve");
                            holder.serve_btn.setVisibility(View.VISIBLE);
                        }

                      /*  holder.hold_spinner.setVisibility(View.VISIBLE);
                        holder.unhold_btn.setVisibility(View.GONE);
*/

                    } else if (data.get(position).getStatus().equals("Active")) {
                        holder.service_name.setVisibility(View.VISIBLE);
                        holder.serve_customer_name.setVisibility(View.INVISIBLE);
                        holder.customer_est.setVisibility(View.VISIBLE);
                        holder.serve_btn.setClickable(false);
                        holder.serve_btn.setEnabled(false);

                        if (set.getStaff_status().equalsIgnoreCase("Hold")) {
                            holder.unhold_btn.setVisibility(View.VISIBLE);
                            holder.serve_btn.setVisibility(View.INVISIBLE);
                            holder.hold_spinner.setVisibility(View.INVISIBLE);
                        } else {
                            holder.unhold_btn.setVisibility(View.GONE);
                            holder.serve_btn.setText("Serve");
                            holder.serve_btn.setVisibility(View.VISIBLE);
                            holder.hold_spinner.setVisibility(View.VISIBLE);

                        }

                    }
                }

            } else {

                holder.mStartServiceDetails.setVisibility(View.GONE);
                holder.mStartService.setVisibility(View.VISIBLE);
                holder.start_service.setVisibility(View.VISIBLE);

            }


            try {
                holder.start_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        updateListData.doUpdate("http://lifeoninternet.com/"+ Utils.stringBuilder()+"/api.php?action=staffserviceStarted&business_id=" + business_id + "&address_id=" + address_id +
                                "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date="
                                + GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)));

                        Log.d("LA", "staff_id" + data.get(position).getStaff_id());


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            holder.unhold_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  /*  if(data.get(position).getAppointment_date()!=null) {
                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusUnHold&staff_id=" + data.get((int) v.getTag()).getStaff_id() + "&hold_id=" + data.get(position).getHold_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&appointment_date=" + data.get(position).getAppointment_date());
                    }else {
                    */    updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusUnHold&staff_id=" +
                                data.get((int) v.getTag()).getStaff_id() + "&hold_id=" + data.get(position).getHold_id() + "&business_id=" +
                                business_id + "&address_id=" + address_id + "&appointment_date=" +
                                GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)));

                    Log.d("Date",""+GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)));
                  //  }
                }
            });

            holder.serve_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (data.get(position).getStatus().equals("At Permise")) {
                        //      holder.hold_spinner.setVisibility(View.INVISIBLE);
                        updateListData.doUpdate("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updateUserStatusIn&id=" + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date());

                    } else {
                        //   holder.hold_spinner.setVisibility(View.VISIBLE);
                        updateListData.doUpdate("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updateUserStatusOut&id=" +
                                data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" +
                                data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date());

                    }
                }
            });
            holder.hold_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                    switch (p) {
                        case 0:

                            break;

                        case 1:
                            Log.d("Ta", "5 min" + data.get(position).getStaff_id());
                            // 5 min
                            updateListData.doUpdate("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updateStaffStatusHold&id=" + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() + "&hold_time_interval=5");

                            break;

                        case 2:
                            //10 min
                            Log.d("Ta", "10 min" + data.get(position).getStaff_id());
                            updateListData.doUpdate("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updateStaffStatusHold&id=" + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() + "&hold_time_interval=10");

                            break;

                        case 3:
                            //15 min
                            updateListData.doUpdate("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updateStaffStatusHold&id=" + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() + "&hold_time_interval=15");

                            break;

                        case 4:
                            //20 min
                            updateListData.doUpdate("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updateStaffStatusHold&id=" + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() + "&hold_time_interval=20");

                            break;

                        case 5:
                            //25 min
                            updateListData.doUpdate("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updateStaffStatusHold&id=" + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() + "&hold_time_interval=25");

                            break;

                        case 6:
                            //30 min
                            updateListData.doUpdate("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updateStaffStatusHold&id=" + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() + "&hold_time_interval=30");

                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;


    }

    public static String GetDateFormat(int Year, int Month, int Date) {
        try {
            return (new StringBuilder()
                    .append(Year).append("-")
                    .append((Month)).append("-")
                    .append((Date <= 9 ? "0" : "")).append(Date)).toString();
        } catch (Exception e) {
            return "";
        }
    }


}

