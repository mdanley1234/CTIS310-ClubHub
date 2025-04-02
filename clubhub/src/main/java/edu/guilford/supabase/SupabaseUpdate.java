package edu.guilford.supabase;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * SupabaseUpdate handles all update services of the SB database
 */
public class SupabaseUpdate {

    // Reuse the HTTP client and base URL from SupabaseAuth
    private static final HttpClient httpClient = SupabaseAuth.getHttpClient();
    private static final String BASE_URL = SupabaseAuth.getSupabaseUrl() + "/rest/v1/";

    /**
     * Updates a single record by ID
     *
     * @param table Table name
     * @param search_field Search field
     * @param search_key UUID key to search for in the search_field in the table
     * @param data JSONObject containing fields to update
     * @return Updated record as JSONObject or null if failed
     */
    public static JSONObject updateById(String table, String search_field, UUID search_key, JSONObject data) {
        try {
            // Construct update url
            String url = BASE_URL + table + "?" + search_field + "=eq." + search_key;

            // Build HttpRequest object
            HttpRequest request = buildAuthorizedRequest(url)
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(data.toString()))
                    .header("Prefer", "return=representation") // Return the updated record
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling
            if (response.statusCode() == 200) {
                JSONArray result = new JSONArray(response.body());
                return result.length() > 0 ? result.getJSONObject(0) : null;
            } else {
                System.err.println("Update failed: " + response.body());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Update error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates multiple records matching filters
     *
     * @param table Table name
     * @param filters Supabase filter string (e.g., "status=eq.active")
     * @param data JSONObject containing fields to update
     * @return Array of updated records or null if failed
     */
    public static JSONArray updateMany(String table, String filters, JSONObject data) {
        try {
            // Construct url
            String url = BASE_URL + table + "?" + filters;

            // Build HttpRequest object
            HttpRequest request = buildAuthorizedRequest(url)
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(data.toString()))
                    .header("Prefer", "return=representation")
                    .build();
            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling 
            if (response.statusCode() == 200) {
                return new JSONArray(response.body());
            } else {
                System.err.println("Bulk update failed: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.err.println("Bulk update error: " + e.getMessage());
            return null;
        }
    }

    private static HttpRequest.Builder buildAuthorizedRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseAuth.getSupabaseApiKey())
                .header("Authorization", "Bearer " + SupabaseAuth.getAuthToken())
                .header("Content-Type", "application/json");
    }

    /**
     * Fluent builder for update operations
     */
    public static class Builder {

        private final String table;
        private final JSONObject data = new JSONObject();
        private final StringBuilder filters = new StringBuilder();
        private boolean firstFilter = true;

        public Builder(String table) {
            this.table = table;
        }

        public Builder set(String column, Object value) {
            data.put(column, value);
            return this;
        }

        public Builder whereEq(String column, Object value) {
            addFilter(column + "=eq." + value);
            return this;
        }

        public Builder whereGt(String column, Object value) {
            addFilter(column + "=gt." + value);
            return this;
        }

        private void addFilter(String filter) {
            filters.append(firstFilter ? "" : "&").append(filter);
            firstFilter = false;
        }

        /**
         * Executes the update operation
         *
         * @return Updated records or null if failed
         */
        public JSONArray execute() {
            try {
                String url = BASE_URL + table + "?" + filters.toString();

                HttpRequest request = buildAuthorizedRequest(url)
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(data.toString()))
                        .header("Prefer", "return=representation")
                        .build();

                HttpResponse<String> response = httpClient.send(
                        request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    return new JSONArray(response.body());
                } else {
                    System.err.println("Update failed: " + response.body());
                    return null;
                }
            } catch (Exception e) {
                System.err.println("Update error: " + e.getMessage());
                return null;
            }
        }
    }
}
