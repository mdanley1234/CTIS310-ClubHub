package edu.guilford.data.packets;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONObject;

import edu.guilford.supabase.SupabaseDelete;
import edu.guilford.supabase.SupabaseInsert;
import edu.guilford.supabase.SupabaseQuery;
import edu.guilford.supabase.SupabaseUpdate;

/**
 * DataPacket defines the functionality for creating, storing, retrieving,
 * and sending data packets between the application and SB.
 */
public class DataPacket extends JSONObject {

    // Packet table identifier
    private String table;

    // Packet metadata (Non-SB) {"includeJoinButton", etc.}
    private ArrayList<String> metadata = new ArrayList<>();

    // Constructors (Retrieval)

    // General non-SB Packet constructor
    public DataPacket(String table) { this.table = table; }

    // General SB Retrieve Packet constructor (UUID)
    public DataPacket(String table, String search_field, UUID search_key) {
        this.table = table;

        JSONObject jsonObject = SupabaseQuery.queryById(table, search_field, search_key);
        jsonObject.keySet().forEach(key -> this.put(key, jsonObject.get(key)));
    }

    // General SB Retrieve Packet constructor (overload - String)
    public DataPacket(String table, String search_field, String search_key) {
        this(table, search_field, UUID.fromString(search_key));
    }

    // Supabase (Alter)

    // Update Packet in table
    public boolean updatePacket(String table, String search_field, UUID search_key) {
        // Check if the packet is valid (not null and not empty)
        if (this == null || this.isEmpty()) {
            return false; // Invalid packet
        }

        // Send the packet to the server (implementation depends on your server API)
        JSONObject result = SupabaseUpdate.updateById(table, search_field, search_key, this);

        return result != null;
    }
    
    // Insert Packet into table
    public boolean insertPacket(String table) {
        // Check if the packet is valid (not null and not empty)
        if (this == null || this.isEmpty()) {
            return false; // Invalid packet
        }

        // Send the packet to the server (implementation depends on your server API)
        JSONObject result = SupabaseInsert.insertOne(table, this);

        return result != null;
    }

    // Delete Packet from table
    public boolean deletePacket(String table) {
        StringBuilder filterBuilder = new StringBuilder();

        this.keySet().forEach(key -> {
            Object value = this.get(key);
            if (value instanceof String) {
            filterBuilder.append(key).append("=eq.").append("'").append(value).append("'").append("&");
            } else {
            filterBuilder.append(key).append("=eq.").append(value).append("&");
            }
        });

        // Remove the trailing '&' if it exists
        if (filterBuilder.length() > 0) {
            filterBuilder.setLength(filterBuilder.length() - 1);
        }

        // Send the delete request to the server
        int result = SupabaseDelete.deleteMany(table, filterBuilder.toString());

        return result > 0; // Return true if delete was successful
    }

    // Getter for table String
    public String getTable() {
        return table;
    }

    // Metadata methods
    public void addMetadata(String metadata) {
        this.metadata.add(metadata);
    }

    public boolean hasMetadata(String metadata) {
        return this.metadata.contains(metadata);
    }

    public ArrayList<String> getMetadata() {
        return metadata;
    }
}
