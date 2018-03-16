package frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import r2stech.lifeoninternet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedQueueFrag extends Fragment {


    public JoinedQueueFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_joined_queue, container, false);

        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {


    }

}
