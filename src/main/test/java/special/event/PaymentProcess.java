package special.event;

import components.Checker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import components.UserComponent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PaymentProcess {
    UserComponent userComponent = new UserComponent();
    String email = "";
    String credit="";
    UserComponent usercompo=new UserComponent();

    @Given("the user has logged in with email as {string}")
    public void theUserHasLoggedInWithEmailAs(String string) {
        this.email = string;
        assertTrue(usercompo.existEmail(string));

    }
    @When("the user enters their valid credit card information credit card number as {string}")
    public void theUserEntersTheirValidCreditCardInformationCreditCardNumberAs(String string) {
        credit=string;
    }
    @When("the user confirms the payment")
    public void theUserConfirmsThePayment() {
        assertTrue(Checker.checkStringLength(credit));
    }
    @Then("a payment confirmation message indicating the successful process is displayed to the user")
    public void aPaymentConfirmationMessageIndicatingTheSuccessfulProcessIsDisplayedToTheUser() {
        System.out.println("operation accomplished successfully");
    }



    @When("the user enters their invalid credit card information credit card number as {string}")
    public void theUserEntersTheirInvalidCreditCardInformationCreditCardNumberAs(String string) {
        assertFalse(Checker.checkStringLength(credit));

    }
    @Then("the system should display a message indicating that the credit card number is invalid")
    public void theSystemShouldDisplayAMessageIndicatingThatTheCreditCardNumberIsInvalid() {
        System.out.println("The operation failed");

    }

}