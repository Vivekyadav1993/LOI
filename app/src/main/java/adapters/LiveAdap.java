package adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import atw.lifeoninternet.R;
import atw.lifeoninternet.interfaces.UpdateListData;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import frags.MyAdsFrag;
import models.StaffListData;

import static frags.MyAdsFrag.address_id;
import static frags.MyAdsFrag.business_id;

/**
 * Created by ROINET on 2/21/2018.
 */

public class LiveAdap extends BaseAdapter {
    private Activity activity;

    private Dialog mDialougeBox;
    private ArrayList<StaffListData> data = new ArrayList<>();
    private StaffListData set;
  //  private String skip_last;
    private UpdateListData updateListData;
    Holder holder;
    private Sharedpreferences mPref;


    public LiveAdap(Activity act,/* String skip_last, */ArrayList<StaffListData> _data, UpdateListData _updateListData) {
        activity = act;
        data = _data;
      //  this.skip_last = skip_last;
        updateListData = _updateListData;
        mPref = Sharedpreferences.getUserDataObj(act);
        // Collections.sort(data);
    }

    static class Holder {

        TextView staff_name, service_name, serve_customer_name, customer_est, start_service, staff_name_for_start;
        Button serve_btn, unhold_btn, absent, skip;
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
            holder.staff_name_for_start = (TextView) view.findViewById(R.id.myads_staff_name_tv);
            holder.service_name = (TextView) view.findViewById(R.id.service_name);
            holder.serve_customer_name = (TextView) view.findViewById(R.id.serving_customer_name);
            holder.customer_est = (TextView) view.findViewById(R.id.serving_customer_eft);
            holder.serve_btn = (Button) view.findViewById(R.id.serve_btn);
            holder.unhold_btn = (Button) view.findViewById(R.id.unhold_btn);
            holder.absent = (Button) view.findViewById(R.id.staff_absent_btn);
            holder.skip = (Button) view.findViewById(R.id.skip_btn);
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

        set = data.get(position);
        String text = "<font color=#000000>" + data.get(position).getStaff_name() + "</font> | <font color=#343851>" + data.get(position).getService_name()
                + "</font> | <font color=#2B71FF><b>" + data.get(position).getCustomer_name() + "</b></font>";

        holder.staff_name.setText((Html.fromHtml(text)));
        holder.staff_name_for_start.setText(data.get(position).getStaff_name());
        final Calendar calendar = Calendar.getInstance();

        try {
            if (data.get(position).getStaff_service_started().equalsIgnoreCase("Yes")) {

                holder.mStartServiceDetails.setVisibility(View.VISIBLE);
                holder.mStartService.setVisibility(View.GONE);
                holder.start_service.setVisibility(View.GONE);

                if (data.get(position).getService_name().equals("")) {

                    // holder.absent_btn.setVisibility(View.GONE);
                    holder.service_name.setVisibility(View.GONE);
                    holder.serve_customer_name.setVisibility(View.GONE);
                    holder.customer_est.setVisibility(View.GONE);
                    holder.skip.setVisibility(View.GONE);
                    Log.d("LAA", "hold" + data.get(position).getStaff_status());
                    if (data.get(position).getStaff_status().equalsIgnoreCase("Hold")) {
                        holder.hold_spinner.setVisibility(View.GONE);
                        holder.unhold_btn.setVisibility(View.VISIBLE);
                        holder.serve_btn.setVisibility(View.INVISIBLE);
                        holder.serve_btn.setClickable(false);
                        holder.absent.setVisibility(View.GONE);
                        holder.serve_btn.setEnabled(false);
                        holder.serve_btn.setText("No customer found!");
                        holder.serve_btn.setTextSize(12);
                    } else {
                        holder.hold_spinner.setVisibility(View.VISIBLE);
                        holder.unhold_btn.setVisibility(View.GONE);
                        holder.serve_btn.setVisibility(View.VISIBLE);
                        holder.absent.setVisibility(View.GONE);
                        holder.serve_btn.setClickable(false);
                        holder.serve_btn.setEnabled(false);
                        holder.serve_btn.setText("No customer found!");
                        holder.serve_btn.setTextSize(12);

                    }


                } else {
                    Log.e("status", data.get(position).getStatus());
                    //   holder.serve_customer_name.setVisibility(View.VISIBLE);
                    holder.serve_btn.setEnabled(true);
                    //   holder.customer_est.setVisibility(View.VISIBLE);
                    holder.serve_btn.setClickable(true);
                    //  holder.service_name.setText(data.get(position).getService_name());
                    //  holder.service_name.setVisibility(View.VISIBLE);
                    //  holder.customer_est.setText(data.get(position).getEstimate_time());

                    if (data.get(position).getStatus().contains("In")) {
                        holder.serve_customer_name.setText(data.get(position).getCustomer_name());
                        holder.serve_customer_name.setText(data.get(position).getCustomer_name());
                        holder.serve_btn.setText("NEXT");
                        holder.skip.setVisibility(View.VISIBLE);
                        holder.customer_est.setVisibility(View.GONE);
                        holder.absent.setVisibility(View.VISIBLE);
                        holder.serve_btn.setVisibility(View.VISIBLE);
                        holder.hold_spinner.setVisibility(View.VISIBLE);
                        holder.unhold_btn.setVisibility(View.GONE);


                    } else if (data.get(position).getStatus().equals("At Permise")) {
                        //  holder.customer_est.setVisibility(View.GONE);
                        holder.absent.setVisibility(View.VISIBLE);
                        holder.skip.setVisibility(View.VISIBLE);
                        holder.serve_customer_name.setText("Will serve :" + data.get(position).getCustomer_name());
                        if (set.getStaff_status().equalsIgnoreCase("Hold")) {
                            holder.hold_spinner.setVisibility(View.GONE);
                            holder.unhold_btn.setVisibility(View.VISIBLE);
                            holder.customer_est.setVisibility(View.GONE);
                            holder.absent.setVisibility(View.GONE);
                            holder.skip.setVisibility(View.GONE);
                            holder.serve_customer_name.setVisibility(View.GONE);
                            holder.serve_btn.setVisibility(View.INVISIBLE);
                        } else {
                            holder.hold_spinner.setVisibility(View.VISIBLE);
                            holder.unhold_btn.setVisibility(View.GONE);
                            holder.serve_btn.setText("NEXT");
                            holder.skip.setVisibility(View.VISIBLE);
                            //     holder.customer_est.setVisibility(View.VISIBLE);
                            //   holder.serve_customer_name.setVisibility(View.VISIBLE);
                            holder.absent.setVisibility(View.VISIBLE);
                            holder.serve_btn.setVisibility(View.VISIBLE);
                        }

                    } else if (data.get(position).getStatus().equals("Active")) {
                        //    holder.service_name.setVisibility(View.VISIBLE);
                        //   holder.serve_customer_name.setVisibility(View.GONE);
                        //   holder.customer_est.setVisibility(View.VISIBLE);
                        holder.absent.setVisibility(View.VISIBLE);
                        holder.skip.setVisibility(View.VISIBLE);
                        holder.serve_btn.setClickable(false);
                        holder.serve_btn.setEnabled(false);
                        //   holder.customer_est.setVisibility(View.VISIBLE);
                        // holder.customer_est.setText(data.get(position).getEstimate_time());


                        if (set.getStaff_status().equalsIgnoreCase("Hold")) {
                            holder.unhold_btn.setVisibility(View.VISIBLE);
                            holder.serve_btn.setVisibility(View.GONE);
                            holder.hold_spinner.setVisibility(View.GONE);

                        } else {
                            holder.unhold_btn.setVisibility(View.GONE);
                            holder.serve_btn.setText("NEXT");
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


            if (data.get(position).getSkip_last().equalsIgnoreCase("Yes")) {
                holder.skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=pushmyselfBack&business_id=" + business_id + "&address_id="
                                + MyAdsFrag.address_id + "&id=" + data.get(position).getToken_id() + "&staff_id=" + data.get(position).getStaff_id() +
                                "&appointment_date=" + data.get(position).getAppointment_date() + "&adminlogin=yes&type=skiplast&sub_date=" +
                                data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());


                    }
                });
            } else {
                holder.skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusSkip&business_id=" + business_id + "&address_id="
                                + MyAdsFrag.address_id + "&id=" + data.get(position).getToken_id() + "&staff_id=" + data.get(position).getStaff_id() +
                                "&appointment_date=" + data.get(position).getAppointment_date() + "&adminlogin=yes&sub_date=" +
                                data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());


                    }
                });
            }

            holder.absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    mDialougeBox = new Dialog(activity);
                    mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mDialougeBox.setContentView(R.layout.item_absent_customer);
                    mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                    mDialougeBox.show();

                    TextView mDelete = (TextView) mDialougeBox.findViewById(R.id.absent_customer_tv);
                    TextView mCancleTv = (TextView) mDialougeBox.findViewById(R.id.cancel_customer_tv);

                    mDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mPref.getStaffId().equalsIgnoreCase("")) {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                        + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id="
                                        + MyAdsFrag.address_id + "&appointment_date=" + data.get(position).getAppointment_date() + "&sub_date=" +
                                        data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id() + "&absent_type=top"+
                                        "&adminlogin=yes");


                            } else {

                                Log.d("CLA", "" + data.get(position).getToken_id());
                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                        + data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id="
                                        + MyAdsFrag.address_id + "&appointment_date=" + data.get(position).getAppointment_date() +
                                        "&staff_id=" + mPref.getStaffId() + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" +
                                        data.get(position).getService_id() + "&absent_type=top"+"&adminlogin=yes");

                            }
                            mDialougeBox.hide();
                        }
                    });

                    mCancleTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            mDialougeBox.hide();

                        }
                    });
                }
            });


            try {

                holder.start_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mDialougeBox = new Dialog(activity);
                        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialougeBox.setContentView(R.layout.item_start_service);
                        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                        mDialougeBox.show();

                        TextView mStartService= (TextView) mDialougeBox.findViewById(R.id.start_service_tv);
                        TextView mCancleServiceTv = (TextView) mDialougeBox.findViewById(R.id.cancel_start_service_tv);

                        mStartService.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (mPref.getStaffAdmin().equalsIgnoreCase("Yes")) {

                                    updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=staffserviceStarted&business_id=" + business_id + "&address_id=" + address_id +
                                            "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date="
                                            + data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                            "&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                                } else {
                                    updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=staffserviceStarted&business_id=" + business_id + "&address_id=" + address_id +
                                            "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date="
                                            + data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                            "&adminlogin=no" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());
                                }
                                mDialougeBox.hide();
                            }
                        });

                        mCancleServiceTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mDialougeBox.hide();

                            }
                        });
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
                    */
                    if (mPref.getStaffAdmin().equalsIgnoreCase("Yes")) {


                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusUnHold&staff_id=" +
                                data.get((int) v.getTag()).getStaff_id() + "&hold_id=" + data.get(position).getHold_id() + "&business_id=" +
                                business_id + "&address_id=" + address_id + "&appointment_date=" +data.get(position).getAppointment_date()
                                /*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE))*/
                                + "&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                        Log.d("Date", "" + GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)));

                    } else {

                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusUnHold&staff_id=" +
                                data.get((int) v.getTag()).getStaff_id() + "&hold_id=" + data.get(position).getHold_id() + "&business_id=" +
                                business_id + "&address_id=" + address_id + "&appointment_date=" +data.get(position).getAppointment_date()
                               /* GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                "&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());


                    }
                }
            });

            holder.serve_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=nextCustomer&business_id=" +
                            business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() +
                            "&sub_date=" + data.get(position).getSub_date() + "&appointment_date=" + data.get(position).getAppointment_date()
                            + "&adminlogin=yes&service_id=" + data.get(position).getService_id() + "&id=" + data.get(position).getToken_id()
                            + "&selbook_id=" + "");



                              /*  if (data.get(position).getStatus().equals("At Permise")) {
                        //      holder.hold_spinner.setVisibility(View.INVISIBLE);
                        if (mPref.getStaffId().equalsIgnoreCase("")) {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusIn&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" +
                                        data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() +
                                        "&adminlogin=yes"+"&sub_date="+data.get(position).getSub_date()+"&service_id="+data.get(position).getService_id());

                        } else {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusIn&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" +
                                        data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() +
                                        "&adminlogin=no"+"&sub_date="+data.get(position).getSub_date()+"&service_id="+data.get(position).getService_id());


                        }

                    } else {
                        //   holder.hold_spinner.setVisibility(View.VISIBLE);
                        if (mPref.getStaffId().equalsIgnoreCase("")) {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusOut&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" +
                                        data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() +
                                        "&adminlogin=yes"+"&sub_date="+data.get(position).getSub_date()+"&service_id="+data.get(position).getService_id());

                        } else {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusOut&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" +
                                        data.get(position).getStaff_id() + "&appointment_date=" + data.get(position).getAppointment_date() +
                                        "&adminlogin=no"+"&sub_date="+data.get(position).getSub_date()+"&service_id="+data.get(position).getService_id());


                        }

                    }
*/
                }
            });
            holder.hold_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                    switch (p) {
                        case 0:

                            break;

                        case 1:
                            Log.d("Ta", "5 min" + mPref.getStaffId());
                            // 5 min

                            if (mPref.getStaffAdmin().equalsIgnoreCase("Yes")) {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" +
                                        data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=5&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            } else {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=5&adminlogin=no" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            }
                            break;

                        case 2:
                            //10 min
                            if (mPref.getStaffAdmin().equalsIgnoreCase("Yes")) {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE))*/ +
                                        "&hold_time_interval=10&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            } else {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE))*/  +
                                        "&hold_time_interval=10&adminlogin=no" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());


                            }
                            break;

                        case 3:
                            //15 min
                            if (mPref.getStaffAdmin().equalsIgnoreCase("Yes")) {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE))*/ +
                                        "&hold_time_interval=15&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            } else {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                       data.get(position).getAppointment_date()/* GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=15&adminlogin=no" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            }
                            break;

                        case 4:
                            //20 min
                            if (mPref.getStaffAdmin().equalsIgnoreCase("Yes")) {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=20&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            } else {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=20&adminlogin=no" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());


                            }
                            break;

                        case 5:
                            //25 min
                            if (mPref.getStaffAdmin().equalsIgnoreCase("Yes")) {

                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=25&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            } else {
                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=25&adminlogin=no" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            }
                            break;

                        case 6:
                            //30 min
                            if (mPref.getStaffAdmin().equalsIgnoreCase("Yes")) {
                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=30&adminlogin=yes" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());
                            } else {
                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                        data.get(position).getToken_id() + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + data.get(position).getStaff_id() + "&appointment_date=" +
                                        data.get(position).getAppointment_date()/*GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) */+
                                        "&hold_time_interval=30&adminlogin=no" + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());

                            }
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
                    .append((Month <= 9 ? "0" : "")).append(Month + "-")
                    .append((Date <= 9 ? "0" : "")).append(Date)).toString();
        } catch (Exception e) {
            return "";
        }
    }
}

