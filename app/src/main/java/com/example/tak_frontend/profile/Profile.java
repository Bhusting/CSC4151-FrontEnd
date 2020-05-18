package com.example.tak_frontend.profile;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
public class Profile {

    public UUID ProfileId;

    public String FirstName;

    public String LastName;

    public int XP;

    public UUID HouseId;

    public String Email;


    public Profile(@NotNull Profile profile){
        ProfileId = profile.ProfileId;
        FirstName = profile.FirstName;
        LastName = profile.LastName;
        this.XP = profile.XP;
        HouseId =profile.HouseId;
        Email = profile.Email;
    }

    public Profile(UUID profileId, String firstName, String lastName, int XP, UUID houseId, String email) {
        ProfileId = profileId;
        FirstName = firstName;
        LastName = lastName;
        this.XP = XP;
        HouseId = houseId;
        Email = email;
    }
}
