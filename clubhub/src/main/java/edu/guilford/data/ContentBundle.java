package edu.guilford.data;

import java.util.UUID;

/**
 * A ContentBundle contains a list of ContentPacket objects
 * that each correspond to a content panel. 
 */
public class ContentBundle {

    /**
     * The UserRole enum regulates which ContentPackets are fetched for a
     * specific user_id and club_id. Eve
     */
    private enum UserRole {
        MEMBER,
        PARENT,
        LEADERSHIP,
        ADVISOR,
        ADMIN
    }
    
    // ContentBundle Attributes
    private int bundle_id; // Bundle_id of the content bundle
    private int club_id; // Club_id of the content bundle
    private UUID user_id; // User_id of the content bundle
    private UserRole userRole; // User permission level

    public ContentBundle(UUID user_id, int club_id) {
        this.user_id = user_id;
        this.club_id = club_id;

        try {
            fetchBundleID();
        } catch (BundleNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void fetchBundleID() throws BundleNotFoundException {
        // Logic to fetch the bundle ID
        // If the bundle cannot be found, throw the exception
        throw new BundleNotFoundException("Bundle not found for user_id: " + user_id + " and club_id: " + club_id);
    }














    
    // Custom exception class
    public static class BundleNotFoundException extends Exception {
        public BundleNotFoundException(String message) {
            super(message);
        }
    }
}
