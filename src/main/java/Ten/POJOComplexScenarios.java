package Ten;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.*;


/*
        Copied the same code from ClientCredentials.java to demonstrate different scenarios with POJO classes.
 */

public class POJOComplexScenarios {

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

        /*
        String CourseDetails = given()      // Now we use this access token to get the course details from the main end point.
                                .queryParam("access_token",accessToken)  // passing the access_token as a query parameter

                                .when().get("/oauthapi/getCourseDetails")   // Actual endpoint where the courses are located.

                                .then().assertThat().statusCode(401).extract().response().asString();   // Converting to String to print them.

        System.out.println("Course details: " +CourseDetails);

         */

        // The above way is how we normally get the CourseDetails. But if we want to get the data using Deserialization we use the below approach.

        Serialization sc= given()      // Creating an object of the Serilization.(Serialization is the class that contains the data)
                .queryParam("access_token",accessToken)

                .when().get("/oauthapi/getCourseDetails")   // Actual endpoint where the courses are located.

                .as(Serialization.class);  // Converting the response generate to the type Serilization. In that way we can access the attributes one by one.

                        // We are just gettin the below details. Not need to assert anything at the moment. So we skipped .then()

        System.out.println("Instructor details " +sc.getInstructor());
        System.out.println("LinkedIn details " +sc.getLinkedin());

        System.out.println(sc.getCourses().getWebAutomation().get(1).getCourseTitle());

        List<webAutomationcourse> allWebAutomationCourses = sc.getCourses().getWebAutomation();

        for (int i=0;i<allWebAutomationCourses.size();i++){
            System.out.println(allWebAutomationCourses.get(i).getCourseTitle()); // Prints all Web automation course titles.

            System.out.println(allWebAutomationCourses.get(i).getPrice());  // Prints all web automation course prices.
        }


        // If we wish to get the price of a particular API course, here's how we do it.

        List<apicourse> allApiCourses = sc.getCourses().getApi();

        for (int i=0;i<allApiCourses.size();i++) {
            if (allApiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println("SoapUI Webservices testing price: " +allApiCourses.get(i).getPrice());

            }
        }

    }
}
