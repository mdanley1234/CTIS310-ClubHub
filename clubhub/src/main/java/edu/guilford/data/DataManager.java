package edu.guilford.data;

public class DataManager {
    
    private static ProfilePacket profilePacket;

    public static ProfilePacket getProfilePacket() {
        return profilePacket;
    }

    public static void setProfilePacket(ProfilePacket profilePacket) {
        DataManager.profilePacket = profilePacket;
    }



}
