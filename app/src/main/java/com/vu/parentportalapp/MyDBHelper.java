package com.vu.parentportalapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyDBHelper extends SQLiteAssetHelper {
    // Define the database name and version
    private static final String DB_NAME = "MyDBHelper.sqlite";
    private static final int DB_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    public long insertParent(String name, String contact, String email, String password, String address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("parent_name", name);
        values.put("parent_contact", contact);
        values.put("parent_email", email);
        values.put("parent_password", password);
        values.put("address", address);

        long result = db.insert("Parents", null, values);
        db.close();
        return result;
    }

    public long insertStudent(String name, String dob, int parentId, String className, String section, String rollNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Student_name", name);
        values.put("Student_DOB", dob);
        values.put("Student_Rollno", rollNo);
        values.put("Parent_ID", parentId);
        values.put("Student_class", className);  // class as String
        values.put("Student_section", section);
        // Optional: add ProfilePicture if needed
        return db.insert("Student", null, values);
    }

    // Insert FeeDetail
    public long insertFeeDetail(int studentId, String feeType, double amount, String dueDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Student_ID", studentId);
        values.put("FeeType", feeType);
        values.put("Amount", amount);
        values.put("DueDate", dueDate);
        values.put("Status", "Unpaid"); // Default value

        long result = db.insert("FeeDetail", null, values);
        db.close();
        return result;
    }

    // Fetch Student IDs for the dropdown (Searchable List)
    public String[] getAllStudentIDs() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Student_ID FROM Student", null);

        if (cursor == null || cursor.getCount() == 0) {
            return new String[]{}; // Return an empty array if no data
        }

        String[] studentIds = new String[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            studentIds[i++] = cursor.getString(0);
        }
        cursor.close();
        return studentIds;
    }

    public boolean insertAnnouncement(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title", title);
        values.put("Description", description);

        long result = db.insert("Announcement", null, values);
        db.close();
        return result != -1;
    }
//LeaveApplicaion parent
    public long insertLeaveApplication(int studentId, String startDate, String endDate, String leaveType, String reason, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("StudentID", studentId);
        values.put("LeaveStartDate", startDate);
        values.put("LeaveEndDate", endDate);
        values.put("LeaveType", leaveType);
        values.put("Reason", reason);
        values.put("Status", status);

        long result = db.insert("LeaveApplications", null, values);
        db.close();
        return result;
    }
//    FeedBack for parent(in working)
    public boolean insertFeedback(int parentId, String message, String sender) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("Parent_ID", parentId);
    values.put("Feedback", message);
    values.put("Sender", sender); // "parent" or "admin"

    long result = db.insert("Feedback", null, values);
    return result != -1;
}

    // Save diary entry to the correct column based on type
    public boolean saveDiaryEntry(String studentClass, String type, String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Student_Class", studentClass);

        switch (type) {
            case "Classwork":
                values.put("Classwork", detail);
                break;
            case "Homework":
                values.put("Homework", detail);
                break;
            case "Assignment":
                values.put("Assignment", detail);
                break;
            default:
                return false;
        }

        long result = db.insert("DailyDiary", null, values);
        return result != -1;
    }

//    Syllbus entry
    public boolean insertSyllabus(String studentClass, String subject, String topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Student_class", studentClass);
        values.put("Subject", subject);
        values.put("Topic", topic);

        long result = db.insert("Syllabus", null, values);
        return result != -1;
    }

//TimeTable entry
    public boolean insertTimeTable(String studentClass, String day, String timeSlot, String subject, String teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Student_class", studentClass);
        values.put("Day", day);
        values.put("TimeSlot", timeSlot);
        values.put("Subject", subject);
        values.put("Teacher", teacher);

        long result = db.insert("TimeTable", null, values);
        return result != -1; // returns true if insert was successful
    }

    // Insert transport data
    public boolean insertTransportData(int studentId, String busNumber, String driverName, String driverContact, String pickup, String drop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Student_ID", studentId);
        values.put("BusNumber", busNumber);
        values.put("DriverName", driverName);
        values.put("DriverContact", driverContact);
        values.put("PickupPoint", pickup);
        values.put("DropPoint", drop);
        long result = db.insert("Transport", null, values);
        return result != -1;
    }

// Update leave status
    public void updateLeaveStatus(int leaveId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Status", newStatus);
        db.update("LeaveApplications", values, "Leave_ID = ?", new String[]{String.valueOf(leaveId)});
    }

//update attendance based on leaves
    public void updateAttendanceForLeave(int studentId, String startDate, String endDate, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        try {
            Date start = sdf.parse(startDate); // Parse start date
            Date end = sdf.parse(endDate); // Parse end date

            // Set the calendar to the start date
            startCalendar.setTime(start);
            endCalendar.setTime(end);

            // Iterate over each day in the range
            while (!startCalendar.after(endCalendar)) {
                String currentDate = sdf.format(startCalendar.getTime());

                // Check if attendance record exists for the current date
                ContentValues values = new ContentValues();
                values.put("Student_ID", studentId);
                values.put("Date", currentDate);
                values.put("Status", status);

                // Try updating the record if it already exists
                int rowsAffected = db.update("Attendance", values,
                        "Student_ID = ? AND Date = ?", new String[]{String.valueOf(studentId), currentDate});

                // If no record was found (rowsAffected == 0), insert a new record
                if (rowsAffected == 0) {
                    // Insert the attendance record with the "Leave" status
                    long result = db.insert("Attendance", null, values);
                    if (result == -1) {
                        // Handle insertion failure (optional)
                        Log.e("Attendance Update", "Failed to insert attendance for " + currentDate);
                    }
                }

                // Move to the next day
                startCalendar.add(Calendar.DATE, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    // Save Attendance Data
    public void saveAttendanceData(int studentId, String date, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Student_ID", studentId);
        values.put("Date", date);
        values.put("Status", status);

        // Check if "Month" column exists, and ignore it if it does
        if (!hasMonthColumn()) {
            // Perform insertion without "Month"
            db.insert("Attendance", null, values);
        } else {
            // If "Month" column exists, proceed without inserting "Month" data
            db.insert("Attendance", null, values);
        }
        db.close();
    }

    public boolean hasMonthColumn() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(Attendance);", null);

        // Check if the cursor has any data
        if (cursor != null && cursor.moveToFirst()) {
            // Iterate through all columns and check if "Month" exists
            int columnIndex = cursor.getColumnIndex("name");
            if (columnIndex == -1) {
                cursor.close();
                return false; // "name" column doesn't exist
            }

            do {
                String columnName = cursor.getString(columnIndex);
                if ("Month".equals(columnName)) {
                    cursor.close();
                    return true; // "Month" column exists
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false; // "Month" column doesn't exist
    }

    public boolean insertReportCard(int studentId, String term, String marksJson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Student_ID", studentId);
        contentValues.put("Term", term);
        contentValues.put("Marks", marksJson); // Marks as a JSON string

        long result = db.insert("ReportCard", null, contentValues);
        return result != -1; // Return true if insert was successful
    }
    public boolean updateReportCard(int studentId, String term, String marksJson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Marks", marksJson); // Update the Marks as a JSON string

        // Update the report card based on Student_ID and Term
        int rowsUpdated = db.update("ReportCard", contentValues, "Student_ID = ? AND Term = ?",
                new String[]{String.valueOf(studentId), term});
        return rowsUpdated > 0; // Return true if the update was successful
    }

    // Open the database
    public SQLiteDatabase openDatabase() {
        SQLiteDatabase db = getReadableDatabase(); // SQLiteAssetHelper automatically copies the database if needed
        Log.d("MyDBHelper", "Database opened successfully.");
        return db;
    }

// Fetch announcements from the database
    public List<announcement_item> getAnnouncements() {
        List<announcement_item> announcements = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        if (db == null) {
            Log.e("MyDBHelper", "Database is null.");
            return announcements;
        }

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT Title, Description, CreatedAt FROM Announcement", null);

            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(0);  // Title
                    String description = cursor.getString(1);  // Description
                    String date = cursor.getString(2);  // CreatedAt

                    announcements.add(new announcement_item(title, description, date));
                } while (cursor.moveToNext());
            }

            Log.d("MyDBHelper", "Retrieved " + announcements.size() + " announcements.");
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error retrieving announcements: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return announcements;
    }

//    Login for different user types
    public boolean checkLogin(String userType, String email, String password, Context context) {
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            Log.e("MyDBHelper", "Database is null.");
            return false;
        }

        String query;
        String[] selectionArgs;

        if (userType.equals("Parent")) {
            query = "SELECT Parent_ID, parent_name, parent_contact, parent_email, parent_password, address FROM Parents WHERE parent_email = ? AND parent_password = ?";
            selectionArgs = new String[]{email, password};
        } else if (userType.equals("Admin")) {
            query = "SELECT admin_id, admin_name FROM Admin WHERE admin_name = ? AND admin_password = ?";
            selectionArgs = new String[]{email, password};
        } else {
            return false; // Invalid user type
        }

        try (Cursor cursor = db.rawQuery(query, selectionArgs)) {
            if (cursor.moveToFirst()) {
                SharedPreferences prefs = context.getSharedPreferences("ParentPortalPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                if (userType.equals("Parent")) {
                    int parentID = cursor.getInt(0);
                    String parentName = cursor.getString(1);
                    String parentContact = cursor.getString(2);
                    String parentEmail = cursor.getString(3);
                    String parentPassword = cursor.getString(4);
                    String address = cursor.getString(5);

                    // Fetch Student_ID linked to Parent_ID
                    int studentID = -1;
                    try (Cursor studentCursor = db.rawQuery(
                            "SELECT Student_ID FROM Student WHERE Parent_ID = ?",
                            new String[]{String.valueOf(parentID)})) {
                        if (studentCursor.moveToFirst()) {
                            studentID = studentCursor.getInt(0);
                        }
                    }

                    // Store Parent & Student details in SharedPreferences
                    editor.putInt("parent_id", parentID);
                    editor.putString("parent_name", parentName);
                    editor.putString("parent_contact", parentContact);
                    editor.putString("parent_email", parentEmail);
                    editor.putString("parent_password", parentPassword);
                    editor.putString("address", address);
                    editor.putInt("student_id", studentID);

                    Log.d("MyDBHelper", "Parent Login Successful: Parent_ID=" + parentID + ", Student_ID=" + studentID);
                } else if (userType.equals("Admin")) {
                    int adminID = cursor.getInt(0);
                    String adminName = cursor.getString(1);

                    editor.putInt("admin_id", adminID);
                    editor.putString("admin_name", adminName);
                    editor.apply(); // <-- this was missing

                    Log.d("MyDBHelper", "Admin Login Successful:");
                    Log.d("MyDBHelper", "Admin_ID: " + adminID);
                    Log.d("MyDBHelper", "Admin_Name: " + adminName);
                }

                editor.apply();
                return true;
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Login error: " + e.getMessage());
        }

        return false;
    }

    // credentails based on Parent
    public boolean checkParentLogin(String email, String password, Context context) {
        try (SQLiteDatabase db = openDatabase()) {
            if (db == null) {
                Log.e("MyDBHelper", "Database is null.");
                return false;
            }
            // Query the Parents table for matching credentials
            try (Cursor cursor = db.rawQuery(
                    "SELECT Parent_ID, parent_name, parent_contact, parent_email, parent_password, address FROM Parents WHERE parent_email = ? AND parent_password = ?",
                    new String[]{email, password})) {

                if (cursor.moveToFirst()) {
                    int parentID = cursor.getInt(0);
                    String parentName = cursor.getString(1);
                    String parentContact = cursor.getString(2);
                    String parentEmail = cursor.getString(3);
                    String parentPassword = cursor.getString(4);
                    String address = cursor.getString(5);

//                    Fetch Student_ID linked to Parent_ID
                    int studentID = -1;
                    try (Cursor studentCursor = db.rawQuery(
                            "SELECT Student_ID FROM Student WHERE Parent_ID = ?",
                            new String[]{String.valueOf(parentID)})){
                        if (studentCursor.moveToFirst()) {
                            studentID = studentCursor.getInt(0);
                        }
                    }
                    SharedPreferences prefs = context.getSharedPreferences("ParentPortalPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("parent_id", parentID);
                    editor.putString("parent_name", parentName);
                    editor.putString("parent_contact", parentContact);
                    editor.putString("parent_email", parentEmail);
                    editor.putString("parent_password", parentPassword);
                    editor.putString("address", address);
                    editor.putInt("student_id", studentID);
                    editor.apply();

                    Log.d("MyDBHelper", "Login successful. Parent_ID: " + parentID + ", Student_ID: " + studentID);
                }

                Log.d("MyDBHelper", "Checking login for email: " + email);
                Log.d("MyDBHelper", "Cursor count: " + cursor.getCount());

                return cursor.getCount() > 0; // Return true if a match is found
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error checking login: " + e.getMessage());
            return false;
        }
    }
// GET STORED Parent_ID & Student_ID
    public int[] getStoredIDs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ParentPortalPrefs", Context.MODE_PRIVATE);
        int parentID = prefs.getInt("parent_id", -1);
        int studentID = prefs.getInt("student_id", -1);
        return new int[]{parentID, studentID};
    }

//  FETCH STUDENT DETAILS BASED ON STORED IDs
    public String[] getStudentDetails(Context context) {
        SQLiteDatabase db = getReadableDatabase();
        String[] studentDetails = new String[]{"", "", "", "", "", ""}; // Default empty values

        if (db == null) {
            Log.e("MyDBHelper", "Database is null.");
            return studentDetails;
        }

//        Get stored Parent_ID & Student_ID
        int[] storedIDs = getStoredIDs(context);
        int parentID = storedIDs[0];
        int studentID = storedIDs[1];

        if (parentID == -1 || studentID == -1) {
            Log.e("MyDBHelper", "Parent_ID or Student_ID not found in SharedPreferences.");
            return studentDetails;
        }

        Cursor cursor = null;
        try {
            // studentDetails
            cursor = db.rawQuery(
                    "SELECT Student_name, Student_class, Student_DOB, Student_section, ProfilePicture, Student_Rollno FROM Student WHERE Student_ID = ? AND Parent_ID = ?",
                    new String[]{String.valueOf(studentID), String.valueOf(parentID)}
            );

            if (cursor.moveToFirst()) {
                studentDetails[0] = cursor.getString(0); // Student_name
                studentDetails[1] = cursor.getString(1); // Student_class (old code line: studentDetails[1] = convertClassToWords(cursor.getShort(1));)
                studentDetails[2] = cursor.getString(2); // Student_DOB
                studentDetails[3] = cursor.getString(3); // Student_section
                studentDetails[4] = cursor.getString(4); // ProfilePicture (Image URL or Base64)
                studentDetails[5] = cursor.getString(5); // Rollno
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error retrieving student details: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        //dont close database here
        }

        return studentDetails; // Returns Student details
    }
//Retrieve parent details from shared prefs
    public String[] getStoredParentDetails(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ParentPortalPrefs", Context.MODE_PRIVATE);

        return new String[]{
                prefs.getString("parent_name", ""),
                prefs.getString("parent_contact", ""),
                prefs.getString("parent_email", ""),
                prefs.getString("parent_password", ""),
                prefs.getString("address", ""),
        };
    }

//    FETCH TRANSPORT DETAILS
    public String[] getTransportDetails(Context context) {
    SQLiteDatabase db = this.getReadableDatabase();
    String[] transportData = new String[]{"", "", "", "", ""}; // Default empty values

    if (db == null) {
        Log.e("MyDBHelper", "Database is null");
        return transportData;
    }

    int[] storedIDs = getStoredIDs(context);
    int studentID = storedIDs[1];

    if (studentID == -1) {
        Log.e("MyDBHelper", "Student_ID not found in SharedPreferences.");
        return transportData;
    }

    Cursor cursor = null;
    try {
        // Corrected query with proper closing bracket
        cursor = db.rawQuery("SELECT BusNumber, DriverName, DriverContact, PickupPoint, DropPoint FROM Transport WHERE Student_ID = ?",
                new String[]{String.valueOf(studentID)});

        if (cursor.moveToFirst()) {
            transportData[0] = cursor.getString(0); // BusNumber
            transportData[1] = cursor.getString(1); // DriverName
            transportData[2] = cursor.getString(2); // DriverContact
            transportData[3] = cursor.getString(3); // PickupPoint
            transportData[4] = cursor.getString(4); // DropPoint
        }
    } catch (Exception e) {
        Log.e("MyDBHelper", "Error retrieving Transport details: " + e.getMessage());
    } finally {
        if (cursor != null) cursor.close();
        db.close();
    }

    return transportData;
}

    public List<AttendanceRecordItem> getAttendanceForMonth(String monthYear, int studentID) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<AttendanceRecordItem> attendanceList = new ArrayList<>();

        // Convert monthYear (yyyy-mm) into the correct date range
        String[] monthYearParts = monthYear.split("-");
        String startDate = monthYearParts[0] + "-" + monthYearParts[1] + "-01";
        String endDate = monthYearParts[0] + "-" + monthYearParts[1] + "-31"; // Just an example, should use actual month end

        // Query to get attendance data for the selected month and student
        Cursor cursor = db.rawQuery(
                "SELECT Date, Status FROM Attendance WHERE Student_ID = ? AND Date BETWEEN ? AND ?",
                new String[]{String.valueOf(studentID), startDate, endDate}
        );

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("Date"));
                String status = cursor.getString(cursor.getColumnIndex("Status"));
                String day = getDayOfWeek(date); // Get day of the week from date
                attendanceList.add(new AttendanceRecordItem(date, day, status));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return attendanceList;
    }

    // Helper method to get the day of the week from a date string
    private String getDayOfWeek(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date parsedDate = sdf.parse(date);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault()); // Day name format
            return dayFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isAttendanceAlreadyMarked(int studentId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT 1 FROM Attendance WHERE Student_ID = ? AND Date = ?",
                new String[]{String.valueOf(studentId), date}
        );

        boolean exists = cursor.moveToFirst(); // true if a record is found
        cursor.close();
        return exists;
    }

    // Get the distinct month-year combinations (sorted in ascending order)
    public List<String> getDistinctMonthsAndYears(Context context) {
        List<String> months = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        // Fetch stored Student_ID from SharedPreferences
        int[] storedIDs = getStoredIDs(context);
        int studentID = storedIDs[1];  // Student ID is the second element

        if (studentID == -1) {
            Log.e("MyDBHelper", "Student_ID not found in SharedPreferences.");
            return months;  // Return an empty list if Student_ID is not found
        }

        try {
            // Query to get distinct months and years from the Date column for the given student ID
            cursor = db.rawQuery("SELECT DISTINCT strftime('%Y-%m', Date) AS MonthYear FROM Attendance WHERE Student_ID = ? ORDER BY MonthYear ASC", new String[]{String.valueOf(studentID)});

            while (cursor.moveToNext()) {
                String monthYear = cursor.getString(0); // Format: yyyy-MM
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
                try {
                    months.add(outputFormat.format(inputFormat.parse(monthYear)));
                } catch (ParseException e) {
                    Log.e("MyDBHelper", "Error parsing month-year: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error retrieving distinct months and years: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return months;  // Return the list of distinct months and years
    }
    // Get count of attendance statuses (present, absent, leave) for a specific month-year
    public int[] getAttendanceCountsForMonth(String monthYear, Context context) {
        int[] counts = new int[3]; // counts[0] = present, counts[1] = absent, counts[2] = leave
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        // Fetch stored Student_ID from SharedPreferences
        int[] storedIDs = getStoredIDs(context);
        int studentID = storedIDs[1];  // Student ID is the second element

        if (studentID == -1) {
            Log.e("MyDBHelper", "Student_ID not found in SharedPreferences.");
            return counts;  // Return empty counts array if Student_ID is not found
        }

        try {
            // Query to get attendance data for the given student ID and selected month-year
            String query = "SELECT Status FROM Attendance WHERE Student_ID = ? AND strftime('%Y-%m', Date) = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(studentID), monthYear});

            if (cursor.moveToFirst()) {
                do {
                    String status = cursor.getString(0).trim().toLowerCase();
                    if (status.equals("present")) {
                        counts[0]++;
                    } else if (status.equals("absent")) {
                        counts[1]++;
                    } else if (status.equals("leave")) {
                        counts[2]++;
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error counting attendance statuses: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return counts;  // Return counts array for present, absent, and leave
    }

    // Fetch Fee details for a student based on studentID and get Month separately from DueDate
    public List<Map<String, String>> getFeeDetailsWithMonth(Context context) {
        List<Map<String, String>> feeData = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // Fetch stored Student_ID from SharedPreferences
        int[] storedIDs = getStoredIDs(context);
        int studentID = storedIDs[1];  // Student ID is the second element

        if (studentID == -1) {
            Log.e("MyDBHelper", "Student_ID not found in SharedPreferences.");
            return feeData;  // Return an empty list if Student_ID is not found
        }

        Cursor cursor = null;
        try {
            // Query to fetch Fee details for the given student ID
            cursor = db.rawQuery("SELECT FeeType, Amount, DueDate, PaidDate, Status FROM FeeDetail WHERE Student_ID = ?",
                    new String[]{String.valueOf(studentID)});

            if (cursor.moveToFirst()) {
                do {
                    // Fetch details from the cursor
                    String feeType = cursor.getString(0);  // FeeType
                    String amount = cursor.getString(1);    // Amount
                    String dueDate = cursor.getString(2);  // DueDate (assumed to be in 'yyyy-MM-dd' format)
                    String paidDate = cursor.getString(3); // PaidDate (if available)
                    String status = cursor.getString(4);   // Status (e.g., Paid, Pending)

                    // Convert DueDate to month
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                    String month = "";
                    try {
                        Date date = inputFormat.parse(dueDate);
                        month = outputFormat.format(date); // Get month from DueDate
                    } catch (ParseException e) {
                        Log.e("MyDBHelper", "Error parsing DueDate: " + e.getMessage());
                    }

                    // Create a map to hold the fee details and month
                    Map<String, String> feeDetail = new HashMap<>();
                    feeDetail.put("FeeType", feeType);
                    feeDetail.put("Amount", String.valueOf(amount));
                    feeDetail.put("DueDate", dueDate);  // Keep original DueDate
                    feeDetail.put("PaidDate", paidDate != null ? paidDate : "Not Paid");
                    feeDetail.put("Status", status);
                    feeDetail.put("Month", month); // Store the extracted month separately

                    // Add the map to the list
                    feeData.add(feeDetail);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error retrieving Fee details: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        Log.d("MyDBHelper", "Total Fee Details retrieved: " + feeData.size());
        return feeData; // Return the list of fee details
    }
    // ResultCard
    public List<ReportCardItem> getReportCard(Context context, String selectedTerm) {
        List<ReportCardItem> reportCardRecords = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        // Fetch stored Student_ID from SharedPreferences
        int[] storedIDs = getStoredIDs(context);
        int studentID = storedIDs[1];

        if (studentID == -1) {
            Log.e("MyDBHelper", "Student_ID not found in SharedPreferences.");
            return reportCardRecords;  // Return an empty list if Student_ID is not found
        }

        try {
            cursor = db.rawQuery("SELECT Term, Marks FROM ReportCard WHERE Student_ID = ?",
                    new String[]{String.valueOf(studentID)});

            if (cursor.moveToFirst()) {
                    // Fetch Term and Marks
                do {
                    String term = cursor.getString(0);  // Term
                    String marksJson = cursor.getString(1); // Marks (JSON string)

                    if (!term.equals(selectedTerm)) continue;

                    try {
                        JSONObject marksObject = new JSONObject(marksJson); // Convert Marks JSON string to JSONObject

                        // Iterate through each subject in the Marks JSON
                        for (Iterator<String> it = marksObject.keys(); it.hasNext(); ) {
                            String subject = it.next();
                            JSONObject subjectData = marksObject.getJSONObject(subject);
                            int marksObtained = subjectData.getInt("MarksObtained");
                            int maxMarks = subjectData.getInt("MaxMarks");

                            double percentage = ((double) marksObtained / maxMarks) * 100;
                            String grade = getGrade(percentage);

                            reportCardRecords.add(new ReportCardItem(term, subject, maxMarks, marksObtained, grade));
                        }
                    } catch (JSONException e) {
                        Log.e("MyDBHelper", "Error parsing Marks JSON: " + e.getMessage());
                    }

                } while (cursor.moveToNext());
            }
        } catch(Exception e){
            Log.e("MyDBHelper", "Error retrieving Report Card Records: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return reportCardRecords;
    }
    public List<String> getTermsList(Context context) {
        List<String> termsList = new ArrayList<>();

        // Fetch stored Student_ID from SharedPreferences
        int[] storedIDs = getStoredIDs(context);
        int studentID = storedIDs[1];  // Student ID is the second element

        if (studentID == -1) {
            Log.e("MyDBHelper", "Student_ID not found in SharedPreferences.");
            return termsList;  // Return an empty list if Student_ID is not found
        }

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT DISTINCT Term FROM ReportCard WHERE Student_ID = ?",
                    new String[]{String.valueOf(studentID)});

            if (cursor.moveToFirst()) {
                do {
                    String term = cursor.getString(0);
                    termsList.add(term);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error retrieving Terms List: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return termsList;  // Return the list of terms
    }

    // Fetch Syllabus Data from database based on Student Class
    public List<Map<String, Object>> getSyllabusData(Context context) {
        List<Map<String, Object>> syllabusList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        if (db == null) {
            Log.e("MyDBHelper", "Database is null.");
            return syllabusList;
        }

        int[] storedIDs = getStoredIDs(context);
        int studentID = storedIDs[1]; // Get Student ID

        if (studentID == -1) {
            Log.e("MyDBHelper", "Student ID not found in SharedPreferences.");
            return syllabusList;
        }

        // Get fresh Student Class from DB
        String studentClass = null;
        Cursor studentCursor = null;
        try {
            studentCursor = db.rawQuery("SELECT Student_class FROM Student WHERE Student_ID = ?",
                    new String[]{String.valueOf(studentID)});
            if (studentCursor.moveToFirst()) {
                studentClass = studentCursor.getString(0);
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error fetching student class: " + e.getMessage());
        } finally {
            if (studentCursor != null) studentCursor.close();
        }

        if (studentClass == null || studentClass.isEmpty()) {
            Log.e("MyDBHelper", "Student class not found.");
            return syllabusList;
        }

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT Subject, Topic FROM Syllabus WHERE Student_class = ?",
                    new String[]{studentClass});

            Map<String, List<String>> syllabusMap = new HashMap<>();

            if (cursor.moveToFirst()) {
                do {
                    String subject = cursor.getString(0);
                    String topic = cursor.getString(1);

                    if (!syllabusMap.containsKey(subject)) {
                        syllabusMap.put(subject, new ArrayList<>());
                    }
                    syllabusMap.get(subject).add(topic);
                } while (cursor.moveToNext());
            }

            for (Map.Entry<String, List<String>> entry : syllabusMap.entrySet()) {
                Map<String, Object> subjectMap = new HashMap<>();
                subjectMap.put("subject", entry.getKey());

                List<Map<String, String>> topicList = new ArrayList<>();
                for (String topic : entry.getValue()) {
                    Map<String, String> topicMap = new HashMap<>();
                    topicMap.put("topics", topic);
                    topicList.add(topicMap);
                }
                subjectMap.put("topic_list", topicList);
                syllabusList.add(subjectMap);
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error retrieving syllabus: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return syllabusList;
    }

    // Fetch DailyDiary Data
    public List<Map<String, String>> getDailyDiaryEntries(Context context) {
        List<Map<String, String>> diaryEntries = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = getReadableDatabase();

            if (db == null) {
                Log.e("MyDBHelper", "Database is null");
                return diaryEntries;
            }

            // Get Student_Class from stored student details
            String[] studentDetails = getStudentDetails(context);
            String studentClass = studentDetails[1]; // index 1 holds Student_Class

            if (studentClass == null || studentClass.isEmpty()) {
                Log.e("MyDBHelper", "Student_Class not found in SharedPreferences or DB.");
                return diaryEntries;
            }

            cursor = db.rawQuery(
                    "SELECT Date, Classwork, Homework, Assignment FROM DailyDiary WHERE Student_Class = ? ORDER BY Date DESC",
                    new String[]{studentClass}
            );

            if (cursor.moveToFirst()) {
                do {
                    Map<String, String> diaryEntry = new HashMap<>();
                    diaryEntry.put("Date", cursor.getString(0));
                    diaryEntry.put("Classwork", cursor.getString(1));
                    diaryEntry.put("Homework", cursor.getString(2));
                    diaryEntry.put("Assignment", cursor.getString(3));
                    diaryEntries.add(diaryEntry);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("MyDBHelper", "Error retrieving Daily Diary: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return diaryEntries;
    }

    // Fetch TimeTable
    public List<Map<String, String>> getTimeTable(Context context) {
        List<Map<String, String>> timeTableList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        if (db == null) {
            Log.e("MyDBHelper", "Database is null.");
            return timeTableList;
        }

        int[] storedIDs = getStoredIDs(context);
        int studentID = storedIDs[1]; // Get Student ID

        if (studentID == -1) {
            Log.e("MyDBHelper", "Student ID not found in SharedPreferences.");
            return timeTableList;
        }

        // Get fresh Student Class from DB
        String studentClass = null;
        Cursor studentCursor = null;
        try {
            studentCursor = db.rawQuery("SELECT Student_Class FROM Student WHERE Student_ID = ?",
                    new String[]{String.valueOf(studentID)});
            if (studentCursor.moveToFirst()) {
                studentClass = studentCursor.getString(0);
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error fetching student class: " + e.getMessage());
        } finally {
            if (studentCursor != null) studentCursor.close();
        }

        if (studentClass == null) {
            Log.e("MyDBHelper", "Student class not found.");
            return timeTableList;
        }

        // Fetch timetable data using the dynamically retrieved Student_Class
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT Day, TimeSlot, Subject, Teacher FROM TimeTable WHERE Student_Class = ?",
                    new String[]{studentClass});

            if (cursor.moveToFirst()) {
                do {
                    Map<String, String> timeTableItem = new HashMap<>();
                    timeTableItem.put("Day", cursor.getString(0));       // Correctly mapped to "Day"
                    timeTableItem.put("TimeSlot", cursor.getString(1));  // Correctly mapped to "TimeSlot"
                    timeTableItem.put("Subject", cursor.getString(2));   // Correctly mapped to "Subject"
                    timeTableItem.put("Teacher", cursor.getString(3));   // Correctly mapped to "Teacher"

                    timeTableList.add(timeTableItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error fetching timetable: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return timeTableList;
    }



//for addTransport admin side
// Fetch distinct class names from Student table
public List<String> getAllClasses() {
    List<String> classes = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT DISTINCT Student_class FROM Student", null);
    while (cursor.moveToNext()) {
        classes.add(cursor.getString(0));
    }
    cursor.close();
    return classes;
}

    // Fetch student names, Roll no for a given class
    public List<String> getStudentsByClass(String className) {
        List<String> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Correct column names in the query
        Cursor cursor = db.rawQuery("SELECT Student_name, Student_Rollno FROM Student WHERE Student_class = ?", new String[]{className});

        // Check if cursor is valid and has data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Student_name"));
                String rollNo = cursor.getString(cursor.getColumnIndexOrThrow("Student_Rollno"));
                students.add(name);
                students.add(rollNo);
            } while (cursor.moveToNext());
        } else {
            Log.e("DB Error", "Cursor empty or columns missing.");
        }

        if (cursor != null) {
            cursor.close();
        }

        return students;
    }
    // Get Student_ID using class and student name
    public int getStudentId(String studentName, String className) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Student_ID FROM Student WHERE Student_name = ? AND Student_class = ?", new String[]{studentName, className});
        int studentId = -1;
        if (cursor.moveToFirst()) {
            studentId = cursor.getInt(0);
        }
        cursor.close();
        return studentId;
    }

    // Fetch only pending leaves
    public List<LeaveStatusItem> getLeaves() {
        List<LeaveStatusItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Leave_ID, Student_name, Student_class FROM LeaveApplications " +
                "JOIN Student ON LeaveApplications.StudentID = Student.Student_ID WHERE Status = 'Pending'", null);

        if (cursor.moveToFirst()) {
            do {
                int leaveId = cursor.getInt(cursor.getColumnIndexOrThrow("Leave_ID"));
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow("Student_name"));
                String studentClass = cursor.getString(cursor.getColumnIndexOrThrow("Student_class"));

                list.add(new LeaveStatusItem(leaveId, studentName, studentClass));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("DBHelper", "Fetched " + list.size() + " leave records.");
        return list;
    }

// Fetch leave details directly without model class
    public Bundle getLeaveDetailsById(int leaveId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LeaveApplications WHERE Leave_ID = ?", new String[]{String.valueOf(leaveId)});
        Bundle bundle = new Bundle();

        if (cursor.moveToFirst()) {
            bundle.putString("startDate", cursor.getString(cursor.getColumnIndexOrThrow("LeaveStartDate")));
            bundle.putString("endDate", cursor.getString(cursor.getColumnIndexOrThrow("LeaveEndDate")));
            bundle.putString("leaveType", cursor.getString(cursor.getColumnIndexOrThrow("LeaveType")));
            bundle.putString("reason", cursor.getString(cursor.getColumnIndexOrThrow("Reason")));
            bundle.putString("status", cursor.getString(cursor.getColumnIndexOrThrow("Status")));
        }
        cursor.close();
        return bundle;
    }

    public int getStudentIdByRollNo(String rollNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Student_ID FROM Student WHERE Student_Rollno = ?", new String[]{rollNo});
        int studentId = -1;

        // Ensure cursor is not null and contains data
        if (cursor != null && cursor.moveToFirst()) {
            // Get the column index
            int columnIndex = cursor.getColumnIndex("Student_ID");

            // Check if the column exists
            if (columnIndex != -1) {
                studentId = cursor.getInt(columnIndex);
            } else {
                Log.e("DB Error", "Column 'Student_ID' not found.");
            }
        }

        // Always close the cursor to prevent memory leaks
        if (cursor != null) {
            cursor.close();
        }

        return studentId;
    }

    public List<AdminFeedBackItem> getAdminFeedbackItems() {
        List<AdminFeedBackItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT Parents.parent_id, Parents.parent_name, Student.Student_name, Student.Student_class " +
                        "FROM Feedback " +
                        "JOIN Parents ON Feedback.Parent_ID = Parents.parent_id " +
                        "JOIN Student ON Student.Parent_ID = Parents.parent_id",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int parentId = cursor.getInt(cursor.getColumnIndexOrThrow("parent_id"));
                String parentName = cursor.getString(cursor.getColumnIndexOrThrow("parent_name"));
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow("Student_name"));
                String studentClass = cursor.getString(cursor.getColumnIndexOrThrow("Student_class"));

                list.add(new AdminFeedBackItem(parentId, parentName, studentName, studentClass));
            } while (cursor.moveToNext());
        }

        cursor.close();
        Log.d("DBHelper", "Fetched " + list.size() + " admin feedback items (both sender types).");
        return list;
    }


    public boolean checkReportCardExists(int studentId, String term) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ReportCard WHERE Student_ID = ? AND Term = ?",
                new String[]{String.valueOf(studentId), term});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String getReportCardMarksJson(int studentId, String term) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Marks FROM ReportCard WHERE Student_ID = ? AND Term = ?",
                new String[]{String.valueOf(studentId), term});
        String result = null;

        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex("Marks");
            if (index != -1) {
                result = cursor.getString(index);
            }
        }
        cursor.close();
        return result;
    }

    public List<AdminFeedbackNewChatItem> getStudentsByClassDetailed(String className) {
        List<AdminFeedbackNewChatItem> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Student_name, Student_Rollno, Parent_ID FROM Student WHERE Student_class = ?", new String[]{className});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Student_name"));
                String rollNo = cursor.getString(cursor.getColumnIndexOrThrow("Student_Rollno"));
                int parentId = cursor.getInt(cursor.getColumnIndexOrThrow("Parent_ID"));

                students.add(new AdminFeedbackNewChatItem(name, rollNo, parentId));
            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();
        return students;
    }



    public String getGrade(double percentage) {
        if (percentage >= 90) {
            return "A";
        } else if (percentage >= 80) {
            return "B";
        } else if (percentage >= 70) {
            return "C";
        } else if (percentage >= 60) {
            return "D";
        } else {
            return "E";
        }
    }

    private String convertClassToWords(int studentClass){
        switch (studentClass) {
            case 1: return "One";
            case 2: return "Two";
            case 3: return "Three";
            case 4: return "Four";
            case 5: return "Five";
            case 6: return "Six";
            case 7: return "Seven";
            case 8: return "Eight";
            case 9: return "Nine";
            case 10: return "Ten";
            case 11: return "Eleven";
            case 12: return "Twelve";
            default: return "Unknown";
        }
    }


    // Close the database when done
    public void closeDatabase() {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            db.close();
            Log.d("MyDBHelper", "Database closed.");
        }
    }
}
