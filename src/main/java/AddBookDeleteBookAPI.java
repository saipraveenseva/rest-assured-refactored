
/*
    We have split the addBook and deleteBook logics into seperate methods.
 */

import files.payload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AddBookDeleteBookAPI {

    @Test(dataProvider = "booksData")
    public void addBookAndDelete(String isbn, String aisle) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        // Add the book and get the response
        String addBookResponse = given().log().all()
                .header("Content-Type", "application/json")
                .body(payload.Addbook(isbn, aisle))

                .when().post("Library/Addbook.php")

                .then().assertThat().statusCode(200)
                .body("Msg", equalTo("successfully added"))
                .extract().response().asString();

        // Retrieve the Book ID from the response
        JsonPath js = new JsonPath(addBookResponse);
        String bookID = js.getString("ID");
        System.out.println("Book added with ID: " + bookID);

        // Call the deleteBook() method to delete the book
        deleteBook(bookID);
    }

    // Method to delete a book using the given Book ID
    public void deleteBook(String bookID) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        String deleteBookResponse = given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ID\": \"" + bookID + "\"\n" +
                        "}")
                .when().delete("Library/DeleteBook.php")
                .then().assertThat().statusCode(200)
                .body("msg", equalTo("book is successfully deleted"))
                .extract().response().asString();

        System.out.println("Book with ID: " + bookID + " successfully deleted.");
    }
}
