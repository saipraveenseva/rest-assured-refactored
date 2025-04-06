package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    /* We assigned @DeletePlace annotation to Delete place testcase in the .feature file.
         This is classic example. If we wish to delete a place before even adding it, it will throw an error and test will fail.
         We don't need to run the AddPlace testcase before running DeletePlace everytime
         We can set some rules that before executing DeletePlace testcase we want to execute a few lines of code which involves adding a place temporarily.
     */

    @Before("@DeletePlace")
    public void beforeScenarios() throws IOException {
        // write a code that will give you place ID>/
        // Execute this code ONLY if place ID is null.
        // We don't need to run this when we are running entire test suite, which involes AddPlace as well.


        stepDefinition m = new stepDefinition();    // Created an object for stepDefinition class

        if(stepDefinition.place_id == null){    // We could do m.place_id but the java recommends to call the static members with class name itself.


            m.add_place_payload_with("Sai","English","Vijayawada");     // Sending payload by calling this method.
            m.user_calls_with_http_request("AddPlaceAPI","POST");   // Sending the resource data and the http request by calling this method.
            m.verify_the_place_id_generated_maps_to_using("Sai","getPlaceAPI");    // Getting place ID by calling this method.

        }
    }
}
