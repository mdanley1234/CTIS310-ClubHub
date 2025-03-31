// package edu.guilford.data;

// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.util.Date;
// import java.util.UUID;

// import org.json.JSONArray;

// import edu.guilford.supabase.SupabaseAuth;

// /**
//  * A ContentBundle contains a list of ContentPacket objects
//  * that each correspond to a content panel. 
//  */
// public class ContentBundle {

//     /**
//      * Each membership_bundle in SupaBase contains a UserRole enum that indicates
//      * the user's permission level for a specific club_id (all lowercase in SB)
//      */
//     private enum UserRole {
//         MEMBER,
//         PARENT,
//         LEADERSHIP,
//         ADVISOR,
//         ADMIN
//     }
    
//     // ContentBundle SB Attributes
//     private UUID bundle_id; // Bundle_id of the content bundle
//     private UUID club_id; // Club_id of the content bundle
//     // Note: user_id and authToken pulled statically from SupabaseAuthClient.java

//     // ContentBundle Data Attributes
//     private UserRole userRole; // User permission level
//     private String membershipStatus; // "active", "inactive", "suspended" etc.
//     private Date expirationDate; // Membership expiration date (optional)


//     public ContentBundle(UUID club_id) throws BundleNotFoundException {
//         this.club_id = club_id;
//         fetchBundleID();
//     }

//     // Fetch the BundleID for future server calls
//     private void fetchBundleID() throws BundleNotFoundException {
//         try {
//             // Construct Supabase API request URL
//             String url = SupabaseAuth.getSUPABASE_URL()
//             + "/bundles?user_id=eq." 
//             + SupabaseAuth.getUserID() 
//             + "&club_id=eq." + club_id 
//             + "&select=bundle_id";

//             // Send bundle request via HTTP
//             HttpClient client = HttpClient.newHttpClient();
//             HttpRequest request = HttpRequest.newBuilder()
//                 .uri(URI.create(url))
//                 .header("Authorization", "Bearer " + SupabaseAuth.getAuthToken()) // Pass user authentication token
//                 .header("Content-Type", "application/json")
//                 .GET()
//                 .build();

//             HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//             if (response.statusCode() == 200) {
//                 JSONArray jsonResponse = new JSONArray(response.body());
//                 if (jsonResponse.length() > 0) {
//                     this.bundle_id = jsonResponse.getJSONObject(0).getInt("bundle_id");
//                     System.out.println("Fetched bundle_id: " + bundle_id);
//                 } else {
//                     throw new BundleNotFoundException("No bundle found for user_id: " + user_id + " and club_id: " + club_id);
//                 }
//             } else {
//                 throw new RuntimeException("Failed to fetch bundle: " + response.body());
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new BundleNotFoundException("Error fetching bundle for user_id: " + user_id + " and club_id: " + club_id);
//         }
//     }














    
//     // Custom exception class
//     public static class BundleNotFoundException extends Exception {
//         public BundleNotFoundException(String message) {
//             super(message);
//         }
//     }
// }
