/*
Consider the below complex json where the fields have subfields and also an array.
We are going to understand how to parse such nested/complex json.

We wrote this json in the payload.java file in the ComplexJson method.

Note: Often times the dev will give us the expected response of the API even before the API is developed.
We don't need to wait until the API is developed and then start automating it.
Instead, we can start automating it based on the expected responses that the dev has given us.

Inorder to do that, we take the expected response from the dev and write it into a class and match the
response of the json parser with the response from the class

We can understand much better while we try to solve the below questions.

{
"dashboard": {
    "purchaseAmount": 910,
    "website": "rahulshettyacademy.com"
},
"courses": [
    {
    "title": "Selenium Python",
    "price": 50,
    "copies": 6
    },

    {
    "title": "Cypress",
    "price": 40,
    "copies": 4
    },

    {
    "title": "RPA",
    "price": 45,
    "copies": 10
    }
]
}

1. Print No of courses returned by API
2. Print Purchase Amount
3. Print Title of the first course
4. Print All course titles and their respective Prices
5. Print no of copies sold by RPA Course
6. Verify if Sum of all Course prices matches with Purchase Amount
 */

import files.payload;
import io.restassured.path.json.JsonPath;

public class ParseComplexJsons {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(payload.ComplexJson());

        //1. Print No of courses returned by API
        System.out.println("Number of courses: "+js.getInt("courses.size()"));      // There are 2 elements here. dashboard and courses. we are retrieving the size from courses.
                                                                                        // The size() can only be obtained for arrays as courses is an array.

        //2. Print Purchase Amount
        System.out.println("Total Purchase amount: "+js.getInt("dashboard.purchaseAmount"));    // Similarly we are accessing purchaseAmount from dashboard.

        //3. Print Title of the first course
        System.out.println(js.getString("courses[0].title"));       // As it is an array and we wish to get the title of first course, we can do it like a conventional array

        //4. Print All course titles and their respective Prices
        int coursesCount=js.getInt("courses.size()");       // We need the size of the courses array to run a for loop.
        System.out.println(coursesCount);
        for(int i=0;i<coursesCount;i++){
            System.out.println(js.getString("courses["+i+"].title"));   // While we iterate through the array we display every title and its corresponding price.
            System.out.println(js.getInt("courses["+i+"].price"));
        }

        //5. Print no of copies sold by RPA Course
        for(int i=0;i<coursesCount;i++){
            if("RPA".equals(js.getString("courses["+i+"].title"))) {    // Similarly we run a loo search for a match of "RPA" and print its corresponding copes sold.
                System.out.println(js.getString("courses["+i+"].title")+" Copies sold: "+js.getInt("courses["+i+"].copies"));
                break;  // Optimising this by using break to avoid further iterating once RPA is found.
            }
        }

        //6. Verify if Sum of all Course prices matches with Purchase Amount

        // Login written in booksSoldAmount.java

//        if(js.getInt("dashboard.purchaseAmount")==booksSoldAmount()){
//            System.out.println("Transaction amount match the booksSoldAmount");   // We can write in this way and actual process is written in booksSoldAmount.java
//        }
//        else{
//            System.out.println("Doesn't match");
//        }
    }
}
