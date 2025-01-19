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

import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<MemberDetail> memberDetails;
    PaidButtonListener paidButtonListener;

    public MyItemRecyclerViewAdapter(Context context, List<MemberDetail> items) {
        memberDetails = items;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = memberDetails.get(position);
        holder.memberId.setText(memberDetails.get(position).getMemberId());
        holder.memberName.setText(memberDetails.get(position).getMemberName().concat(" ").concat(memberDetails.get(position).getFatherName()));

       holder.paidBtn.setOnClickListener(view -> {
        Toast.makeText(context, "Item Clicked Paid :"+memberDetails.get(position).getMemberId()+" "+memberDetails.get(position).getMemberName(), Toast.LENGTH_SHORT).show();
       });

       holder.notPaidBtn.setOnClickListener(view -> {
           Toast.makeText(context, "Item Clicked Not Paid :"+position, Toast.LENGTH_SHORT).show();
       });
    }

    @Override
    public int getItemCount() {
        return memberDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView memberId;
        public final TextView memberName;
        public MemberDetail mItem;

        public Button paidBtn, notPaidBtn;
        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            memberId = binding.memberId;
            memberName = binding.memberName;
            paidBtn = itemView.findViewById(R.id.paidBtn);
            notPaidBtn = itemView.findViewById(R.id.notPaidBtn);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + memberName.getText() + " ";
        }
    }
}

@FunctionalInterface
interface PaidButtonListener {
    void paidButtonClicked(View view, int position);
}