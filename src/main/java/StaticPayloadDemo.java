import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StaticPayloadDemo {        // Note: Inorder to run this again change the isbn and aisle in the AddBook.json file as the book already exists from the previous run.

    /*
        Till now we have seen the payload being passed as an argument or directly copying in the .given() body
        There comes some circumstances where the payload is given as a file in the external drive or system
        We can use this static payload file that is in the system by passing the path of this file.

        Inorder to do that, first we have to convert the Path to Byte format and then we convert into String format.

     */

    @Test
    public void addBookAndDelete() throws IOException {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        // Add the book and get the response
        String addBookResponse = given().log().all()
                .header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("A:\\API Testing\\REST_Assured\\REST_Assured\\src\\main\\java\\files\\AddBook.json"))))
                        /*
                            Files.readAllBytes(Path path) is a method from the java.nio.file.Files class.
                            This method reads all bytes from the file and returns them as a byte array (byte[]).

                            new String(byte array[]) converts it to a String

                         */


                .when().post("Library/Addbook.php")


                .then().assertThat().statusCode(200)
                .body("Msg", equalTo("successfully added"))
                .extract().response().asString();

        // Retrieve the Book ID from the response
        JsonPath js = new JsonPath(addBookResponse);
        String bookID = js.getString("ID");
        System.out.println("Book added with ID: " + bookID);

        // Instead of storing the bookID here, we can delete books after the entire test run
    }
}
