package frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import adapters.CustomerLandingRecyclerViewAdapter;
import adapters.MyHsitoryAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import r2stech.lifeoninternet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHistoryFrag extends Fragment {

    private MyHsitoryAdapter mMyHsitoryAdapter;

    @BindView(R.id.my_history_frag_recycler_view)
    public RecyclerView mRecyclerView;


    private ArrayList<String> list;

    public MyHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_history, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {
        list=new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mMyHsitoryAdapter = new MyHsitoryAdapter(getActivity(), list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mMyHsitoryAdapter);
    }
   /* @OnClick({R.id.my_history_back_btn})
    public void onClick(View v) {
        switch (v.getId()) {
              case R.id.my_history_back_btn:
                getActivity().onBackPressed();
                break;

        }
    }*/
}
