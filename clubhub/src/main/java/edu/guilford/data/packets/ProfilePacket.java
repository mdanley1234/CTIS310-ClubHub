package edu.guilford.data.packets;

import java.util.UUID;

import edu.guilford.supabase.SupabaseAuth;

/**
 * {@code ProfilePacket} is a subclass of {@link DataPacket} designed specifically
 * for handling user profile data, including authentication-related operations
 * such as sign-up and login.
 * <p>
 * It provides multiple constructors for creating, retrieving, and authenticating
 * user profiles and uses Supabase as the backend service.
 */
public class ProfilePacket extends DataPacket {

    /**
     * Constructs a new {@code ProfilePacket} for user sign-up.
     *
     * @param email            the user's email address
     * @param student_id       the user's school ID
     * @param first_name       the user's first name
     * @param last_name        the user's last name
     * @param date_of_birth    the user's date of birth
     * @param graduation_year  the user's expected graduation year
     * @param phone_number     the user's phone number
     * @param address          the user's home address
     */
    public ProfilePacket(
            String email,
            int student_id,
            String first_name,
            String last_name,
            String date_of_birth,
            int graduation_year,
            String phone_number,
            String address
    ) {
        super("profiles");

        this.put("email", email);
        this.put("student_id", student_id);
        this.put("first_name", first_name);
        this.put("last_name", last_name);
        this.put("date_of_birth", date_of_birth);
        this.put("graduation_year", graduation_year);
        this.put("phone_number", phone_number);
        this.put("address", address);
    }

    /**
     * Constructs a {@code ProfilePacket} for login using only an email.
     *
     * @param email the user's email
     */
    public ProfilePacket(String email) {
        super("profiles");
        this.put("email", email);
    }

    /**
     * Constructs a {@code ProfilePacket} by retrieving the user profile from Supabase
     * using a UUID primary key.
     *
     * @param id the UUID of the profile (usually profile_id)
     */
    public ProfilePacket(UUID id) {
        super("profiles", "profile_id", id);
    }

    /**
     * Attempts to register this profile in Supabase with the specified password.
     *
     * @param password the user's chosen password
     * @return {@code true} if sign-up succeeded; {@code false} otherwise
     */
    public boolean signUp(String password) {
        return SupabaseAuth.signUp(this, password);
    }

    /**
     * Attempts to log in using the current profile and the provided password.
     *
     * @param password the user's password
     * @return {@code true} if login succeeded; {@code false} otherwise
     */
    public boolean login(String password) {
        return SupabaseAuth.login(this, password);
    }
}
