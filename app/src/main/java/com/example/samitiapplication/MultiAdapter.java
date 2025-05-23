package com.example.samitiapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.data.DatabaseHelper;
import com.example.samitiapplication.data.model.UserInfo;
import com.example.samitiapplication.modal.Employee;
import com.example.samitiapplication.modal.MemberDetail;

import java.util.ArrayList;
import java.util.List;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

    private Context context;
    DatabaseHelper db;

    UserInfo userInfo;

    private ArrayList<MemberDetail> memberDetails;

    public MultiAdapter(Context context, ArrayList<MemberDetail> memberDetails) {
        this.context = context;
        this.memberDetails = memberDetails;
    }

    public void setEmployees(ArrayList<MemberDetail> employees) {
        this.memberDetails = new ArrayList<>();
        this.memberDetails = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(memberDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return memberDetails.size();
    }


    class MultiViewHolder extends RecyclerView.ViewHolder {

        private final TextView memberName, memberId, memberAmount;
        private final ImageView paidTickIcon, notPaidCrossIcon;

//        private final Button paidBtn, notPaidBtn;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.memberName);
            memberId = itemView.findViewById(R.id.memberId);
            paidTickIcon = itemView.findViewById(R.id.paidTick);
            notPaidCrossIcon = itemView.findViewById(R.id.notPaidCross);
            memberAmount = itemView.findViewById(R.id.memberAmount);
//            paidBtn = itemView.findViewById(R.id.paidBtn);
//            notPaidBtn = itemView.findViewById(R.id.notPaidBtn);
        }

        void bind(final MemberDetail memberDetail) {
            db = new DatabaseHelper(itemView.getContext());
            Cursor cursor = db.getAllData();
            paidTickIcon.setVisibility(memberDetail.isPaid() ? View.VISIBLE : View.GONE);
            notPaidCrossIcon.setVisibility(memberDetail.isNotPaid() ? View.VISIBLE : View.GONE);
            memberId.setText(memberDetail.getMemberId());
            memberName.setText(memberDetail.getMemberName().concat(" ").concat(memberDetail.getFatherName()));
            memberAmount.setText("EMI: ".concat("500"));
//            paidBtn.setVisibility(View.GONE);
//            notPaidBtn.setVisibility(View.GONE);

            List<Integer> paidMemberIds = new ArrayList<Integer>();

            while (cursor.moveToNext()) {
                paidMemberIds.add(cursor.getInt(0));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (paidMemberIds.contains(Integer.parseInt(memberDetail.getMemberId()))){
                        db.updateRecord(Integer.parseInt(memberDetail.getMemberId()), String.valueOf(!memberDetail.isPaid()));
                        memberDetail.setPaid(!memberDetail.isPaid());
                    } else {
                        userInfo = new UserInfo();

                        userInfo.setMemeberId(Integer.parseInt(memberDetail.getMemberId()));
                        userInfo.setMemberName(memberDetail.getMemberName());
                        userInfo.setInstallmentStatus(String.valueOf(!memberDetail.isPaid()));
                        memberDetail.setPaid(!memberDetail.isPaid());
                        db.insetData(userInfo);
                    }

//
                    paidTickIcon.setVisibility(memberDetail.isPaid() ? View.VISIBLE : View.GONE);
                    notifyItemChanged(getAbsoluteAdapterPosition());
                }
            });

//            paidBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    memberDetail.setPaid(!memberDetail.isPaid());
//                    memberDetail.setNotPaid(!memberDetail.isPaid());
//                    if(memberDetail.isPaid()) {
//                        paidTickIcon.setVisibility(memberDetail.isPaid() ? View.VISIBLE : View.GONE);
//                        paidBtn.setVisibility(memberDetail.isPaid() || memberDetail.isNotPaid() ? View.GONE : View.VISIBLE);
//                        notPaidBtn.setVisibility(memberDetail.isNotPaid() || memberDetail.isPaid() ? View.GONE : View.VISIBLE);
//                        notifyItemChanged(getAbsoluteAdapterPosition());
//                    }
//                }
//            });
//
//            notPaidBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    memberDetail.setNotPaid(!memberDetail.isNotPaid());
//                    memberDetail.setPaid(!memberDetail.isNotPaid());
//
//                    if(memberDetail.isNotPaid()) {
//                        notPaidCrossIcon.setVisibility(memberDetail.isNotPaid() ? View.VISIBLE : View.GONE);
//                       paidBtn.setVisibility(memberDetail.isPaid() || memberDetail.isNotPaid() ? View.GONE : View.VISIBLE);
//                       notPaidBtn.setVisibility(memberDetail.isNotPaid() || memberDetail.isPaid() ? View.GONE : View.VISIBLE);
////
//                        notifyItemChanged(getAbsoluteAdapterPosition());
//                    }
//                }
//            });
        }
    }

    public ArrayList<MemberDetail> getAll() {
        return memberDetails;
    }

    public ArrayList<MemberDetail> getSelected() {
        ArrayList<MemberDetail> selected = new ArrayList<>();
        for (int i = 0; i < memberDetails.size(); i++) {
            if (memberDetails.get(i).isPaid()) {
                selected.add(memberDetails.get(i));
            }
        }
        return selected;
    }



}