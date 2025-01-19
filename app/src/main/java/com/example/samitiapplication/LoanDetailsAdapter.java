package com.example.samitiapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.LoanDetail;
import com.example.samitiapplication.modal.MemberDetail;

import java.util.List;

public class LoanDetailsAdapter extends RecyclerView.Adapter<LoanDetailsAdapter.LoanDetailsViewHolder> {

    List<LoanDetail> loans;

    public LoanDetailsAdapter(Context context, List<LoanDetail> loans) {
        this.loans = loans;
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public LoanDetailsAdapter.LoanDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_loan_member, parent, false);
        return new LoanDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanDetailsAdapter.LoanDetailsViewHolder holder, int position) {
        LoanDetail loanDetail = loans.get(position);
        holder.memberId.setText(context.getString(R.string.member_id).concat(" ").concat(loanDetail.getMemberDetails().getMemberId()));
        holder.memberName.setText(context.getString(R.string.member_name).concat(" ").concat(loanDetail.getMemberDetails().getMemberName()));
        holder.loanAmount.setText(context.getString(R.string.loan_amount).concat(" ").concat(loanDetail.getLoanAmount()));
        holder.loanDate.setText(context.getString(R.string.loan_date).concat(" ").concat(loanDetail.getDate()));
        holder.loanEmi.setText(context.getString(R.string.loan_emi).concat(" ").concat(String.valueOf(loanDetail.getEmiAmount())));
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    public static class LoanDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView memberId, memberName, loanAmount, loanDate, loanEmi;
        public LoanDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            memberId = itemView.findViewById(R.id.memberId);
            memberName = itemView.findViewById(R.id.memberName);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            loanDate = itemView.findViewById(R.id.loanDate);
            loanEmi = itemView.findViewById(R.id.loanEmi);

        }
    }
}
