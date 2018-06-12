package adapters;

/**
 * Created by Vivek on 06-07-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FaqChildAdapter extends RecyclerView.Adapter<FaqChildAdapter.DataObjectHolder> {

    private final Context context;
    private final List<String> faqQuestionList;


    public FaqChildAdapter(Context context, List<String> faqQuestion) {
        this.context = context;
        this.faqQuestionList = faqQuestion;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_list_faq_act, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            holder.mQuestionTv.setText(faqQuestionList.get(position));
            holder.mSerialNoTv.setText((position+1)+".");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return faqQuestionList.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.child_question_tv)
        protected TextView mQuestionTv;

        @BindView(R.id.staff_serial_no_tv)
        public TextView mSerialNoTv;


        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}