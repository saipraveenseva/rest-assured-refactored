import files.payload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AddBookDeleteAllBooksAPI {

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

        // Instead of storing the bookID here, we can delete books after the entire test run
    }

    @Test
    public void deleteAllBooks() {
        // We pass the data directly to the deleteBooks method
        deleteBooks(getData());  // Calling deleteBooks with data provider booksData
    }

    // Method to delete all books specified in the Object[][] array
    public void deleteBooks(Object[][] booksData) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        // Iterate over the booksData array and delete each book using the provided ISBN and aisle
        for (Object[] book : booksData) {
            String isbn = (String) book[0];
            String aisle = (String) book[1];
            String bookID = isbn + aisle;  // Construct the bookID from isbn and aisle (for simplicity)

            // Delete the book using the bookID
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

    @DataProvider(name = "booksData")
    public Object[][] getData() {
        return new Object[][]{
                {"bnm", "789"},
                {"okl", "456"},
                {"bji", "123"}
        };
    }
}
