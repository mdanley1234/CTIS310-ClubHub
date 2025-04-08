package edu.guilford.data;

import java.util.UUID;

import org.json.JSONObject;

import edu.guilford.supabase.SupabaseQuery;

/**
 * DataPacket is the base class for all data packets used in the application.
 * It defines the functionality for creating, storing, retrieving, and sending data packets.
 */
public abstract class DataPacket extends JSONObject {

    // General SB Retrieve Packet constructor
    public DataPacket(String table, String search_field, UUID search_key) {
        JSONObject jsonObject = SupabaseQuery.queryById(table, search_field, search_key);
        jsonObject.keySet().forEach(key -> this.put(key, jsonObject.get(key)));

    }

}
