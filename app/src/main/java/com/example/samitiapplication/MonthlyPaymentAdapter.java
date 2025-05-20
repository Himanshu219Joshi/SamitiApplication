package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samitiapplication.data.DatabaseHelper;
import com.example.samitiapplication.data.model.UserInfo;
import com.example.samitiapplication.modal.MemberDetail;

import java.util.List;
import java.util.Objects;

public class MonthlyPaymentAdapter extends RecyclerView.Adapter<MonthlyPaymentAdapter.ViewHolder> {

    private final List<MemberDetail> memberDetails;

    buttonClickListener buttonClickListener;
    DatabaseHelper db;
    UserInfo userInfo;

//    DatabaseHelper db;

    public MonthlyPaymentAdapter(Context context, List<MemberDetail> items, buttonClickListener buttonClickListener, UserInfo userInfo) {
        this.memberDetails = items;
        this.context = context;
        this.buttonClickListener = buttonClickListener;
        this.userInfo = userInfo;
    }

    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);;

        return new ViewHolder(view, buttonClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = memberDetails.get(position);
        MemberDetail data = memberDetails.get(position);
        holder.memberId.setText(data.getMemberId());
        holder.memberName.setText(data.getMemberName().concat(" ").concat(data.getFatherName()));
        this.userInfo.setMemeberId(Integer.parseInt(data.getMemberId()));
        this.userInfo.setMemberName(data.getMemberName());
//        String text = userInfo.getInstallmentStatus();
        if (data.isSelected()) {
            String text = userInfo.getInstallmentStatus();
            if(text.equals("Paid")) {
                holder.paidTickBtn.setVisibility(View.VISIBLE);
                holder.notPaidBtn.setVisibility(View.GONE);
                holder.paidBtn.setVisibility(View.GONE);
            } else if(text.equals("Not Paid")) {
                holder.notPaidCrossBtn.setVisibility(View.VISIBLE);
                holder.notPaidBtn.setVisibility(View.GONE);
                holder.paidBtn.setVisibility(View.GONE);
            }
        } else {
            holder.paidBtn.setVisibility(View.VISIBLE);
            holder.notPaidBtn.setVisibility(View.VISIBLE);
//            notifyItemChanged(position);
        }


//
        holder.paidBtn.setOnClickListener(view-> {
            data.setSelected(true);
            this.buttonClickListener.onButtonClick(view, position, "Paid");
            notifyItemChanged(position);
        });

        holder.notPaidBtn.setOnClickListener(view-> {
            data.setSelected(true);
            this.buttonClickListener.onButtonClick(view, position, "Not Paid");
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return memberDetails.size();
    }

    public List<MemberDetail> getMemberDetails() {
        return this.memberDetails;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView memberId;
        public final TextView memberName;
        public MemberDetail mItem;

        UserInfo userInfo;
        buttonClickListener buttonClickListener;;
        public Button paidBtn, notPaidBtn;
        public ImageView paidTickBtn, notPaidCrossBtn;
//        DatabaseHelper db = new DatabaseHelper();

        public ViewHolder(View itemView, buttonClickListener itemClickListener) {
            super(itemView);;
            memberId = itemView.findViewById(R.id.memberId);
            memberName = itemView.findViewById(R.id.memberName);
//            paidBtn = itemView.findViewById(R.id.paidBtn);
//            notPaidBtn = itemView.findViewById(R.id.notPaidBtn);
            paidTickBtn = itemView.findViewById(R.id.paidTick);
            notPaidCrossBtn = itemView.findViewById(R.id.notPaidCross);

//            paidBtn.setOnClickListener(this);
//            notPaidBtn.setOnClickListener(this);
            this.buttonClickListener = itemClickListener;

        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + memberName.getText() + " ";
        }

        @Override
        public void onClick(View v) {
            String textView = String.valueOf(v.getId());
            Button b = (Button) v;
            String text = b.getText().toString();
            Toast.makeText(b.getContext(),"Text Clicked", Toast.LENGTH_SHORT).show();
            if(text.equals("Paid")) {
                itemView.findViewById(R.id.paidTick).setVisibility(View.VISIBLE);
//                itemView.findViewById(R.id.paidBtn).setVisibility(View.GONE);
//                itemView.findViewById(R.id.notPaidBtn).setVisibility(View.GONE);
                buttonClickListener.onButtonClick(v, getAbsoluteAdapterPosition(), "PAID");
            } else if(text.equals("Not Paid")) {
//                itemView.findViewById(R.id.paidBtn).setVisibility(View.GONE);
//                itemView.findViewById(R.id.notPaidBtn).setVisibility(View.GONE);
                itemView.findViewById(R.id.notPaidCross).setVisibility(View.VISIBLE);
                buttonClickListener.onButtonClick(v, getAbsoluteAdapterPosition(), "NOT PAID");
            }

        }
    }

    public interface buttonClickListener {
        void onButtonClick(View view,int position, String text);
    }

}