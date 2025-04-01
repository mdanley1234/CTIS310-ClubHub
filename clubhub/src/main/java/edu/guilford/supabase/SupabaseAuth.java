package edu.guilford.supabase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.json.JSONObject;

public class SupabaseAuth {
    private static final String SUPABASE_URL = "https://hjxjfjsiyormcexjkkpv.supabase.co";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhqeGpmanNpeW9ybWNleGpra3B2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDM1MjkxNjMsImV4cCI6MjA1OTEwNTE2M30.U49B6u66GbU4XQsq9JM9hsXgUq8x6Nr8NL8BAIqn8Dg";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static UUID userId = null;
    private static String authToken = null;

    // Getters
    public static String getSupabaseUrl() { return SUPABASE_URL; }
    public static String getSupabaseApiKey() { return SUPABASE_API_KEY; }
    public static String getAuthToken() { return authToken; }
    public static UUID getUserId() { return userId; }

    /**
     * Enhanced signup method with all profile fields
     */
    public static JSONObject signUp(
        String email,
        String password,
        String fullName,
        Integer studentId,
        String classYear,
        String dateOfBirth,
        String phoneNumber,
        String address,
        String emergencyContactName,
        String emergencyContactPhone
    ) {
        try {
            // Build user metadata
            JSONObject metadata = new JSONObject();
            metadata.put("full_name", fullName);
            metadata.put("email", email); // Required for trigger
            if (studentId != null) metadata.put("student_id", studentId);
            if (classYear != null) metadata.put("class_year", classYear);
            if (dateOfBirth != null) metadata.put("date_of_birth", dateOfBirth);
            if (phoneNumber != null) metadata.put("phone_number", phoneNumber);
            if (address != null) metadata.put("address", address);
            if (emergencyContactName != null) metadata.put("emergency_contact_name", emergencyContactName);
            if (emergencyContactPhone != null) metadata.put("emergency_contact_phone", emergencyContactPhone);

            JSONObject options = new JSONObject();
            options.put("data", metadata);
            options.put("email_confirm", false); // Disable email confirmation for testing

            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("options", options);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SUPABASE_URL + "/auth/v1/signup"))
                .header("Content-Type", "application/json")
                .header("apikey", SUPABASE_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

            HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject userData = new JSONObject(response.body());
                authToken = userData.getString("access_token");
                userId = UUID.fromString(userData.getJSONObject("user").getString("id"));
                
                // // Verify profile was created TODO
                // JSONObject profile = SupabaseQuery.getById("profiles", userId);
                // if (profile == null) {
                //     System.err.println("Warning: Profile creation failed");
                // }
                return userData;
            } else {
                System.err.println("Signup failed (" + response.statusCode() + "): " + response.body());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Signup error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Login with email/password
     */
    public static boolean login(String email, String password) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);
            requestBody.put("password", password);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SUPABASE_URL + "/auth/v1/token?grant_type=password"))
                .header("Content-Type", "application/json")
                .header("apikey", SUPABASE_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

            HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                authToken = jsonResponse.getString("access_token");
                userId = UUID.fromString(jsonResponse.getJSONObject("user").getString("id"));
                return true;
            } else {
                System.err.println("Login failed: " + response.body());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
    }

    // /**
    //  * Get the current user's full profile
    //  */
    // public static JSONObject getCurrentUserProfile() {
    //     if (userId == null) return null;
    //     return SupabaseQuery.getById("profiles", userId);
    // }

    // ... (keep your existing logout, isAuthTokenValid, refreshToken, 
    // and sendPasswordResetEmail methods unchanged)
}