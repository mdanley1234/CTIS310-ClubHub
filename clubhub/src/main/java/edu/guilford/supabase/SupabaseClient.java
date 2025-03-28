import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class SupabaseClient {
    private static final String SUPABASE_URL = "https://rzmoxaovvmsdjzryclow.supabase.co";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ6bW94YW92dm1zZGp6cnljbG93Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDI0MDM4OTIsImV4cCI6MjA1Nzk3OTg5Mn0.qcOPIhMFIjHqmcNR03Vqk2rFMBzfJQKEcUX4Pfip8bQ"; // Use a secure method to store this
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    
    private String authToken = null;  // Stores authentication token

    /**
     * Logs in a user with email and password
     */
    public boolean login(String email, String password) {
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
                System.out.println("Login successful!");
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
