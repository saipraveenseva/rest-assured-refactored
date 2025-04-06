package Eleven;

import java.util.List;

public class AddPlaceSerialization {

    // In earlier sessions we have seen how we can deserialize and get the details from the Json file

    //Now we are gonna see how can set values to the json attributes. This is Serialization
     // For this we are gonna use the well know Add place API

    // First we declare the attributes in the json class

    private int accuracy;
    private String name;
    private String phone_number;
    private String address;
    private String website;
    private String language;

    // All the above attributes are simple and straight forward. below are some complex attributes

    // The location attribute is a sub json with the subfields latitude and longitude.

    // For that we have write a seperate "Location" class.

    private Location location;  // The return type of this attribute is the Location class

    // types is one another complex attribute. Its an array.

    private List<String> types;

    // Lets generate getters and setters

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }


    // Now time to set the values to these attributes.(Serialization)






}
