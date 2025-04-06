package Eight;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class GetBugAutomation {

    public static void main(String[] args) {
        RestAssured.baseURI="https://saipraveenseva.atlassian.net";

        int issueID=10004;  // I wrote this in code in a seperate file so i manually declared a variable and assign the bug ID that we filed earlier.

        /*
                Similarly we visit https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-issueidorkey-get
                to get the required headers needed to obtain the details of the bug we filed earlier.

                curl --request GET \
                --url 'https://your-domain.atlassian.net/rest/api/3/issue/{issueIdOrKey}' \
                --user 'email@example.com:<api_token>' \
                --header 'Accept: application/json'

         */

        String getBugResponse =
                given()
                        .pathParam("key",issueID)   // used a pathParam as we are sending the bug ID as a parameter to retrieve the bug data.
                        .header("Content-Type","application/json")
                        .header("Accept","application/json")
                        .header("Authorization","Basic c2FpcHJhdmVlbnNldmFAZ21haWwuY29tOkFUQVRUM3hGZkdGMEk2dU9COFdqWGdhaFVYX1VLUV9XMFlIVjQtTVFubm1NWm5LU3ZYRHZhTnVZQkt5dFJLcGs1eDRDTmtWSjRWdTVjd2J2Q2VDVURtbXNpRUV6QzFVaDJwOXpwX2N5alhMS1hxQ09vZEZiX3NONFVFcFdES2JPa0VZOXkwRVZCVzZJMGw1MExhZERKN3JEV09fZ04tUFlMVDgtaXpGYWZ6VGNEcVk5R0xfclo2WT01MTNDODY1Qw==")
                        .log().all()    // same autorization we used earlier
                        .when().get("/rest/api/3/issue/{key}")  // {key} as we are passing the bug ID as a parameter
                        .then().log().all()
                        .assertThat()
                        .statusCode(200)    // Status code 200 on a successful retrieve of bug data
                        .extract().response().asString();



        System.out.println("----------------------------------------------");
        System.out.println(getBugResponse);
        System.out.println("Issue Id: "+issueID);

    }
}
