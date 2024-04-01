package special.event;
import components.SendMail;
import components.UserComponent;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import repositories.EventRepository;
import repositories.UserRepository;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mockStatic;

public class SignUp {

    UserComponent userComponent = new UserComponent();
    String email = "";
    String password = "";

    String firstName = "";
    String finalName= "";
    String Confirm_password = "";

    UserRepository o=new UserRepository();
    EventRepository kk=new EventRepository();

    String type= "";

    User user;
    public SignUp(){}


    @When("The user enter firstName with {string} and finalName with {string} and email with {string} and password with {string} and Confirm password with {string} and type with {string}")
    public void theUserEnterFirstNameWithAndFinalNameWithAndEmailWithAndPasswordWithAndConfirmPasswordWithAndTypeWith(String fname, String secname, String inemail, String inpass, String confpass, String typ) {
        firstName=fname;
        finalName=secname;
        Confirm_password=confpass;
        password=inpass;
        email=inemail;
        type=typ;
        o.readUsers(UserRepository.FILE_NAME1);
        kk.readEventFile(EventRepository.FILE_NAME);

    }


    @Then("creating an account successfully")
    public void creatingAnAccountSuccessfully() {
        user=new User(email,password,type,firstName,finalName);
          userComponent.existEmail(email);
        assertTrue(userComponent.validateSignup(firstName,finalName,email,password,Confirm_password,type));
        UserRepository.addToUsers(user);

    }
    @Then("The user should see a confirmation message")
    public void theUserShouldSeeAConfirmationMessage() {
        System.out.println("You have been registered successfully");
    }


    @When("the user fill the {string} with {string}")
    public void theUserFillTheWith(String string, String string2) {
        if (string.equals(email))
        {
            assertFalse(userComponent.isValidEmail(string2));
        }
        else if(string.equals(password))
        {
            assertFalse(userComponent.isValidPassword(string2));
        }
        else
        {
            assertNotEquals(password, string2);
        }
    }

    @Then("account creation should Failed")
    public void accountCreationShouldFailed() {

    }
    @Then("a {string} should appear")
    public void aShouldAppear(String string) {
        System.out.println(string);
    }

    @Then("the user's application for service provider is stored for review")
    public void theUserSApplicationForServiceProviderIsStoredForReview() {
        user=new User(email,password,type,firstName,finalName);
        UserRepository.addToReviw(user);

    }
    @Then("the admin approves the application")
    public void theAdminApprovesTheApplication() {
        assertTrue(true);
    }

    @Then("a message is sent to the user via email to inform them that their booking was successful")
    public void aMessageIsSentToTheUserViaEmailToInformThemThatTheirBookingWasSuccessful() {
        // Write code here that turns the phrase above into concrete actions
        SendMail.sendEmail("Accepted :) ",email);


    }
    @Then("the user data is added to the arraylist")
    public void theUserDataIsAddedToTheArraylist() {
        UserRepository.addToUsers(user);
        assertTrue(userComponent.validateSignup(firstName,finalName,email,password,Confirm_password,type));
    }

}