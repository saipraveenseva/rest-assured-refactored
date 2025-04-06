Feature: Validating Place APIs
  @AddPlaceGetPlace
  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the API call is success with status code 200
    And check "status" in response body is "OK"
    And validate "scope" in response body is "APP"
    And verify the place_id generated maps to "<name>" using "getPlaceAPI"

  Examples:
    |name | language |address  |
    |Seva | English  |Hyderabad|
    # |Sai | Telugu  |Vijayawada |
    # |Praveen | Hindi  |Allahabad|

  @DeletePlace    # Including tags like these will assign some tags to the testcase which we can use to skip or not skip a particular testcase
  Scenario: Verify if Delete place functionality is working.
    Given DeletePlace payload
    When user calls "deletePlaceAPI" with "POST" http request
    Then the API call is success with status code 200
    And check "status" in response body is "OK"

    # We have written testcase for deletePlace as well. When, Then and And are already written in the above testcase. All we have to implement is Given.


    # we can keep adding multiple data sets here. 3 are added so the framework will run 3 times.Ability:
    # Easy, Handy and readable to keep adding data sets for the framework to run on.
    # FYI the logging.txt will have the logs of the last data set only.
    # To avoid it we make necessary changes in Utils.java to get all the logs in logging.txt.

    #As we are writing Examples we change the "Scenario" to Scenario Outline"

    #We are providing the data for name, language and address attributes from here. We need to change the skeleton as well.
    # For that just run the TestRunner.java and copy the code from the output console.
    # is used to write comments in Gherkin

  # This is where the Cucumber BDD framework begins. We write it from scratch

  # We will link the AddPlace API to every line of the above code.
  # We do the linkage in stepDefinitions.java file

  #"AddPlaceAPI" is written in quotes indicating that I can replace it with DeleteAPI or any other API in the future.
  # Instead of writing a seperate statement we just replaces what's in the quotes.

  # same goes for "status", "scope" or the field "OK" or "APP"

