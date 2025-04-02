package edu.guilford.supabase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class SupabaseUpdate {
    private static final HttpClient httpClient = SupabaseAuth.getHttpClient();
    private static final String BASE_URL = SupabaseAuth.getSupabaseUrl() + "/rest/v1/";

    /**
     * Updates a single record by ID
     * @param table Table name
     * @param id Record ID to update
     * @param data JSONObject containing fields to update
     * @return Updated record as JSONObject or null if failed
     */
    public static JSONObject updateById(String table, UUID id, JSONObject data) {
        try {
            String url = BASE_URL + table + "?id=eq." + id;
            
            HttpRequest request = buildAuthorizedRequest(url)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(data.toString()))
                .header("Prefer", "return=representation") // Return the updated record
                .build();

            HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

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
     * @param table Table name
     * @param filters Supabase filter string (e.g., "status=eq.active")
     * @param data JSONObject containing fields to update
     * @return Array of updated records or null if failed
     */
    public static JSONArray updateMany(String table, String filters, JSONObject data) {
        try {
            String url = BASE_URL + table + "?" + filters;
            
            HttpRequest request = buildAuthorizedRequest(url)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(data.toString()))
                .header("Prefer", "return=representation")
                .build();

            HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return new JSONArray(response.body());
            } else {
                System.err.println("Bulk update failed: " + response.body());
                return null;
            }
        } catch (Exception e) {
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