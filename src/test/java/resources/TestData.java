package resources;
import Eleven.AddPlaceSerialization;
import Eleven.Location; // Location.java is a pojo class we have written where we have the getters and setters of the date to be pushed into AddPlacePI

import java.util.ArrayList;
import java.util.List;

// We are writing the payload here instead of writing in .given() in stepDefinitions.java

public class TestData {

    public AddPlaceSerialization addPlacePayload(String name, String language, String address) {        // AddPlaceSerialization.java is a where we have declared the attributes of the payload.
        AddPlaceSerialization aps = new AddPlaceSerialization();    // We are creating an object for that class and setting the values for it.

        aps.setAccuracy(100);
        //aps.setAddress("Ferry road, Ibrahimpatnam");    // Lets say  we wish to send the name, language and address as parameters instead of hardcoding it. We first modify the .feature file.
        aps.setAddress(address);    // dynamically passing the data
        //aps.setLanguage("Telugu");
        aps.setLanguage(language);
        aps.setPhone_number("9959958191");
        // aps.setName("Sai praveen Seva");
        aps.setName(name);                      // PS: Check the log file for the dynamically passed name, language and address.
        aps.setWebsite("www.saipraveenseva.com");

        List<String> myList = new ArrayList<String>();
        myList.add("Seva home");

        aps.setTypes(myList);
        Location lo = new Location();       // Location has two sub attributes. we write a seperate class for that Location.java as its an array.
        lo.setLat(-38.456789);  // Creating an object for that class and settings lat and long values.
        lo.setLng(-38.456789);

        aps.setLocation(lo);

        return aps;
    }

    public String deletePlacePayload(String place_id) {
        return "{\r\n    \"place_id\":\""+place_id+"\"\r\n}\r\n";
    }

}
