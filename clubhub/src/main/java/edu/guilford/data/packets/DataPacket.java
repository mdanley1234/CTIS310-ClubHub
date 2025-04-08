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

    // General non-SB Packet constructor
    public DataPacket() {
    }

    // General SB Retrieve Packet constructor
    public DataPacket(String table, String search_field, UUID search_key) {
        JSONObject jsonObject = SupabaseQuery.queryById(table, search_field, search_key);
        jsonObject.keySet().forEach(key -> this.put(key, jsonObject.get(key)));
    }

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

    // Upsert Packet into table with onConflict clause
    public boolean upsertPacket(String table, String onConflict) {
        // Check if the packet is valid (not null and not empty)
        if (this == null || this.isEmpty()) {
            return false; // Invalid packet
        }

        // Send the packet to the server (implementation depends on your server API)
        JSONObject result = SupabaseInsert.upsertOne(table, this, onConflict);

        return result != null;
    }
}
