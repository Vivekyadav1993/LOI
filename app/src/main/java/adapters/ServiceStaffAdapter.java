package adapters;

/**
 * Created by Vivek on 06-07-2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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

public class ServiceStaffAdapter extends RecyclerView.Adapter<ServiceStaffAdapter.DataObjectHolder> {

    private Context context;
    private List<String> list;
    private LinearLayoutManager linearLayoutManager;
    private FaqChildAdapter mChildAdapter;
    private EditDetailsViewAdapter mClick;
    private int row_index = -1;

    public interface EditDetailsViewAdapter {
        void onClick(int pos);
    }

    public ServiceStaffAdapter(Context context/*, List<String> faqList*/, List<String> list, EditDetailsViewAdapter mClick) {
        this.context = context;
        this.list = list;
        this.mClick = mClick;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_faq, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {

            if (row_index == position) {

                Log.d("SSA", "row_index" + row_index + "position" + position);
                holder.mCv.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                holder.mTitleTV.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                holder.mCv.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.mTitleTV.setTextColor(context.getResources().getColor(R.color.black));

                Log.d("SSA", "row_index_2" + row_index);
            }

            holder.mTitleTV.setText(list.get(position).toString());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClick.onClick(position);
                    Log.d("SSA", "row_index3" + row_index);

                    // mClick.onClick( position);
                    row_index = position;
                    notifyDataSetChanged();
                }
            });
            //  List<String> childList=findWithQuery("select * from faq_question where parent_id='"+position+"'");
          /*  holder.mChildRecyclerView.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            holder.mChildRecyclerView.setLayoutManager(linearLayoutManager);
            mChildAdapter = new FaqChildAdapter(context, faqQuestionList,position);
            holder.mChildRecyclerView.setAdapter(mChildAdapter);
*/
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.faq_act_title_tv)
        protected TextView mTitleTV;

        @BindView(R.id.parent_cv)
        public LinearLayout mCv;

        public DataObjectHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            ButterKnife.bind(this, itemView);
        }
    }
}