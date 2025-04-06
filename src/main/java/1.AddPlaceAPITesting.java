import files.payload; // files/payload has the input of the place we want to add to the API
import io.restassured.RestAssured; //required to use RestAssured class
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*; // import all static methods of restassured
import static org.hamcrest.Matchers.*; // used for string matching
class AddPlaceAPI {
    public static void main(String[] args) {
    //validate if Add Place API is working as expected

        //given - all input details
        //when - Submit the API
        //then - validate the response

        RestAssured.baseURI="https://rahulshettyacademy.com";       // First we declare the base URL

        //Once it is given we focus on given, when and then


                // The response is converted to a String so that it could be used else where.
        String response=given().log().all().queryParam("key","qaclick123")  // .log().all() used to log the inputs and display the log file in the output.
                                            .header("Content-Type","application/json")
                                            .body(payload.Addplace())   // Addplace is a method in the payload file that has the input

                // all input details like parameters, headers and body(input) are given in the "given" section

        .when().post("maps/api/place/add/json")

                // when deals with the which http method we wanted to use. In our case its POST and the parameter is the resource name.
                // This resource name is concatenated with the base URL.

        .then().log().all().assertThat().statusCode(200)                    //.log().all() is used to log the response and display the log file in the output.
                                                .body("scope",equalTo("APP"))       //Matchers is used here
                                                .header("server","Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();   // I am extracting the response and converting it to a String. This is stored in the variable "response" declared above.

                // then is used for the (assertions)validations. We can validate all kinds of things with this.
                // In the above then section I am validating
                // -statuscode which is expected to be 200 for a successful AddPlace operation
                // -body where the scope should be equal to the string "APP"
                // -header to validate the server that we are getting the response from.


        //Add place --> Update place with New address -->Get place to validate if New address is present in the response

        System.out.println("Response: "+response);       // Printing the response

        JsonPath js = new JsonPath(response); // JsonPath is a class used to Parse the content in the Json.
        String placeID = js.getString("place_id"); // place ID is present in the response and we are extracting it
        System.out.println("Place ID:"+placeID);

        // Now that we have successfully added a place now lets try to Update the added place with a new address. For that we need the place ID that got added.
        // We extracted the place ID and we will use it to update the address of that place.

        String newAddress = "70 winter walk, USA";

        given().log().all().queryParam("key","qaclick123")      // As usual we start with given, when, then
                .header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeID+"\",\n" +                 // the place ID we are giving here as an input is the place ID that we retreived from the response
                        "\"address\":\""+newAddress+"\",\n" +          // New updated address
                        "\"key\":\"qaclick123\"\n" +
                        "}\n" +
                        " ")

        .when().put("maps/api/place/update/json")
        .then().assertThat().log().all().statusCode(200)   // Validating status code
                                        .body("msg", equalTo("Address successfully updated"));      // Validating a success message
                                                                                                                   // If the place ID is wrong we get a different error message.
                                                                                                                    // This error message is written by dev. All I do is validate :(

            // We validated if the address is updated or not with this successful error message. But thats is not the way to do it
            // We need to compare the address updated with the address input we have given. We'll see how its done.


            // We can do this by GetPlace API where we can get the address of that place ID and we have the compare and verify the address.

        String getPlaceResponse=given().log().all().queryParam("key","qaclick123")
                                                    .queryParam("place_id",placeID)     // GET request has 2 query parameters. key and place ID

        .when().get("maps/api/place/get/json")      // resource location
        .then().assertThat().log().all().statusCode(200)    // getting the entire location of that place ID and converting it to a string and storing in getPlaceResponse variable
                .extract().response().asString();


        System.out.println("updated: "+getPlaceResponse); // Printing the entire location response. now we have to seperate the address from it

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String updatedAddress=js1.getString("address"); // JsonPath is the parser. Using it to seperate the address and storing in "updatedAddress"

        System.out.println("new: "+newAddress);
        System.out.println("updated: "+updatedAddress);

        /*  Conventional way of comparing strings.
        if(updatedAddress.equals(newAddress)){
            System.out.println("Address validation successful :)");
        }
        else{
            System.out.println("Address update failed");
        }
        */ //In testing we use assertions

        // Using Assert method to compare it. It does not return anything if both the strings are equal.
        Assert.assertEquals(newAddress,updatedAddress); // Only returns or prints something if they don't match
    }
}