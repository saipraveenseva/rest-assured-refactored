package Twelve;


// SpecBuilders are used to avoid code repetition.

// Declaring base URI, and some common parameters in given, when and then are sometimes
// repeated multiple times while writing get, post and delete requests.

// Inorder to avoid that we write these common lines of code as a SpecBuilder and use it when necessary

// One prime example is lets say we need to  change the query parameter of key and value

// We have to change the key and valuable wherever we wrote given, when and then.

import Eleven.AddPlaceSerialization;
import Eleven.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

// Instead we write the SpecBuilder and make the changes in the SpecBuilder
public class SpecBuilders {

    public static void main(String[] args) {

        // Copied the code from the SerializationTest.java
        // Compare the code with SerializationTest.java side by side for better understanding.


        RestAssured.baseURI="https://rahulshettyacademy.com";     // Commented this line because its a common line that we will use often and we write it in a RequestSpect Builder.

        AddPlaceSerialization aps = new AddPlaceSerialization();

        aps.setAccuracy(100);
        aps.setAddress("Ferry road, Ibrahimpatnam");
        aps.setLanguage("Telugu");
        aps.setPhone_number("9959958191");
        aps.setName("Sai praveen Seva");
        aps.setWebsite("www.saipraveenseva.com");

        List<String> myList = new ArrayList<String>();
        myList.add("Seva home");

        aps.setTypes(myList);
        Location lo = new Location();
        lo.setLat(-38.456789);
        lo.setLng(-38.456789);

        aps.setLocation(lo);

        RequestSpecification requestSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123").setContentType(ContentType.JSON).build();

        // Syntax for RequestSpec builder goes like above. RequestSpecification is class responsible for that.
        // RequestSpecBuilder() is a method which can build the specBuilder.
        // We set the baseURI here and also add queryParameters. No need to mention these everytime we write given, when and then.

        ResponseSpecification responseSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        // ResponseSpecBuilder can help avoid rewriting the assertion elements.
        // Wherever we need to assert something, no need to write assertThat again and again for every given, when and then.
        // We are asserting the statusCode and ContentType here.

        RequestSpecification res=given().spec(requestSpec)  // Instead of given.addQueryParam() we just write .spec(requestSpec) and pass on requestSpec object
                .body(aps);         // We have split the given with the response by writing Response separately.

        Response response=res.when().post("/maps/api/place/add/json")   // Declare a variable response of type Response as we normally do.

                .then().spec(responseSpec).extract().response();    // Instead of .then().assertThat() we write spec(responseSpec) and pass responseSpec object.

        String responseString = response.asString();    // Rest of the code is same.
        System.out.println(responseString);


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
