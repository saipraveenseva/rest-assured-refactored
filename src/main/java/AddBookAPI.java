/*

    Lets work on a new Library API. We will cover the below topics
    1. Dynamically build json payload with external inputs
    2. Parameterize the API test with multiple data sets
    3. Send static json files(payload) directly into post method of rest Assured.

 */

import files.payload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo; //hamcrest.Matchers for equalTo

public class AddBookAPI {   // Writing an API to add book into Library API
    @Test(dataProvider = "booksData") // dataProvider is a way to provide data in the form of multiple data sets. This statement will setup a connection to @Test and @DataProvider

    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";        // As usual we initially declare the base URI

        String addBookResponse = given().log().all()    // Followed by the given, when and then logics. Output of this is stored in addBookResponse.
                                        .header("Content-Type", "application/json")
                                        .body(payload.Addbook(isbn,aisle))      // Observe the payload we are sending. The Addbook function has parameters.
                                                                            // look into payload.Addbook() for further explanation

                .when().post("Library/Addbook.php") // .when() for post request and the resources.
                .then().assertThat().statusCode(200) // verifying status code(200) and "Msg" with an assertion
                                    .body("Msg", equalTo("successfully added")).extract().response().asString();

        JsonPath js = new JsonPath(addBookResponse);    // Creating a json object
        String bookID = js.getString("ID");     // Using the object to retrieve the ID from addBookResponse

        System.out.println("Book ID: "+bookID);     // Printing the bookID


        String deleteBookResponse=given().log().all()       // Deleting the book that we entered earlier in the Library API. Storing the response in deleteBookResponse
                                        .header("Content-Type", "application/json")
                                        .body("{\n" +
                        "    \"ID\": \"" + bookID + "\"\n" +    // Passing the bookID here as a variable.
                        "}")


        .when().delete("Library/DeleteBook.php")    // declaring the delete request and resources in .when()
                .then().assertThat().statusCode(200)    // asserting the status code(200) and the "msg"
                                    .body("msg", equalTo("book is successfully deleted")).extract().response().asString();



    }

    @DataProvider(name="booksData")     // This @DataProvider provides the data to the AddBookAPI.  we assigned a name to it.
    public Object[][] getData(){
        //array is a collection of elements
        //multidimensional array is a collection of arrays.
        return new Object[][] {{"bnm","789"},{"okl","456"},{"bji","123"}};  // We modified the payload.AddBook() file with variables instead of actual values for isbn and aisle

        // We are passing a multi-dimensional array which is a collection of arrays. The outer arrays contains 2 sub arrays each with isbn and aisle values.
        // This array is of type object so the return type is object as well.

//        Object[][] allows you to return a wide variety of data types (e.g., String, int, boolean, etc.) because all Java objects inherit from the Object class.
//        This flexibility is critical for frameworks like TestNG, which use the data provider to inject test data.

        // TestNG requires the data provider method to return data in a format it can understand, and Object[][] is the most commonly used structure for this purpose.

//        A normal 2D array like String[][] can only hold String values. If your test requires mixed data types (e.g., int, boolean), it will fail.
//                Object[][] can hold any data type, including String, Integer, Double, or even custom objects.

        // Note: There are 3 sub arrays in Object[][]. So the AddBook API will run 3 times and add 3 books to the Library API.
                // And everytime we add a book its ID is being retrieved and the book is being deleted by deleteBook logic we wrote above.


    }
}
