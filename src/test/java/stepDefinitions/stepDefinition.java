package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestData;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class stepDefinition extends Utils {        // ANy other utilities like Base URI are declated in Utils.java so we can reuse it instead of writing it again and again.
    // Instead of creating object for Utils.java we just extend it. Just demonstrating inheritance.
    RequestSpecification res;

    ResponseSpecification responseSpec;
    Response response;

    static String place_id; // This should be a static as the place_id will be used by multiple testcase
                // If it were to be a non-static it gets overriden for every testcase.


    //*************** WE CREATE the RequestSpecification and Response object above instead of writing in .given()************

    TestData data = new TestData(); // payload in written in TestData.java we are creating an object for it.

    // This is the code we get into the output console if we run TestRunner.java without implementing stepDfinitions.jva
    // I copied the entire code and pasted it.
    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException { // The logging involves creating a file and writing the logs in it. Addressed in Utils.java. So we throw a FileNotFoundExpection incase of a missing file
                        // As we are getting the name, language and address dynamically I have changed the method signature accordingly
                        // we need to change the method signature of addPlacePayload() from TestData.java as well and include the parameters

        //*************************************** Copied given condition from SpecBuilders.java***********************
        // I deleted the comments that further explain the code SpecBuilder.java. Refer this file for further understanding

        // We set the baseURI here and also add queryParameters. No need to mention these everytime we write given, when and then.

        //*************** WE CREATE the RequestSpecification and Response object above instead of writing in .given()************


        // ResponseSpecBuilder can help avoid rewriting the assertion elements.
        // Wherever we need to assert something, no need to write assertThat again and again for every given, when and then.
        // We are asserting the statusCode and ContentType here.

        res=given().spec(requestSpecificationUtil())  // requestSpecificationUtil() exists in Utils.java where we declared Base URI.
                // Instead of given.addQueryParam() we just write .spec(requestSpec) and pass on requestSpec object
                .body(data.addPlacePayload(name,language,address));         // We have split the given with the response by writing Response separately
                                // passing the name, language, address from here.
                // Instead of writing the payload in .given(), we write in TestData.java file inside a addPlacePayload() method.
                // We create an object for this class as well.

    }
    @When("user calls {string} with {string} http request" )
    public void user_calls_with_http_request(String resource, String httpMethod ) {

        APIResources resourceAPI = APIResources.valueOf(resource);      // This would call the constructor we wrote in APIResource.java and pass the resource variable.

        System.out.println(resourceAPI.getResource());

        responseSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        //*************** Copied when condition from SpecBuilders.java *******************
        //response=res.when().post("/maps/api/place/add/json")        // We can centralize this resources using enums. we write a seperate file where we have all the resources and access the required resource address
                                                                            // rather than hard coding it like this.
        // Commenting out the conventional way of sending resource data
        if(httpMethod.equalsIgnoreCase("POST")) {
            response = res.when().post(resourceAPI.getResource())
                    .then().spec(responseSpec).extract().response();    // Instead of .then().assertThat() we write spec(responseSpec) and pass responseSpec object.
        }
        else if(httpMethod.equalsIgnoreCase("GET")){
            response = res.when().get(resourceAPI.getResource())
                    .then().spec(responseSpec).extract().response();        // We have not written logic for GET yet. Just demonstrating it.
        }
    }
    @Then("the API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        assertEquals(response.statusCode(),200);
        // This is how we write assertions in JUnit


    }
    @Then("check {string} in response body is {string}")
    public void in_response_body_is(String keyValue, String ExpectedValue) {
            // In .then() of placeValidations.feature file we have "status" and "OK" as value. These will sit in "keyValue" and "ExpectedValue".
        String resp = response.asString();

        assertEquals(getJsonPath(response,keyValue),ExpectedValue);
        //js.get(keyValue) gives the keyValue of the key "status" as "OK"
        // We are comparing it with ExpectedValue "OK"

    }

    @Then("validate {string} in response body is {string}")
    public void in_response_body_is_here(String keyValue1, String ExpectedValue1) {
        // In .then() of placeValidations.feature file we have "status" and "OK" as value. These will sit in "keyValue" and "ExpectedValue".
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        assertEquals(js.get(keyValue1),ExpectedValue1);

        //js.get(keyValue) gives the keyValue of the key "status" as "OK"
        // We are comparing it with ExpectedValue "OK"

    }

    /*
        We are validating if the place_id generated corresponds to the place we added.
        We use the place_id and send it as a parameter to getPlaceAPI and the get the details.
        In those details we validate the place name with the input we have given in the payload.
     */
    @Then("verify the place_id generated maps to {string} using {string}")
    public void verify_the_place_id_generated_maps_to_using(String expectedName, String resource) throws IOException {

        place_id = getJsonPath(response,"place_id");     // getJsonPath is a method written in Utils.java that will fetch the place_id from the response. So we pass the response the place_id key to get the place_id value. It is stored in place_id variable.
        res=given().spec(requestSpecificationUtil())      // given sends the baseURI, logging, required query parameters
                .queryParam("place_id",place_id);

        user_calls_with_http_request(resource,"GET");       // Instead of writing a when and send the get from it we are using a previously used method user_calls_with_http_request.
                                                                        // this method can handle any http request. Refer APIResources.java to get a picture.
        String responseName = getJsonPath(response,"name");     // We are getting the name of the place we added and storing it in responseName
        assertEquals(responseName,expectedName);                // asserting the responseName with the expectedName that we assigned in placeValidations.feature.

    }

    @Given("DeletePlace payload")
    public void delete_place_payload() throws IOException {
        // Write code here that turns the phrase above into concrete actions

        res = given().spec(requestSpecificationUtil()).body(data.deletePlacePayload(place_id));

        // Just written the Given implementation and we are getting the place_id from above passing it as a payload using deletePlacePayload in TestData.java file
    }

}
