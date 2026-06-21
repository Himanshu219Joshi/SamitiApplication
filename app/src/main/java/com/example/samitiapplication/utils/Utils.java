package com.example.samitiapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
public class Utils {

        public String getNextDueDate() {
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            // If today is after the 15th, move to next month
            if (currentDay > 15) {
                calendar.add(Calendar.MONTH, 1);
            }

            // Set the day to exactly 15
            calendar.set(Calendar.DAY_OF_MONTH, 15);

            // Format the date (e.g., "15 July 2024")
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            return sdf.format(calendar.getTime());
        }

        public int getPercentagePaid(double totalAmount, double recoveredAmount) {
            if (totalAmount <= 0) {
                return 0;
            }
            double percentage = (recoveredAmount / totalAmount) * 100;

            // Return as an integer for the ProgressBar
            return (int) Math.round(percentage);
        }

        public long getOutstandingAmount(long loanAmount, long loanAmountRecovered){
            return Math.round(loanAmount - loanAmountRecovered);
        }
}
