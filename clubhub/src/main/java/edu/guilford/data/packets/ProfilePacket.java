package edu.guilford.data.packets;

import java.util.UUID;

import edu.guilford.supabase.SupabaseAuth;

/**
 * ProfilePacket is a special DataPacket subclass that adds SBAuth methods
 * Also provides explicit constructors for signup and login
 */
public class ProfilePacket extends DataPacket {

    // Signup Constructor
    public ProfilePacket(String email,
            int student_id,
            String first_name,
            String last_name,
            String date_of_birth,
            int graduation_year,
            String phone_number,
            String address) {
        super("profiles");

        // Required profile information
        this.put("email", email);
        this.put("student_id", student_id);
        this.put("first_name", first_name);
        this.put("last_name", last_name);
        this.put("date_of_birth", date_of_birth);
        this.put("graduation_year", graduation_year);
        this.put("phone_number", phone_number);
        this.put("address", address);
    }

    // Login Constructor
    public ProfilePacket(String email) {
        super("profiles");
        this.put("email", email);
    }

    // profilePacket SB constructor
    public ProfilePacket(UUID id) {
        super("profiles", "profile_id", id);
    }

    // SupabaseAuth - SIGNUP
    public boolean signUp(String password) {
        return SupabaseAuth.signUp(this, password);
    }

    // SupabaseAuth - LOGIN
    public boolean login(String password) {
        return SupabaseAuth.login(this, password);
    }
}
