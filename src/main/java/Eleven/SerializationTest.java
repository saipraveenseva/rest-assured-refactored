package Eleven;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {
    public static void main(String[] args) {

        RestAssured.baseURI="https://rahulshettyacademy.com";   // We will use the well know Google maps API

        AddPlaceSerialization aps = new AddPlaceSerialization();    // Creating an object for the AddPlaceSerialization class we wrote earlier

        aps.setAccuracy(50);                                    // Setting the values for all parameters. This is called Serialization
        aps.setAddress("Ferry road, Ibrahimpatnam");
        aps.setLanguage("Telugu");
        aps.setPhone_number("9959958191");
        aps.setName("Sai praveen Seva");
        aps.setWebsite("www.saipraveenseva.com");

        List<String> myList = new ArrayList<String>();  // As setTypes is an Array we are creating an ArrayList and adding value to it before we assign the value using set method
        myList.add("Seva home");    // Value is added here

        aps.setTypes(myList);   // Passing the myList

        Location lo = new Location();       // Location is also a complex attribute. Its a nested json so we create an object for Location class
        lo.setLat(-38.456789);      // using the object we pass on the values.
        lo.setLng(-38.456789);

        aps.setLocation(lo);        // And we send the object to the set method to assign values.

        Response res=given().queryParam("key","qaclick123") // As usual we write the given when and then methods.
                .body(aps)

        .when().post("/maps/api/place/add/json")

                .then().assertThat().statusCode(200).extract().response();  // asserting the status code

        String responseString = res.asString(); // Converting the response to a String and printing it.
        System.out.println(responseString);

        // The date is successfully assigned using setter methods(Serialization).

        // Now we just manually print and see if the data is correctly assigned using the Getter(Deserialization)

        System.out.println(aps.getName());
        System.out.println(aps.getPhone_number());
        System.out.println(aps.getAddress());
        System.out.println(aps.getLanguage());
        System.out.println(aps.getAccuracy());
        System.out.println(aps.getWebsite());
        System.out.println(aps.getLocation());
        System.out.println(aps.getTypes());

    }
}
