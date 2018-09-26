package adapters;

/**
 * Created by Vivek on 06-07-2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinAppointmentAdapter extends RecyclerView.Adapter<JoinAppointmentAdapter.DataObjectHolder> {

    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private FaqChildAdapter mChildAdapter;


    public JoinAppointmentAdapter(Context context) {
        this.context = context;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_items, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {



          //  holder.mTitleTV.setText(list.get(position).toString());

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return 8;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

      /*  @BindView(R.id.faq_act_title_tv)
        protected TextView mTitleTV;

        @BindView(R.id.parent_cv)
        public LinearLayout mCv;
*/
        public DataObjectHolder(View itemView) {
            super(itemView);
           /* itemView.setClickable(true);*/
            ButterKnife.bind(this, itemView);
        }
    }
}