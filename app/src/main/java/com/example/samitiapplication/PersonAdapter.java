package com.example.samitiapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    List<Person> personList;

    public PersonAdapter(Context context, List<Person> person) {
        personList = person;
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public PersonAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.PersonViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.memberId.setText(context.getString(R.string.member_id).concat(person.getId()));
        holder.memberName.setText(context.getString(R.string.name).concat(person.getMemberName()).concat(" ").concat(person.getFatherName()));
        holder.loanAmount.setText(context.getString(R.string.loan_amount).concat(person.getLoanAmount()));
        holder.investedMoney.setText(context.getString(R.string.invested_money).concat(person.getInvestedMoney()));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView memberId, memberName, loanAmount, investedMoney;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            memberId = itemView.findViewById(R.id.memberId);
            memberName = itemView.findViewById(R.id.memberName);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            investedMoney = itemView.findViewById(R.id.investedMoney);
        }
    }
}
