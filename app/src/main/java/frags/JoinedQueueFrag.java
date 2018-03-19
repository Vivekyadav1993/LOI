package frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import r2stech.lifeoninternet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedQueueFrag extends Fragment {


    @BindView(R.id.frag_joined_queue_position_in_queue_tv)
    public TextView mPositionInQueue;

    @BindView(R.id.frag_joined_queue_waiting_time_tv)
    public TextView mWaitingTime;

    @BindView(R.id.frag_toolbar_title_tv)
    public TextView mHeaderTitle;

    public JoinedQueueFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joined_queue, container, false);

        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {


    }

    @OnClick({R.id.frag_joined_queue_back_btn,R.id.frag_joined_queue_cancel_btn ,R.id.frag_joined_queue_push_back_btn,R.id.frag_joined_queue_show_details_btn})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.frag_joined_queue_back_btn:
                getActivity().onBackPressed();
                break;

            case R.id.frag_joined_queue_cancel_btn:
                appointmentCancleFun();
                break;
            case R.id.frag_joined_queue_push_back_btn:
                pushMySelfBackFunction();
                break;

            case R.id.frag_joined_queue_show_details_btn:
                showDetailsFun();
                break;
        }

    }

    private void showDetailsFun() {

    }

    private void pushMySelfBackFunction() {

    }

    private void appointmentCancleFun() {
    }

}
