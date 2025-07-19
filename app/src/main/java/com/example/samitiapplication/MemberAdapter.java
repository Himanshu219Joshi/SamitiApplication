package com.example.samitiapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.LastMemberDetails;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.modal.members.MemberModal;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.PersonViewHolder> {

    List<MemberModal> personList;

    public MemberAdapter(Context context, List<MemberModal> person) {
        personList = person;
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public MemberAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_member_detail, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.PersonViewHolder holder, int position) {
        MemberModal person = personList.get(position);
        holder.memberId.setText(context.getString(R.string.member_id).concat(" ").concat(String.valueOf(person.getMemberId())));
        holder.memberName.setText(context.getString(R.string.name).concat(person.getMemberName()).concat(" ").concat(person.getFatherName()));
        holder.loanAmount.setText(context.getString(R.string.loan_amount).concat(" ").concat(String.valueOf(person.getLoanAmount())));
        holder.investedMoney.setText(context.getString(R.string.invested_money).concat(" ").concat(String.valueOf(person.getInvestedMoney())));
        holder.memberStatus.setText(context.getString(R.string.member_status).concat(" ").concat(person.getMemberStatus()));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView memberId, memberName, loanAmount, investedMoney, memberStatus;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            memberId = itemView.findViewById(R.id.memberId);
            memberName = itemView.findViewById(R.id.memberName);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            investedMoney = itemView.findViewById(R.id.investedMoney);
            memberStatus = itemView.findViewById(R.id.memberStatus);
        }
    }
}
