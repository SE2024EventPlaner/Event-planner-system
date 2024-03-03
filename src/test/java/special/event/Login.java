package special.event;
import components.UserComponent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class Login {
    UserComponent userComponent = new UserComponent();
    String email = "";
    String password = "";
    User user;
    public Login(){

    }

    @When("user tries to login through the system")
    public void userTriesToLoginThroughTheSystem() {

    }
    @When("user enters the email {string}")
    public void userEntersTheEmail(String email) {
        // Write code here that turns the phrase above into concrete actions
        this.email = email;
    }
    @When("user enters the password {string}")
    public void userEntersThePassword(String password) {
        // Write code here that turns the phrase above into concrete actions
        this.password = password;
    }
    @Then("user login succeeds")
    public void userLoginSucceeds() {
        // Write code here that turns the phrase above into concrete actions
        this.user = this.userComponent.validateLogin(this.email, this.password);
       assertNotNull(this.user);
    }
    @Then("user page {string} appears")
    public void userPageAppears(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, this.user.getType());
        assertTrue(this.userComponent.validateUserType(this.user, string));
    }


    // next scenario
    @Then("user not login succeeds")
    public void userNotLoginSucceeds() {

        this.user = this.userComponent.validateLogin(this.email, this.password);
        assertNull(this.user);

    }
    @Then("A message appears that the password is wrong")
    public void aMessageAppearsThatThePasswordIsWrong() {
        System.out.println("The password is wrong");
    }


    @Then("A message appears that the email is wrong")
    public void aMessageAppearsThatTheEmailIsWrong() {
        System.out.println("The email is wrong");


    }

}
