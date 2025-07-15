package com.example.samitiapplication.utils;

public class CustomDate {

    String monthName;
    public String getMonthNameEnglish(int month){
        switch (month) {
            case 0:
                monthName= "Jan";
                break;
            case 1:
                monthName="Feb";
            case 2:
                monthName="Mar";
                break;
            case 3:
                monthName="Apr";
                break;
            case 4:
                monthName="May";
                break;
            case 5:
                monthName="Jun";
                break;
            case 6:
                monthName="Jul";
                break;
            case 7:
                monthName="Aug";
                break;
            case 8:
                monthName="Sep";
                break;
            case 9:
                monthName="Oct";
            case 10:
                monthName="Nov";
                break;
            case 11:
                monthName="Dec";
                break;
        }

        return monthName;
    }

    public String getMonthNameHindi(int month){
        switch (month) {
            case 0:
                monthName= "जनवरी";
                break;
            case 1:
                monthName="फ़रवरी";
            case 2:
                monthName="मार्च";
                break;
            case 3:
                monthName="अप्रैल";
                break;
            case 4:
                monthName="मई";
                break;
            case 5:
                monthName="जून";
                break;
            case 6:
                monthName="जुलाई";
                break;
            case 7:
                monthName="अगस्त";
                break;
            case 8:
                monthName="सितंबर";
                break;
            case 9:
                monthName="अक्टूबर";
            case 10:
                monthName="नवंबर";
                break;
            case 11:
                monthName="दिसंबर";
                break;
        }

        return monthName;
    }


}
