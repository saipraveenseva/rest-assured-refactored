package Eight;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


import java.io.File;

import static io.restassured.RestAssured.*;

/*

    Headover to https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-post
    and open the curl section in the code which will explain how to write this post request to create a bug in JIRA using REST API calls.

    --url 'https://your-domain.atlassian.net/rest/api/3/issue' \
  --user 'email@example.com:<api_token>' \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \

  -url would be the base URI. Change it accordingly as shown in the below code.
  -header 2 headers to be add as shown below.
  -

 */

public class CreateBugAutomation {

    public static void main(String[] args) {
        RestAssured.baseURI="https://saipraveenseva.atlassian.net/";

        String createBugResponse =
                given()
                    .header("Content-Type","application/json")      // Refer "8.FileBuginJiraUsingPostman.txt" to get this key for applying Basic authorization to this request.
                    .header("Authorization","Basic c2FpcHJhdmVlbnNldmFAZ21haWwuY29tOkFUQVRUM3hGZkdGMEk2dU9COFdqWGdhaFVYX1VLUV9XMFlIVjQtTVFubm1NWm5LU3ZYRHZhTnVZQkt5dFJLcGs1eDRDTmtWSjRWdTVjd2J2Q2VDVURtbXNpRUV6QzFVaDJwOXpwX2N5alhMS1hxQ09vZEZiX3NONFVFcFdES2JPa0VZOXkwRVZCVzZJMGw1MExhZERKN3JEV09fZ04tUFlMVDgtaXpGYWZ6VGNEcVk5R0xfclo2WT01MTNDODY1Qw==")
                    .body("{\n" +                   // Follow this body rather than whats given in the https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-post
                        "  \"fields\": {\n" +          // There will be multiple fields while we file a bug. For time being we don't need to include all those.
                        "    \"project\": {\n" +
                        "      \"key\": \"SCRUM\"\n" +  // This is the project KEY that we got from Jira project setting.
                        "    },\n" +
                        "    \"summary\": \"Bug 3 created via automation\",\n" +       // This would be the title.
                        "    \"description\": {\n" +
                        "      \"version\": 1,\n" +
                        "      \"type\": \"doc\",\n" +
                        "      \"content\": [\n" +
                        "        {\n" +
                        "          \"type\": \"paragraph\",\n" +
                        "          \"content\": [\n" +
                        "            {\n" +
                        "              \"type\": \"text\",\n" +
                        "              \"text\": \"Your bug description here. This can be multiple lines.\"\n" +    // This is the bug description
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    \"issuetype\": {\n" +
                        "      \"name\": \"Bug\"\n" +   // We mention the issue type bug here.
                        "    }\n" +
                        "  }\n" +
                        "}")
                .log().all()
                .when().post("rest/api/3/issue")    // post request in .when as usual and rest of the base URI
                .then().log().all() // Logging everything
                .assertThat()
                .statusCode(201)    // Assert code 201 indicating the bug has been filed in Jira succesfully.
                .extract().response().asString();   // COnverting all this response into a string so we can use it in future.  This is stored in createBugResponse String.

        JsonPath js = new JsonPath(createBugResponse);  // Creating an json object for this createBugResponse
        String issueID=js.getString("id");  // Retrieving just the bug ID from this whole log response.

        System.out.println("Issue Id: "+issueID);       // Printing the Bug ID


        // Now adding attachment to the filed bug using REST API calls.
        // Similarly visit https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-attachments/#api-rest-api-3-attachment-content-id-get
        // to get the header required and the statuscode we get on a successful addition of an attachment to previously filed bug.

        String addAttachmentResponse =
                given()
                        .pathParam("key",issueID)   // this is new. We are including a pathParam as we are providing the bug ID as a parameter to add the attachment to the bug.
                        .header("Authorization","Basic c2FpcHJhdmVlbnNldmFAZ21haWwuY29tOkFUQVRUM3hGZkdGMEk2dU9COFdqWGdhaFVYX1VLUV9XMFlIVjQtTVFubm1NWm5LU3ZYRHZhTnVZQkt5dFJLcGs1eDRDTmtWSjRWdTVjd2J2Q2VDVURtbXNpRUV6QzFVaDJwOXpwX2N5alhMS1hxQ09vZEZiX3NONFVFcFdES2JPa0VZOXkwRVZCVzZJMGw1MExhZERKN3JEV09fZ04tUFlMVDgtaXpGYWZ6VGNEcVk5R0xfclo2WT01MTNDODY1Qw==")
                        .header("X-Atlassian-Token","no-check") // A key header used while adding an attachment

                        .multiPart("file", new File("C:\\Users\\saipr\\Pictures\\IMG_0181.jpg")).log().all()
                                // this .multiPart is new as well. We need to comprehend the address location of the attachment in the local machine
                .when().post("rest/api/3/issue/{key}/attachments")
                        // this post request has {key} in it as the bug ID is passed as a parameter here.
                        .then().log().all().assertThat().statusCode(200).toString();

        System.out.println("Issue ID: "+issueID);


    }
}
