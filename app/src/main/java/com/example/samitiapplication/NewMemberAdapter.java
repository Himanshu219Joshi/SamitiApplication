package com.example.samitiapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.members.MemberModal;

import java.util.List;

public class NewMemberAdapter extends RecyclerView.Adapter<NewMemberAdapter.PersonViewHolder> {

    private List<MemberModal> personList;
    private Context context;
    // 1. Define the listener at the adapter level
    private final OnMemberItemClickListener onMemberItemClickListener;

    public NewMemberAdapter(Context context, List<MemberModal> person, OnMemberItemClickListener listener) {
        this.personList = person;
        this.context = context;
        this.onMemberItemClickListener = listener; // 2. Assign the listener here
    }

    public void setFilteredList(List<MemberModal> filteredList) {
        this.personList = filteredList;
        notifyDataSetChanged();
    }

    public MemberModal getMemberAt(int position) {
        return personList.get(position);
    }

    @NonNull
    @Override
    public NewMemberAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_new_member_list_info, parent, false);
        // 3. Pass the listener to the ViewHolder
        return new PersonViewHolder(view, onMemberItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewMemberAdapter.PersonViewHolder holder, int position) {
        MemberModal person = personList.get(position);
        holder.memberId.setText(String.valueOf(person.getMemberId()));
        holder.memberName.setText(String.valueOf(person.getMemberName()).concat(" ").concat(person.getFatherName()));
        holder.loanAmount.setText(String.valueOf(person.getLoanAmount()));
        holder.investedMoney.setText(String.valueOf(person.getInvestedMoney()));
//        holder.memberStatus.setText(context.getString(R.string.member_status).concat(" ").concat(person.getMemberStatus()));
        holder.interestEarned.setText(String.valueOf(person.getInterestEarned()));

        if(person.getMemberStatus().equals("Active")){
            holder.otherStatus.setVisibility(View.GONE);
            holder.activeStatus.setVisibility(View.VISIBLE);
        }else{
            holder.otherStatus.setVisibility(View.VISIBLE);
            holder.activeStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return personList != null ? personList.size() : 0;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView memberId, memberName, loanAmount, investedMoney, memberStatus, interestEarned, activeStatus, otherStatus;


        OnMemberItemClickListener onMemberItemClickListener;

        public PersonViewHolder(@NonNull View itemView, OnMemberItemClickListener listener) {
            super(itemView);
            memberId = itemView.findViewById(R.id.memberId);
            memberName = itemView.findViewById(R.id.memberName);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            investedMoney = itemView.findViewById(R.id.investedMoney);
            otherStatus = itemView.findViewById(R.id.otherStatus);
            activeStatus = itemView.findViewById(R.id.activeStatus);
            interestEarned = itemView.findViewById(R.id.interestEarned);

            // 4. Correctly assign the listener passed from the adapter
            this.onMemberItemClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onMemberItemClickListener != null) {
                onMemberItemClickListener.onMemberItemClick(getAbsoluteAdapterPosition());
            }
        }
    }

    public interface OnMemberItemClickListener {
        void onMemberItemClick(int position);
    }
}