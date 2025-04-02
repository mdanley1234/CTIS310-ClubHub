package edu.guilford.supabase;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

/**
 * SupabaseDelete handles all delete services of the SB database
 */
public class SupabaseDelete {

    // Reuse the HTTP client and base URL from SupabaseAuth
    private static final HttpClient httpClient = SupabaseAuth.getHttpClient();
    private static final String BASE_URL = SupabaseAuth.getSupabaseUrl() + "/rest/v1/";

    /**
     * Deletes a single record by ID
     *
     * @param table Table name
     * @param search_field Search field
     * @param search_key UUID key to search for in the search_field in the table
     * @return True if delete is successful, false otherwise
     */
    public static boolean deleteById(String table, String search_field, UUID search_key) {
        try {
            // Construct query url
            String url = BASE_URL + table + "?" + search_field + "=eq." + search_key;

            // Build HttpRequest Object
            HttpRequest request = buildAuthorizedRequest(url)
                    .DELETE()
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling
            if (response.statusCode() == 204) {
                System.out.println("Successfully deleted record");
                return true;
            } else {
                System.err.println("Delete failed: " + response.body());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Delete error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes multiple records matching filters
     *
     * @param table Table name
     * @param filters Supabase filter string (e.g., "status=eq.inactive")
     * @return Number of deleted records or -1 if error
     */
    public static int deleteMany(String table, String filters) {
        try {
            // Construct url
            String url = BASE_URL + table + "?" + filters;

            // Build HttpRequest Object
            HttpRequest request = buildAuthorizedRequest(url)
                    .DELETE()
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling
            if (response.statusCode() == 204) {
                // Supabase returns count in Content-Range header
                String contentRange = response.headers().firstValue("Content-Range").orElse("0");
                return Integer.parseInt(contentRange.split("/")[1]);
            } else {
                System.err.println("Delete failed: " + response.body());
                return -1;
            }
        } catch (IOException | InterruptedException | NumberFormatException e) {
            System.err.println("Delete error: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Creates a base authorized request builder
     */
    private static HttpRequest.Builder buildAuthorizedRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseAuth.getSupabaseApiKey())
                .header("Authorization", "Bearer " + SupabaseAuth.getAuthToken())
                .header("Content-Type", "application/json");
    }

    /**
     * Fluent builder for complex delete operations
     */
    public static class Builder {

        private final StringBuilder url;
        private boolean firstParam = true;

        public Builder(String table) {
            this.url = new StringBuilder(BASE_URL).append(table);
        }

        public Builder eq(String column, Object value) {
            addParam(column + "=eq." + value);
            return this;
        }

        public Builder gt(String column, Object value) {
            addParam(column + "=gt." + value);
            return this;
        }

        public Builder lt(String column, Object value) {
            addParam(column + "=lt." + value);
            return this;
        }

        private void addParam(String param) {
            url.append(firstParam ? "?" : "&").append(param);
            firstParam = false;
        }

        /**
         * Executes the delete operation
         *
         * @return Number of deleted records or -1 if error
         */
        public int execute() {
            try {
                HttpRequest request = buildAuthorizedRequest(url.toString())
                        .DELETE()
                        .build();

                HttpResponse<String> response = httpClient.send(
                        request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 204) {
                    String contentRange = response.headers().firstValue("Content-Range").orElse("0");
                    return Integer.parseInt(contentRange.split("/")[1]);
                } else {
                    System.err.println("Delete failed: " + response.body());
                    return -1;
                }
            } catch (Exception e) {
                System.err.println("Delete error: " + e.getMessage());
                return -1;
            }
        }
    }
}
