import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class AddBookAPImain {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        // Sending the POST request to add a book
        String addBookResponse = given().log().all()
                .header("Content-Type", "application/json")
                .body(payload.Addbook("asdf","123"))
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();

        // Printing the response
        System.out.println("Response: " + addBookResponse);

        // Extracting and printing the book ID from the response
        JsonPath js = new JsonPath(addBookResponse);
        System.out.println("Book ID: " + js.getString("ID"));
    }
}
