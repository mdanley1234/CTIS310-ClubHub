// package edu.guilford.supabase;

// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.util.UUID;

// public class SupabaseDelete {
//     private static final HttpClient httpClient = SupabaseAuth.getHttpClient();
//     private static final String BASE_URL = SupabaseAuth.getSupabaseUrl() + "/rest/v1/";

//     private SupabaseDelete() {} // Prevent instantiation

//     /**
//      * Deletes a record by ID
//      * @param table Table name
//      * @param id Record ID to delete
//      * @return true if successful, false otherwise
//      */
//     public static boolean deleteById(String table, UUID id) {
//         try {
//             String url = BASE_URL + table + "?id=eq." + id;
            
//             HttpRequest request = buildAuthorizedRequest(url)
//                 .DELETE()
//                 .build();

//             HttpResponse<String> response = httpClient.send(
//                 request, HttpResponse.BodyHandlers.ofString());

//             if (response.statusCode() == 204) {
//                 System.out.println("Successfully deleted record");
//                 return true;
//             } else {
//                 System.err.println("Delete failed: " + response.body());
//                 return false;
//             }
//         } catch (Exception e) {
//             System.err.println("Delete error: " + e.getMessage());
//             return false;
//         }
//     }

//     /**
//      * Deletes multiple records matching filters
//      * @param table Table name
//      * @param filters Supabase filter string (e.g., "status=eq.inactive")
//      * @return Number of deleted records or -1 if error
//      */
//     public static int deleteMany(String table, String filters) {
//         try {
//             String url = BASE_URL + table + "?" + filters;
            
//             HttpRequest request = buildAuthorizedRequest(url)
//                 .DELETE()
//                 .build();

//             HttpResponse<String> response = httpClient.send(
//                 request, HttpResponse.BodyHandlers.ofString());

//             if (response.statusCode() == 204) {
//                 // Supabase returns count in Content-Range header
//                 String contentRange = response.headers().firstValue("Content-Range").orElse("0");
//                 return Integer.parseInt(contentRange.split("/")[1]);
//             } else {
//                 System.err.println("Delete failed: " + response.body());
//                 return -1;
//             }
//         } catch (Exception e) {
//             System.err.println("Delete error: " + e.getMessage());
//             return -1;
//         }
//     }

//     /**
//      * Creates a base authorized request builder
//      */
//     private static HttpRequest.Builder buildAuthorizedRequest(String url) {
//         return HttpRequest.newBuilder()
//             .uri(URI.create(url))
//             .header("apikey", SupabaseAuth.getSupabaseApiKey())
//             .header("Authorization", "Bearer " + SupabaseAuth.getAuthToken())
//             .header("Content-Type", "application/json");
//     }

//     /**
//      * Fluent builder for complex delete operations
//      */
//     public static class Builder {
//         private final StringBuilder url;
//         private boolean firstParam = true;

//         public Builder(String table) {
//             this.url = new StringBuilder(BASE_URL).append(table);
//         }

//         public Builder eq(String column, Object value) {
//             addParam(column + "=eq." + value);
//             return this;
//         }

//         public Builder gt(String column, Object value) {
//             addParam(column + "=gt." + value);
//             return this;
//         }

//         public Builder lt(String column, Object value) {
//             addParam(column + "=lt." + value);
//             return this;
//         }

//         private void addParam(String param) {
//             url.append(firstParam ? "?" : "&").append(param);
//             firstParam = false;
//         }

//         /**
//          * Executes the delete operation
//          * @return Number of deleted records or -1 if error
//          */
//         public int execute() {
//             try {
//                 HttpRequest request = buildAuthorizedRequest(url.toString())
//                     .DELETE()
//                     .build();

//                 HttpResponse<String> response = httpClient.send(
//                     request, HttpResponse.BodyHandlers.ofString());

//                 if (response.statusCode() == 204) {
//                     String contentRange = response.headers().firstValue("Content-Range").orElse("0");
//                     return Integer.parseInt(contentRange.split("/")[1]);
//                 } else {
//                     System.err.println("Delete failed: " + response.body());
//                     return -1;
//                 }
//             } catch (Exception e) {
//                 System.err.println("Delete error: " + e.getMessage());
//                 return -1;
//             }
//         }
//     }
// }