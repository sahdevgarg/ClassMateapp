package com.htlconline.sm.classmate;

/**
 * Created by Shikhar Garg on 26-12-2016.
 */
public class Config {

    private static final String Color_Type_NM = "#808080";
    private static final String Color_Type_LP = "#FFFF00";
    private static final String Color_Type_A = "#FF0000";
    private static final String Color_Type_P = "#008000";

    private static final String ATTEN_TYPE_NM = "NM";
    private static final String ATTEN_TYPE_LP = "LP";
    private static final String ATTEN_TYPE_A = "A";
    private static final String ATTEN_TYPE_P = "P";

    public static final String IS_LOG_IN="is_logged_in";
    public static final String IS_OTP_DONE="is_otp_done";
    public static final String IS_KYC_DONE="is_kyc_done";
    public static final String IS_STUDENT="is_student";
    public static final String PREF_NAME="Login";
    public static final Integer PRIVATE_MODE=0;

    public static final Integer REQUEST_LOG_IN=0;
    public static final Integer REQUEST_API=1;
    public static final String SERVER_NOT_RESPOND = "Server is not responding.";
    public static final String NETWORK_NOT_AVAILABLE = "Network is not available, Please try again.";
    public static final String SET_USER_ID = "userId";

    public static final String SET_COOKIE_KEY="Cookie";
    public static final String SET_USER_NAME="username";
    public static final String SET_USER_ROLE = "role";
    public static final String BATCH_TITLE = "title";
    public static final String SET_STUDENT_ID = "student_id";
    public static final String SET_USER_NAME_ID = "user_name_id";
    public static final String NO_OF_STUDENTS = "no_of_students";
    public static final String JSON_RESPONSE = "login_response";
    public static final String ACTIVE_STUDENT = "active_student";

    public static String STUDENT_ID="";
    public static final String BATCH_ID="batchId";

   // public static String BASE_URL = "http://www.htlconline.com/";
    //public static final String BASE_URL = "http://10.140.64.90/";
    public static final String BASE_URL = "http://103.26.201.48:9090/";
    public static final String LOGIN_URL = BASE_URL + "api/users/login/";
    public static final String BATCH_LIST_URL = BASE_URL + "api/batch/listing/";
    public static final String BATCH_DETAIL_URL = BASE_URL + "api/timetable/";
    public static final String LESSION_PLAN_URL = BASE_URL + "/api/batch/lesson-plan/";
    public static final String ATTENDANCE_URL = BASE_URL + "/api/student/attendance/";
    public static final String ALLOWED_BATCH_LIST_URL = BASE_URL + "/api/batch/allowed-batches/";

    public class Role {
        public static final String STUDENT = "8";
        public static final String PARENT = "12";
    }

    public static String getAttendanceColor(String attendanceStatus){
        if(attendanceStatus.equalsIgnoreCase(ATTEN_TYPE_NM))
            return Color_Type_NM;
        else if(attendanceStatus.equalsIgnoreCase(ATTEN_TYPE_LP))
            return Color_Type_LP;
        else if(attendanceStatus.equalsIgnoreCase(ATTEN_TYPE_A))
            return Color_Type_A;
        else if(attendanceStatus.equalsIgnoreCase(ATTEN_TYPE_P))
            return Color_Type_P;
        else
            return Color_Type_NM;
    }

    public static String getMonthName(int month){
        switch(month+1){
            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "Jun";

            case 7:
                return "Jul";

            case 8:
                return "Aug";

            case 9:
                return "Sep";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";
        }

        return "";
    }


}
