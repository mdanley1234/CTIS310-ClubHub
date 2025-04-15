package edu.guilford.data.packets;

import java.util.UUID;

import org.json.JSONObject;

import edu.guilford.supabase.SupabaseInsert;
import edu.guilford.supabase.SupabaseQuery;
import edu.guilford.supabase.SupabaseUpdate;

/**
 * DataPacket is the base class for all data packets used in the application. It
 * defines the functionality for creating, storing, retrieving, and sending data
 * packets.
 */
public class DataPacket extends JSONObject {

    // Packet table identifier
    private String table;

    // CONSTRUCTORS

    // General non-SB Packet constructor
    public DataPacket() {}

    // General SB Retrieve Packet constructor
    public DataPacket(String table, String search_field, UUID search_key) {
        this.table = table;

        JSONObject jsonObject = SupabaseQuery.queryById(table, search_field, search_key);
        jsonObject.keySet().forEach(key -> this.put(key, jsonObject.get(key)));
    }

    // General SB Retrieve Packet constructor (overload - String)
    public DataPacket(String table, String search_field, String search_key) {
        this(table, search_field, UUID.fromString(search_key));
    }

    // SB METHODS

    // Update Packet into table
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

    public String getTable() {
        return table;
    }
}
