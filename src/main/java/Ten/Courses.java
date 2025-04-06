package Ten;

/*
    In the case of complex jsons like we are about to see right now, copy paste the json
    in jsoneditoronline.org and view a structured look of the json with nested jsons as well.
 */

import java.util.List;

public class Courses {      // Here we write Courses.java as it contains subfields of list of courses.

    private List<webAutomationcourse> webAutomation;        // Each courses again has some subfields so we write seperate classes for each course
    private List<apicourse> api;        // Visit webAutomationcourse.java, mobilecourse.java, apicourse.java
    private List<webAutomationcourse> mobile;       // As the courses have a list of subfields like course title and price. we declare these as List.

    public List<webAutomationcourse> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(List<webAutomationcourse> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public List<apicourse>  getApi() {
        return api;
    }

    public void setApi(List<apicourse>  api) {
        this.api = api;
    }

    public List<webAutomationcourse> getMobile() {
        return mobile;
    }

    public void setMobile(List<webAutomationcourse> mobile) {
        this.mobile = mobile;
    }




}
