package edu.guilford.supabase;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * SupabaseInsert handles all insert services of the SB database
 */
public class SupabaseInsert {

    // Reuse the HTTP client and base URL from SupabaseAuth
    private static final HttpClient httpClient = SupabaseAuth.getHttpClient();
    private static final String BASE_URL = SupabaseAuth.getSupabaseUrl() + "/rest/v1/";

    /**
     * Inserts a single record
     *
     * @param table Table name
     * @param data JSONObject containing data to insert
     * @return Inserted record as JSONObject or null if failed
     */
    public static JSONObject insertOne(String table, JSONObject data) {
        try {
            // Construct insert url
            String url = BASE_URL + table;

            // Build HttpRequest object
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("apikey", SupabaseAuth.getSupabaseApiKey())
                    .header("Authorization", "Bearer " + SupabaseAuth.getAuthToken()) // Proper JWT format
                    .header("Prefer", "return=representation")
                    .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling
            if (response.statusCode() == 201) {
                JSONArray result = new JSONArray(response.body());
                return result.length() > 0 ? result.getJSONObject(0) : null;
            } else {
                System.err.println("Insert failed: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.err.println("Insert error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Inserts multiple records in a single request
     *
     * @param table Table name
     * @param data JSONArray containing objects to insert
     * @return Array of inserted records or null if failed
     */
    public static JSONArray insertMany(String table, JSONArray data) {
        try {
            // Build url
            String url = BASE_URL + table;

            // Build HttpRequest object
            HttpRequest request = buildAuthorizedRequest(url)
                    .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
                    .header("Prefer", "return=representation")
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling
            if (response.statusCode() == 201) {
                return new JSONArray(response.body());
            } else {
                System.err.println("Bulk insert failed: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.err.println("Bulk insert error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Upserts a record (insert or update if exists)
     *
     * @param table Table name
     * @param data JSONObject containing data
     * @param onConflict Column name for conflict resolution
     * @return Upserted record or null if failed
     */
    public static JSONObject upsertOne(String table, JSONObject data, String onConflict) {
        try {
            // Build url
            String url = BASE_URL + table;

            // Build HttpRequest object
            HttpRequest request = buildAuthorizedRequest(url)
                    .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
                    .header("Prefer", "resolution=merge-duplicates")
                    .header("Prefer", "return=representation")
                    .header("Prefer", "on-conflict=" + onConflict)
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling
            if (response.statusCode() == 201) {
                JSONArray result = new JSONArray(response.body());
                return result.length() > 0 ? result.getJSONObject(0) : null;
            } else {
                System.err.println("Upsert failed: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.err.println("Upsert error: " + e.getMessage());
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
     * Fluent builder for insert operations
     */
    public static class Builder {

        private final String table;
        private final JSONObject data = new JSONObject();
        private String onConflictColumn;
        private boolean upsertMode = false;

        public Builder(String table) {
            this.table = table;
        }

        public Builder value(String column, Object value) {
            data.put(column, value);
            return this;
        }

        public Builder onConflict(String column) {
            this.onConflictColumn = column;
            this.upsertMode = true;
            return this;
        }

        /**
         * Executes the insert/upsert operation
         *
         * @return Inserted record or null if failed
         */
        public JSONObject execute() {
            try {
                String url = BASE_URL + table;
                HttpRequest.Builder requestBuilder = buildAuthorizedRequest(url)
                        .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
                        .header("Prefer", "return=representation");

                if (upsertMode && onConflictColumn != null) {
                    requestBuilder
                            .header("Prefer", "resolution=merge-duplicates")
                            .header("Prefer", "on-conflict=" + onConflictColumn);
                }

                HttpResponse<String> response = httpClient.send(
                        requestBuilder.build(), HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 201) {
                    JSONArray result = new JSONArray(response.body());
                    return result.length() > 0 ? result.getJSONObject(0) : null;
                } else {
                    System.err.println("Insert failed: " + response.body());
                    return null;
                }
            } catch (Exception e) {
                System.err.println("Insert error: " + e.getMessage());
                return null;
            }
        }
    }
}
