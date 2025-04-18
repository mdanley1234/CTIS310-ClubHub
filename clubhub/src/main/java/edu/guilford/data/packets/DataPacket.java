package edu.guilford.data.packets;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONObject;

import edu.guilford.supabase.SupabaseDelete;
import edu.guilford.supabase.SupabaseInsert;
import edu.guilford.supabase.SupabaseQuery;
import edu.guilford.supabase.SupabaseUpdate;

/**
 * The {@code DataPacket} class represents a packet of data associated with a
 * specific table in a Supabase database. It extends {@link JSONObject}, allowing
 * dynamic JSON-based data structure manipulation and storage.
 * <p>
 * This class provides methods for creating, retrieving, updating, and deleting
 * packets from Supabase, as well as maintaining auxiliary metadata.
 */
public class DataPacket extends JSONObject {

    /** The name of the associated database table. */
    private String table;

    /** Metadata related to this packet that is not stored in the database. */
    private final ArrayList<String> metadata = new ArrayList<>();

    /**
     * Constructs a new {@code DataPacket} for a specific table.
     *
     * @param table the table this packet is associated with
     */
    public DataPacket(String table) {
        this.table = table;
    }

    /**
     * Constructs a new {@code DataPacket} by querying Supabase for a specific UUID.
     *
     * @param table        the table to query
     * @param searchField  the field to search by (typically a primary key)
     * @param searchKey    the UUID value to search for
     */
    public DataPacket(String table, String searchField, UUID searchKey) {
        this.table = table;
        JSONObject jsonObject = SupabaseQuery.queryById(table, searchField, searchKey);
        jsonObject.keySet().forEach(key -> this.put(key, jsonObject.get(key)));
    }

    /**
     * Overloaded constructor that accepts a string UUID.
     *
     * @param table        the table to query
     * @param searchField  the field to search by
     * @param searchKey    the string UUID to convert and search
     */
    public DataPacket(String table, String searchField, String searchKey) {
        this(table, searchField, UUID.fromString(searchKey));
    }

    /**
     * Updates this packet in Supabase using the given key.
     *
     * @param table        the table to update
     * @param searchField  the field to search by
     * @param searchKey    the UUID value to match
     * @return {@code true} if update was successful; {@code false} otherwise
     */
    public boolean updatePacket(String table, String searchField, UUID searchKey) {
        if (this.isEmpty()) {
            return false;
        }
        JSONObject result = SupabaseUpdate.updateById(table, searchField, searchKey, this);
        return result != null;
    }

    /**
     * Inserts this packet into the specified table.
     *
     * @param table the table to insert into
     * @return {@code true} if insert was successful; {@code false} otherwise
     */
    public boolean insertPacket(String table) {
        if (this.isEmpty()) {
            return false;
        }
        JSONObject result = SupabaseInsert.insertOne(table, this);
        return result != null;
    }

    /**
     * Deletes this packet from the specified table using its key-value structure as filters.
     *
     * @param table the table to delete from
     * @return {@code true} if deletion was successful; {@code false} otherwise
     */
    public boolean deletePacket(String table) {
        StringBuilder filterBuilder = new StringBuilder();
        this.keySet().forEach(key -> {
            Object value = this.get(key);
            if (value instanceof String) {
                filterBuilder.append(key).append("=eq.'").append(value).append("'&");
            } else {
                filterBuilder.append(key).append("=eq.").append(value).append("&");
            }
        });

        if (filterBuilder.length() > 0) {
            filterBuilder.setLength(filterBuilder.length() - 1);
        }

        int result = SupabaseDelete.deleteMany(table, filterBuilder.toString());
        return result > 0;
    }

    /**
     * Gets the table name this packet is associated with.
     *
     * @return the table name
     */
    public String getTable() {
        return table;
    }

    /**
     * Adds a piece of metadata to this packet (not stored in Supabase).
     *
     * @param metadata the metadata tag to add
     */
    public void addMetadata(String metadata) {
        this.metadata.add(metadata);
    }

    /**
     * Checks whether this packet contains a specific metadata tag.
     *
     * @param metadata the tag to search for
     * @return {@code true} if present; {@code false} otherwise
     */
    public boolean hasMetadata(String metadata) {
        return this.metadata.contains(metadata);
    }

    /**
     * Returns all metadata associated with this packet.
     *
     * @return a list of metadata tags
     */
    public ArrayList<String> getMetadata() {
        return metadata;
    }
}
