package edu.guilford.supabase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.json.JSONObject;

/*
SupabaseAuth.java      // Authentication methods (login, signup, logout)
SupabaseQuery.java     // Read operations (select, filtering)
SupabaseInsert.java    // Create operations
SupabaseUpdate.java    // Update operations
SupabaseDelete.java    // Delete operations
 */

public class SupabaseAuth {
    private static final String SUPABASE_URL = "https://rzmoxaovvmsdjzryclow.supabase.co";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ6bW94YW92dm1zZGp6cnljbG93Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDI0MDM4OTIsImV4cCI6MjA1Nzk3OTg5Mn0.qcOPIhMFIjHqmcNR03Vqk2rFMBzfJQKEcUX4Pfip8bQ"; // Use a secure method to store this
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static UUID user_id = null; // Stores UUID of user
    private static String authToken = null;  // Stores authentication token

    public static String getSUPABASE_URL() {
        return SUPABASE_URL;
    }

    public static String getSUPABASE_API_KEY() {
        return SUPABASE_API_KEY;
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static UUID getUserID() {
        return user_id;
    }

    /**
     * Checks if the current authToken is valid
     */
    public static boolean isAuthTokenValid() {
        if (authToken == null) {
            return false;
        }

        try {
            String url = SUPABASE_URL + "/auth/v1/user";

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SUPABASE_API_KEY)
                .header("Authorization", "Bearer " + authToken)
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs in a user with email and password
     */
    public static boolean login(String email, String password) {
        try {
            String loginUrl = SUPABASE_URL + "/auth/v1/token?grant_type=password";
    
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);
            requestBody.put("password", password);
    
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(loginUrl))
                .header("Content-Type", "application/json")
                .header("apikey", SUPABASE_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
    
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    
            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                authToken = jsonResponse.getString("access_token"); // Store the token
    
                // Extract user_id and convert it to UUID
                String userIdString = jsonResponse.getJSONObject("user").getString("user_id");
                user_id = UUID.fromString(userIdString);
    
                System.out.println("Login successful! User ID: " + userID);
                return true;
            } else {
                System.out.println("Login failed: " + response.body());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


















    // TODO: REMOVE

    /**
     * Fetches user club data if authenticated
     */
    public String fetchUserClubs(String userId) {
        if (authToken == null) {
            System.out.println("User is not authenticated. Please log in.");
            return null;
        }

        try {
            String url = SUPABASE_URL + "/rest/v1/user_club_packets?user_id=eq." + userId
                         + "&select=role,notes,clubs(name,description)";

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SUPABASE_API_KEY)
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();  // Return JSON response
            } else {
                System.out.println("Failed to fetch clubs: " + response.body());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
