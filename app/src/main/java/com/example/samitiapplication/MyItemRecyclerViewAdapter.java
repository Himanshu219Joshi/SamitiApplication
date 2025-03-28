package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samitiapplication.data.DatabaseHelper;
import com.example.samitiapplication.data.model.UserInfo;
import com.example.samitiapplication.modal.MemberDetail;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<MemberDetail> memberDetails;

    buttonClickListener buttonClickListener;
    DatabaseHelper db;
    UserInfo userInfo;

//    DatabaseHelper db;

    public MyItemRecyclerViewAdapter(Context context, List<MemberDetail> items, buttonClickListener buttonClickListener) {
        this.memberDetails = items;
        this.context = context;
        this.buttonClickListener = buttonClickListener;
    }

    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        userInfo = new UserInfo();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);;

        return new ViewHolder(view, buttonClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = memberDetails.get(position);
        holder.memberId.setText(memberDetails.get(position).getMemberId());
        holder.memberName.setText(memberDetails.get(position).getMemberName().concat(" ").concat(memberDetails.get(position).getFatherName()));
        userInfo.setMemeberId(Integer.parseInt(memberDetails.get(position).getMemberId()));
        userInfo.setMemberName(memberDetails.get(position).getMemberName());

//       holder.paidBtn.setOnClickListener(view -> {
//         userInfo.setInstallmentStatus("PAID");
//        Toast.makeText(context, "Item Clicked Paid :"+memberDetails.get(position).getMemberId()+" "+memberDetails.get(position).getMemberName(), Toast.LENGTH_SHORT).show();
//       });
//
//       holder.notPaidBtn.setOnClickListener(view -> {
//           userInfo.setInstallmentStatus("NOT PAID");
////           db.insetData(userInfo);
//           Toast.makeText(context, "Item Clicked Not Paid :"+position, Toast.LENGTH_SHORT).show();
//       });
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
//        DatabaseHelper db = new DatabaseHelper();
        public ViewHolder(View itemView, buttonClickListener itemClickListener) {
            super(itemView);;
            userInfo = new UserInfo();
            memberId = itemView.findViewById(R.id.memberId);
            memberName = itemView.findViewById(R.id.memberName);
            paidBtn = itemView.findViewById(R.id.paidBtn);
            notPaidBtn = itemView.findViewById(R.id.notPaidBtn);
            userInfo.setMemeberId(Integer.parseInt(memberId.getText().toString()));
            userInfo.setMemberName(memberName.getText().toString());

            this.buttonClickListener = itemClickListener;

            paidBtn.setOnClickListener(this);
            notPaidBtn.setOnClickListener(this);
//            itemView.setOnClickListener(this);

        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + memberName.getText() + " ";
        }

        @Override
        public void onClick(View v) {
            String textView = String.valueOf(v.getId());
            System.out.println(textView);
            buttonClickListener.onButtonClick(getAbsoluteAdapterPosition());
        }
    }

    public interface buttonClickListener {
        void onButtonClick(int position);
    }

}