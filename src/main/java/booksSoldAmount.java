import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class booksSoldAmount {
    @Test   // @Test indicates the editor that its a testng file. This file does not require a main function to run.

    public void SoldAmount(){
        JsonPath js = new JsonPath(payload.ComplexJson());
        int coursesCount = js.getInt("courses.size()");
        int booksSoldAmount=0;
        for(int i=0;i<coursesCount;i++) {   // iterating through a loop and multiplying the copies sold with the price to get the total purchase amount
            booksSoldAmount = booksSoldAmount + (js.getInt("courses[" + i + "].price") * js.getInt("courses[" + i + "].copies"));
        }

        Assert.assertEquals(booksSoldAmount,js.getInt("dashboard.purchaseAmount"));
        // Writing an assertion to compare both the purchase amount and the amount that we got by calculating.
        // If the counts doesn't match the editor will throw an error. If not there won't be an error/success message displayed.

    }


}
