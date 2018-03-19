package frags;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wefika.calendar.CollapseCalendarView;
import com.wefika.calendar.manager.CalendarManager;

import org.joda.time.LocalDate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
import r2stech.lifeoninternet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinQueueFrag extends HelperFrags {


    @BindView(R.id.frag_join_queue_button)
    public Button mJoinQueueButton;

    @BindView(R.id.frag_toolbar_title_tv)
    public TextView mHeaderTitle;

    @BindView(R.id.frag_join_queue_calendar_view)
    public CollapseCalendarView mCalendarView;

    @BindView(R.id.frag_join_queue_waiting_rl)
    public RelativeLayout mWaitingRl;

    @BindView(R.id.frag_join_queue_no_waiting_rl)
    public RelativeLayout mNoWaitingRl;

    @BindView(R.id.frag_join_queue_waiting_time_tv)
    public TextView mJoinWaitingTime;

    @BindView(R.id.frag_join_queue_pre_book_amount_tv)
    public TextView mPreBookAmount;

    @BindView(R.id.frag_join_queue_float_btn)
    public FloatingActionButton mFloatBtn;
    public JoinQueueFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_queue, container, false);

        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {

      /*  CalendarManager manager = new CalendarManager(LocalDate.now(),
                CalendarManager.State.MONTH, LocalDate.now(), LocalDate.now().plusYears(1));
        mCalendarView.init(manager);*/
    }

    @OnClick({R.id.frag_join_queue_button, R.id.frag_join_queue_no_waiting_button ,R.id.join_queue_back_btn ,
            R.id.frag_join_queue_float_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_join_queue_button:
                Bundle bundle = new Bundle();
                replaceFrag(new JoinQueueFrag(), bundle, JoinedQueueFrag.class.getName());
                break;

            case R.id.frag_join_queue_no_waiting_button:
                Bundle bundle1 = new Bundle();
                replaceFrag(new JoinQueueFrag(), bundle1, JoinedQueueFrag.class.getName());
                break;

            case R.id.join_queue_back_btn:
                getActivity().onBackPressed();
                break;
            case R.id.frag_join_queue_float_btn:
                showDetailsFloatFun();
                break;
        }
    }

    private void showDetailsFloatFun() {

    }


}
