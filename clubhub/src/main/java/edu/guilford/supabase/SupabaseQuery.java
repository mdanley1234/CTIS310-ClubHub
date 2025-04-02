package edu.guilford.supabase;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * SupabaseQuery handles all query services of the SB database
 */
public class SupabaseQuery {

    // Reuse the HTTP client and base URL from SupabaseAuth
    private static final HttpClient httpClient = SupabaseAuth.getHttpClient();
    private static final String BASE_URL = SupabaseAuth.getSupabaseUrl() + "/rest/v1/";

    /**
     * Gets a single record by ID
     *
     * @param table Table name
     * @param search_field Search field
     * @param search_key UUID key to search for in the search_field in the table
     * @return Updated record as JSONObject or null if failed
     */
    public static JSONObject queryById(String table, String search_field, UUID search_key) {
        try {
            // Construct query url
            String url = BASE_URL + table + "?" + search_field + "=eq." + search_key;

            // Build HttpRequest object
            HttpRequest request = buildAuthorizedRequest(url)
                    .GET()
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling
            if (response.statusCode() == 200) {
                JSONArray results = new JSONArray(response.body());
                return results.length() > 0 ? results.getJSONObject(0) : null;
            } else {
                System.err.println("Get by ID failed: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.err.println("Get by ID error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Executes a SELECT query with optional filters and select (multiple rows
     * returned)
     *
     * @param table The table name to query
     * @param filters Optional filters (e.g., "name=eq.John")
     * @param select Optional select columns (e.g., "id,name,age")
     * @return JSONArray of results or null if error
     */
    public static JSONArray queryMany(String table, String filters, String select) {
        try {
            // Build url
            String url = buildSelectUrl(table, filters, select);

            // Build HttpRequest object
            HttpRequest request = buildAuthorizedRequest(url)
                    .GET()
                    .build();

            // Fire request and store response
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            // Data handling
            if (response.statusCode() == 200) {
                return new JSONArray(response.body());
            } else {
                System.err.println("Query failed: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.err.println("Query error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Builds a select URL with filters and column selection
     */
    private static String buildSelectUrl(String table, String filters, String select) {
        StringBuilder url = new StringBuilder(BASE_URL).append(table).append("?");

        if (select != null && !select.isEmpty()) {
            url.append("select=").append(URLEncoder.encode(select, StandardCharsets.UTF_8));
        }

        if (filters != null && !filters.isEmpty()) {
            if (select != null && !select.isEmpty()) {
                url.append("&");
            }
            url.append(filters);
        }

        return url.toString();
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
     * URL Builder for more complex queries (fluent interface)
     */
    public static class Builder {

        private final StringBuilder url;
        private boolean firstParam = true;

        public Builder(String table) {
            this.url = new StringBuilder(BASE_URL).append(table);
        }

        public Builder select(String... columns) {
            addParam("select=" + URLEncoder.encode(String.join(",", columns), StandardCharsets.UTF_8));
            return this;
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

        public Builder order(String column, boolean descending) {
            addParam("order=" + column + (descending ? ".desc" : ".asc"));
            return this;
        }

        public Builder limit(int count) {
            addParam("limit=" + count);
            return this;
        }

        public Builder offset(int count) {
            addParam("offset=" + count);
            return this;
        }

        private void addParam(String param) {
            url.append(firstParam ? "?" : "&").append(param);
            firstParam = false;
        }

        public JSONArray execute() {
            try {
                HttpRequest request = buildAuthorizedRequest(url.toString())
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(
                        request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    return new JSONArray(response.body());
                } else {
                    System.err.println("Query failed: " + response.body());
                    return null;
                }
            } catch (Exception e) {
                System.err.println("Query error: " + e.getMessage());
                return null;
            }
        }
    }
}
