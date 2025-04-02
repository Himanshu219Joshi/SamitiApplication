package com.example.samitiapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.Employee;
import com.example.samitiapplication.modal.MemberDetail;

import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

    private Context context;
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

        private final TextView memberName, memberId;
        private final ImageView paidTickIcon, notPaidCrossIcon;

        private final Button paidBtn, notPaidBtn;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.memberName);
            memberId = itemView.findViewById(R.id.memberId);
            paidTickIcon = itemView.findViewById(R.id.paidTick);
            notPaidCrossIcon = itemView.findViewById(R.id.notPaidCross);
            paidBtn = itemView.findViewById(R.id.paidBtn);
            notPaidBtn = itemView.findViewById(R.id.notPaidBtn);
        }

        void bind(final MemberDetail memberDetail) {
            paidTickIcon.setVisibility(memberDetail.isPaid() ? View.VISIBLE : View.GONE);
            notPaidCrossIcon.setVisibility(memberDetail.isNotPaid() ? View.VISIBLE : View.GONE);
            memberId.setText(memberDetail.getMemberId());
            memberName.setText(memberDetail.getMemberName().concat(" ").concat(memberDetail.getFatherName()));
            paidBtn.setVisibility(View.GONE);
            notPaidBtn.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    memberDetail.setPaid(!memberDetail.isPaid());
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

//package com.example.samitiapplication;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.samitiapplication.modal.MemberDetail;
//
//import java.util.ArrayList;
//
//public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {
//
//    private Context context;
//    private ArrayList<MemberDetail> memberDetails;
//
//    public MultiAdapter(Context context, ArrayList<MemberDetail> memberDetails) {
//        this.context = context;
//        this.memberDetails = memberDetails;
//    }
//
//    public void setmemberDetails(ArrayList<MemberDetail> memberDetails) {
//        this.memberDetails = new ArrayList<>();
//        this.memberDetails = memberDetails;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item, viewGroup, false);
//        return new MultiViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
//        multiViewHolder.paidBtn.setOnClickListener(view-> {
//                memberDetails.get(position).setSelected(true);
//                boolean isSelected = memberDetails.get(position).isSelected();
//
//                multiViewHolder.paidBtn.setVisibility(isSelected ? View.GONE:View.VISIBLE);
//                multiViewHolder.paidBtnTick.setVisibility(isSelected ? View.VISIBLE : View.GONE);
//
//
//        });
//
//        multiViewHolder.notPaidBtn.setOnClickListener(view->{
//            multiViewHolder.notPaidBtn.setVisibility(View.GONE);
//            multiViewHolder.notPaidCrossBtn.setVisibility(View.VISIBLE);
//        });
//
//        multiViewHolder.bind(memberDetails.get(position));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return memberDetails.size();
//    }
//
//    class MultiViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView memberId, memberName;
//
//        private Button paidBtn, notPaidBtn;
//        private ImageView paidBtnTick, notPaidCrossBtn;
//
//        MultiViewHolder(@NonNull View itemView) {
//            super(itemView);
//            memberId = itemView.findViewById(R.id.memberId);
//            memberName = itemView.findViewById(R.id.memberName);
//            paidBtn = itemView.findViewById(R.id.paidBtn);
//            notPaidBtn = itemView.findViewById(R.id.notPaidBtn);
//            paidBtnTick = itemView.findViewById(R.id.paidTick);
//            notPaidCrossBtn = itemView.findViewById(R.id.notPaidCross);
//        }
//
//
//        void bind(final MemberDetail memberDetail) {
//            paidBtn.setVisibility(memberDetail.isSelected() ? View.GONE : View.VISIBLE);
//            notPaidBtn.setVisibility(memberDetail.isSelected() ? View.GONE : View.VISIBLE);
//            memberName.setText(memberDetail.getMemberName());
//            memberId.setText(memberDetail.getMemberId());
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                        if (!getSelected().isEmpty()) {
//                            for (int i = 0; i < getSelected().size(); i++) {
//
//                                getSelected().get(i).setSelected(true);
//                            }
////
//                        }
//
//                }
//            });
//        }
//    }
//
//    public ArrayList<MemberDetail> getAll() {
//        return memberDetails;
//    }
//
//    public ArrayList<MemberDetail> getSelected() {
//        ArrayList<MemberDetail> selected = new ArrayList<>();
//        for (int i = 0; i < memberDetails.size(); i++) {
//            if (memberDetails.get(i).isSelected()) {
//                selected.add(memberDetails.get(i));
//            }
//        }
//        return selected;
//    }
//}