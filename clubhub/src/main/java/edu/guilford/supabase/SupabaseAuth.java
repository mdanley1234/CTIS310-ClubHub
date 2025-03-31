package edu.guilford.supabase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.json.JSONObject;

public class SupabaseAuth {

    private static final String SUPABASE_URL = "https://rzmoxaovvmsdjzryclow.supabase.co";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ6bW94YW92dm1zZGp6cnljbG93Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDI0MDM4OTIsImV4cCI6MjA1Nzk3OTg5Mn0.qcOPIhMFIjHqmcNR03Vqk2rFMBzfJQKEcUX4Pfip8bQ";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static UUID userId = null;
    private static String authToken = null;

    // Getters
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
     * Logs in a user with email and password
     *
     * @return true if login successful, false otherwise
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
                authToken = jsonResponse.getString("access_token");

                try {
                    String userIdString = jsonResponse.getJSONObject("user").getString("id");
                    userId = UUID.fromString(userIdString);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid UUID format: " + e.getMessage());
                    return false;
                }

                System.out.println("Login successful! User ID: " + userId);
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

    /**
     * Logs out the current user
     */
    public static void logout() {
        authToken = null;
        userId = null;
        System.out.println("Logged out successfully");
    }

    /**
     * Checks if the current authToken is valid
     *
     * @return true if token is valid, false otherwise
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
            System.err.println("Token validation error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Attempts to refresh the authentication token
     *
     * @return true if refresh successful, false otherwise
     */
    public static boolean refreshToken() {
        if (authToken == null) {
            return false;
        }

        try {
            String refreshUrl = SUPABASE_URL + "/auth/v1/token?grant_type=refresh_token";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(refreshUrl))
                    .header("apikey", SUPABASE_API_KEY)
                    .header("Authorization", "Bearer " + authToken)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                authToken = new JSONObject(response.body()).getString("access_token");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Token refresh error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sends a password reset email to the specified address
     *
     * @return true if request was successful, false otherwise
     */
    public static boolean sendPasswordResetEmail(String email) {
        try {
            String resetUrl = SUPABASE_URL + "/auth/v1/recover";
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(resetUrl))
                    .header("Content-Type", "application/json")
                    .header("apikey", SUPABASE_API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            System.err.println("Password reset error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Signup Method
     * @param email
     * @param password
     * @param fullName
     * @param classYear
     * @param phoneNumber
     * @return
     */
    public static JSONObject signUp(String email, String password, String fullName, int classYear, String phoneNumber /* other fields */) {
        try {
            JSONObject metadata = new JSONObject();
            metadata.put("full_name", fullName);
            metadata.put("class_year", classYear);
            metadata.put("phone_number", phoneNumber);
            // Add other fields as needed

            JSONObject options = new JSONObject();
            options.put("data", metadata);
            options.put("email_confirm", false); // Disable for testing

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

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return new JSONObject(response.body());
            }
            // Error handling...
        } catch (Exception e) {
            // Exception handling...
        }
        return null;
    }

}
