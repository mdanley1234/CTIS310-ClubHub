package edu.guilford.data;

import java.util.UUID;

public class ContentPacket {

    String table;
    UUID bundle_id;

    public ContentPacket(String table, UUID bundle_id) {
        this.table = table;
        this.bundle_id = bundle_id;
    }
    
}
