package edu.guilford.data;

import org.json.JSONObject;

public class ProfilePacket {

    // Profile attributes
    private String email;
    private int student_id;
    private String first_name;
    private String last_name;
    private String date_of_birth;
    private int graduation_year;
    private String phone_number;
    private String address;

    // Full Constructor (Use for SignUp operation only)
    public ProfilePacket(String email,
            int student_id,
            String first_name,
            String last_name,
            String date_of_birth,
            int graduation_year,
            String phone_number,
            String address) {
        this.email = email;
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.graduation_year = graduation_year;
        this.phone_number = phone_number;
        this.address = address;
    }

    // Minimum Constructor (Use for Login operation)
    public ProfilePacket(String email) {
        this.email = email;
    }

    // Get JSONObject representing ProfilePacket with non-null components only
    public JSONObject getJSON() {
        JSONObject json = new JSONObject();
        if (email != null) json.put("email", email);
        if (student_id != 0) json.put("student_id", student_id);
        if (first_name != null) json.put("first_name", first_name);
        if (last_name != null) json.put("last_name", last_name);
        if (date_of_birth != null) json.put("date_of_birth", date_of_birth);
        if (graduation_year != 0) json.put("graduation_year", graduation_year);
        if (phone_number != null) json.put("phone_number", phone_number);
        if (address != null) json.put("address", address);
        return json;
    }

    @Override
    public String toString() {
        return "ProfilePacket{" +
                "email='" + email + '\'' +
                ", student_id=" + student_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", graduation_year=" + graduation_year +
                ", phone_number='" + phone_number + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    /**
     * @return true if all fields are defined, false otherwise
     */
    public boolean isComplete() {
        return email != null &&
            student_id != 0 &&
            first_name != null &&
            last_name != null &&
            date_of_birth != null &&
            graduation_year != 0 &&
            phone_number != null &&
            address != null;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getGraduation_year() {
        return graduation_year;
    }

    public void setGraduation_year(int graduation_year) {
        this.graduation_year = graduation_year;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
