package frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    }


    @OnClick({R.id.frag_join_queue_button, R.id.frag_join_queue_no_waiting_button})
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
        }
    }


}
