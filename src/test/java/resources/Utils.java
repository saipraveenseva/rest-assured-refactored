package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {
    public static RequestSpecification requestSpec; // we change this object as public static as we dont wish to create mutliple object everytime this method is called.
                            // static makes sure it uses already existing object
                            // We do this to prevent creation of logging.txt again and again
                    // it will add the logs of multiple datasets in the same logging.txt file.

    public RequestSpecification requestSpecificationUtil() throws IOException {   // This file concept need to catch a FileNotFoundException so we write this.

        if(requestSpec==null) {
                    // We are placing this in the if condition so that it won't create logging.txt again
                    // if there is already an instance of requestSpec and it is not null we just return it so that framework ran on multiple data sets
                    // the logs will be added to already existing logging.txt file and not create another file.

            PrintStream log = new PrintStream(new FileOutputStream("logging.txt")); // We are creating an object of PrintStream class which allows us to send the contents of the object "log" into a file "logging.txt"

            //logging.txt is located at REST_Assured/target/logging.txt and you can find all the logs in there.

            //RestAssured.baseURI="https://rahulshettyacademy.com";       // We declared BaseURI here.

            // The base URI should not be declared here either. In a vast project with multiple testcases it is suggested to have it in a dedicated file.
            // So we comment the Base URI and globalise it. it is written in global.properties in resources package.

            // Unlike doing .log().all() in the .given() we now globalise the logging and avoid rewriting


            // Instead os hard coding the base URI we use the getGlobalValue() method and pass the baseURI variable we have written in the global.properties
            requestSpec = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURI")).addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))      // .addFilter helps with the logging. RequestLoggingFilter is a class that contains .logRequestTo. It has the ability to log everything and paste it in a file.
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))    // Doing the same with response and sending it to the file logging.txt
                    .setContentType(ContentType.JSON).build();
            // passing the required params using the RequestSpecBuilder.
            return requestSpec;
        }
        else {
            return requestSpec;
        }
    }

    public static String getGlobalValue(String key) throws IOException {  // We globalised the base URI. We wrote it in a global.properties file
        Properties prop = new Properties(); // Properties is a java class creating an object for it.
        FileInputStream fis = new FileInputStream("A:\\API Testing\\REST_Assured\\REST_Assured\\src\\test\\java\\resources\\global.properties");
        prop.load(fis); // Creating an object for FileInputStream to access the file mentioned in the above location.
        return prop.getProperty(key);    // using prop and load() in Properties class and passing the fis object

    }                   // passing the baseURI variable.


    /*
        Writing a generalised getJsonPath() method to reuse the same code for get Json utility.
     */

    public String getJsonPath(Response response, String key)
    {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }
}
