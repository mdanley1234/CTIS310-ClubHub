package edu.guilford.supabase;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import edu.guilford.data.ProfilePacket;

/**
 * SupabaseAuth handles all authentication services and contains authentication
 * tokens, UUIDs, and "secure" SB URL & API keys
 */
public class SupabaseAuth {

    // SB Constants & Tokens
    private static final String SUPABASE_URL = "https://hjxjfjsiyormcexjkkpv.supabase.co";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhqeGpmanNpeW9ybWNleGpra3B2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDM1MjkxNjMsImV4cCI6MjA1OTEwNTE2M30.U49B6u66GbU4XQsq9JM9hsXgUq8x6Nr8NL8BAIqn8Dg";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static UUID userId = null;          // Authenticated User UUID
    private static String authToken = null;     // Authentication Token

    // SB Constants & Tokens Getters
    public static String getSupabaseUrl() {
        return SUPABASE_URL;
    }

    public static String getSupabaseApiKey() {
        return SUPABASE_API_KEY;
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static UUID getUserId() {
        return userId;
    }

    /**
     * Signs up a new user using profilePacket information and password
     *
     * @param profilePacket Contains all user profile personal information
     * (isComplete() != false)
     * @param password Password for the new user
     * @return True if signUp was successful, false otherwise
     */
    public static boolean signUp(ProfilePacket profilePacket, String password) {

        // Check if all fields in profilePacket are defined
        if (!profilePacket.isComplete()) {
            return false;
        }

        try {
            // Build signUp request JSONObject
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", profilePacket.getEmail());
            requestBody.put("password", password);

            // Build signUp request HttpRequest Object
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SUPABASE_URL + "/auth/v1/signup"))
                    .header("Content-Type", "application/json")
                    .header("apikey", SUPABASE_API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Check for errors and update data
            if (response.statusCode() == 200) {
                JSONObject userData = new JSONObject(response.body());
                authToken = userData.getString("access_token");
                userId = UUID.fromString(userData.getJSONObject("user").getString("id"));

                // Update profile information
                SupabaseUpdate.updateById("profiles", "profile_id", userId, profilePacket.getJSON());

                return true; // SUCCESSFUL SIGNUP
            } else {
                System.err.println("Signup failed (" + response.statusCode() + "): " + response.body());
                return false; // UNSUCCESFUL SIGNUP
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.err.println("Signup error: " + e.getMessage());
            return false; // UNSUCCESFUL SIGNUP
        }
    }

    /**
     * Logins a user using only email from profilePacket and populating
     * profilePacket with information if successful
     *
     * @param profilePacket Profile packet to be popluated containing login
     * email definition
     * @param password User password
     * @return True if login was successful. false if not.
     */
    public static boolean login(ProfilePacket profilePacket, String password) {
        try {
            // Build JSONObject request
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", profilePacket.getEmail());
            requestBody.put("password", password);

            // Build HttpRequest object
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SUPABASE_URL + "/auth/v1/token?grant_type=password"))
                    .header("Content-Type", "application/json")
                    .header("apikey", SUPABASE_API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Fire login request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Response handling 
            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                authToken = jsonResponse.getString("access_token");
                userId = UUID.fromString(jsonResponse.getJSONObject("user").getString("id"));

                // Query profile information for ProfilePacket
                JSONObject data = SupabaseQuery.queryById("profiles", "profile_id", userId);
                profilePacket.setEmail((String) data.get("email"));
                profilePacket.setStudent_id((int) data.get("student_id"));
                profilePacket.setFirst_name((String) data.get("first_name"));
                profilePacket.setLast_name((String) data.get("last_name"));
                profilePacket.setDate_of_birth((String) data.get("date_of_birth"));
                profilePacket.setGraduation_year((int) data.get("graduation_year"));
                profilePacket.setPhone_number((String) data.get("phone_number"));
                profilePacket.setAddress((String) data.get("address"));

                return true;    // SUCCESSFUL LOGIN
            } else {
                System.err.println("Login failed: " + response.body());
                return false;   // UNSUCCESSFUL LOGIN
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.err.println("Login error: " + e.getMessage());
            return false; // UNSUCCESSFUL LOGIN
        }
    }
}
