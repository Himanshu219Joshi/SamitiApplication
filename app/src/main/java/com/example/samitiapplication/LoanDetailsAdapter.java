package com.example.samitiapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.loans.LoanModal;

import java.util.List;

public class LoanDetailsAdapter extends RecyclerView.Adapter<LoanDetailsAdapter.LoanDetailsViewHolder> {

    List<LoanModal> loans;

    private final OnLoanItemClickListener onLoanItemClickListener;

    public LoanDetailsAdapter(Context context, List<LoanModal> loans, OnLoanItemClickListener onLoanItemClickListener) {
        this.loans = loans;
        this.context = context;
        this.onLoanItemClickListener = onLoanItemClickListener;
    }

    Context context;

    @NonNull
    @Override
    public LoanDetailsAdapter.LoanDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_loan_member, parent, false);
        return new LoanDetailsViewHolder(view, onLoanItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanDetailsAdapter.LoanDetailsViewHolder holder, int position) {
        LoanModal loanDetail = loans.get(position);
//        Log.d("LOAN DETAILS", loanDetail.getMemberDetails().getMemberName());
        holder.memberId.setText(context.getString(R.string.member_id).concat(" ").concat(String.valueOf(loanDetail.getMemberDetails().getMemberId())));
        holder.memberName.setText(context.getString(R.string.member_name).concat(" ").concat(loanDetail.getMemberDetails().getMemberName()).concat(" ").concat(loanDetail.getMemberDetails().getFatherName()));
        holder.loanAmount.setText(context.getString(R.string.loan_amount).concat(" ").concat(String.valueOf(loanDetail.getLoanAmount())));
        holder.loanDate.setText(context.getString(R.string.loan_date).concat(" ").concat(loanDetail.getDate()));
        holder.loanEmi.setText(context.getString(R.string.loan_emi).concat(" ").concat(String.valueOf(loanDetail.getEmiAmount())));
        holder.guarantorNames.setText(context.getString(R.string.guarantor_names).concat(" ").concat(loanDetail.getGuarantors().get(0).getMemberName()).concat(", ").concat(loanDetail.getGuarantors().get(1).getMemberName()));
        holder.loanStatus.setText(context.getString(R.string.loan_status).concat(" ").concat(loanDetail.getLoanStatus()));
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    public static class LoanDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView memberId, memberName, loanAmount, loanDate, loanEmi, guarantorNames, loanStatus;
        OnLoanItemClickListener onLoanItemClickListener;
        public LoanDetailsViewHolder(@NonNull View itemView, OnLoanItemClickListener onLoanItemClickListener) {
            super(itemView);

            memberId = itemView.findViewById(R.id.memberId);
            memberName = itemView.findViewById(R.id.memberName);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            loanDate = itemView.findViewById(R.id.loanDate);
            loanEmi = itemView.findViewById(R.id.loanEmi);
            guarantorNames = itemView.findViewById(R.id.guarantorNames);
            loanStatus = itemView.findViewById(R.id.loanStatus);
            this.onLoanItemClickListener = onLoanItemClickListener;
            itemView.setOnClickListener(this);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder;
//                    builder = new AlertDialog.Builder(itemView.getContext());
//                    builder.setCancelable(true);
//                    builder.setTitle("Title");
//                    builder.setMessage("Sampel message");
//                    builder.show();
//                }
//            });

        }

        @Override
        public void onClick(View v) {
            onLoanItemClickListener.onLoanItemClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnLoanItemClickListener {
        void onLoanItemClick(int position);
    }
}
