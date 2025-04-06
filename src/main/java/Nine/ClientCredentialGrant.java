package Nine;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;


/*
        Copy paste of what we did in Postman and explained in OAuth2.0_Introduction.txt
 */

public class ClientCredentialGrant {

    public static void main(String[] args) {

        RestAssured.baseURI= "https://rahulshettyacademy.com/"; // base URI

        String getAccessTokenResponse = given() // passing the form parameters that we got from the dev. These are given as "form-data" in Postman
                                                .formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                                                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                                                .formParams("grant_type","client_credentials")
                                                .formParams("scope","trust")

                                        .when().post("oauthapi/oauth2/resourceOwner/token") // Authorization server endpoint. Sending the request to this place to get the access code.
                                        .then().assertThat().statusCode(200)
                                        .extract().response().asString();               // extracting and storing the entire response in a String variable.

        JsonPath js = new JsonPath(getAccessTokenResponse);
        String accessToken = js.getString("access_token");  // Just extracting the access_token from the response and printing it.

        System.out.println("Access token: "+accessToken);


        String CourseDetails = given()      // Now we use this access token to get the course details from the main end point.
                                .queryParam("access_token",accessToken)  // passing the access_token as a query parameter

                                .when().get("/oauthapi/getCourseDetails")   // Actual endpoint where the courses are located.

                                .then().assertThat().statusCode(401).extract().response().asString();   // Converting to String to print them.

        System.out.println("Course details: " +CourseDetails);  // These course details are only accessible when we give the valid access_token. This is authorization that we are doing here.


    }
}
