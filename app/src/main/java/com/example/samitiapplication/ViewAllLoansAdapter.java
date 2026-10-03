package com.example.samitiapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.loans.LoanModal;
import com.example.samitiapplication.modal.members.MemberModal;

import java.util.ArrayList;
import java.util.List;

public class ViewAllLoansAdapter extends RecyclerView.Adapter<ViewAllLoansAdapter.LoanViewHolder> {

    List<LoanModal> loansList;
    Context context;

    private final OnLoanItemClickListener onLoanItemClickListener;

    public ViewAllLoansAdapter(Context context,List<LoanModal> loans, OnLoanItemClickListener onLoanItemClickListener) {
        this.loansList = loans;
        this.context = context;
        this.onLoanItemClickListener = onLoanItemClickListener;
    }

    public void setFilteredList(List<LoanModal> filteredList) {
        this.loansList = filteredList;
        notifyDataSetChanged();
    }

    public LoanModal getLoanAt(int position) {
        if (loansList != null && position >= 0 && position < loansList.size()) {
            return loansList.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewAllLoansAdapter.LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_view_all_loans_info, parent, false);
        // 3. Pass the listener to the ViewHolder
        return new LoanViewHolder(view, onLoanItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllLoansAdapter.LoanViewHolder holder, int position) {
        LoanModal loanDetail = loansList.get(position);

        if (loanDetail.getMemberDetails() != null) {
            holder.memberId.setText(String.valueOf(loanDetail.getMemberDetails().getMemberId()));
            holder.memberName.setText(" ".concat(loanDetail.getMemberDetails().getMemberName()).concat(" ").concat(loanDetail.getMemberDetails().getFatherName()));
        }

        holder.loanAmount.setText(" ".concat(String.valueOf(loanDetail.getLoanAmount())));
        holder.loanDate.setText(" ".concat(loanDetail.getDate()));
        holder.loanEmi.setText(" ".concat(String.valueOf(loanDetail.getEmiAmount())));

        if (loanDetail.getGuarantors() != null && loanDetail.getGuarantors().size() >= 2) {
            holder.guarantorNames.setText(" "
                    .concat(loanDetail.getGuarantors().get(0).getMemberName()).concat(", ")
                    .concat(loanDetail.getGuarantors().get(1).getMemberName()));
        }

        holder.loanStatus.setText(" ".concat(loanDetail.getLoanStatus()));
    }

    @Override
    public int getItemCount() {
        return loansList != null ? loansList.size() : 0;
    }


    public static class LoanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView memberId, memberName, loanAmount, loanDate, loanEmi, guarantorNames, loanStatus;

        OnLoanItemClickListener onLoanItemClickListener;

        public LoanViewHolder(@NonNull View itemView, OnLoanItemClickListener onLoanItemClickListener) {
            super(itemView);
            memberId = itemView.findViewById(R.id.memberId);
            memberName = itemView.findViewById(R.id.memberName);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            loanDate = itemView.findViewById(R.id.loanDate);
            loanEmi = itemView.findViewById(R.id.loanEmi);
            guarantorNames = itemView.findViewById(R.id.guarantorNames);
            loanStatus = itemView.findViewById(R.id.loanStatus);

            this.onLoanItemClickListener = onLoanItemClickListener;

            // This line enables the click listener for the entire row
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (onLoanItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onLoanItemClickListener.onLoanItemClick(position);
                }
            }
        }
    }

    public interface OnLoanItemClickListener {
        void onLoanItemClick(int position);
    }
}
