package special.event;


import components.UserComponent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import repositories.UserRepository;


import special.notify.*;

import static org.junit.Assert.*;

public class NotificationSteps {
    String adminEmail;

    User serviceProvider;

    String userEmail;
    String SeviceMessage;
    String announcementMessage;
    UserComponent usercompo=new UserComponent();
    Notification notification = new Notification();
    User admin ;
    User user;
    @Given("the admin is on the notification page in the admin account with email {string}")
    public void theAdminIsOnTheNotificationPageInTheAdminAccount(String adminEmail) {
        this.adminEmail = adminEmail;
        this.admin = getUser(adminEmail);
        assertNotNull(admin);

    }


    @When("a user submits a request to create a business account from the email {string} and write the {string} of his service")
    public void aUserSubmitsARequestToCreateABusinessAccountFromTheEmailAndWriteTheOfHisService(String userEmail, String SeviceMessage) {
        for (int i = 0; i<UserRepository.reviw.size();i++){
            if(UserRepository.reviw.get(i).email.equals(userEmail)){
                user =UserRepository.reviw.get(i);
            }
        }
        this.SeviceMessage = SeviceMessage;
        user.message = SeviceMessage;
        notification.createAccountCreationRequest(user,SeviceMessage);
    }

    @When("the admin receives a notification regarding the request for a business account")
    public void theAdminReceivesANotificationRegardingTheRequestForABusinessAccount() {
        admin.addNotification(notification);
    }
    @When("the admin approves the request")
    public void theAdminApprovesTheRequest() {

        notification.setApproved(true);
       assertTrue(notification.isApproved());
    }
    @Then("a confirmation message {string} is sent to the user's email address {string}")
    public void aConfirmationMessageIsSentToTheUserSEmailAddress(String emailMessage, String userEmail) {
       //email message
        if(notification.isApproved()){
           System.out.println("Message sent !");
        }

    }



    @When("the admin rejects the request")
    public void theAdminRejectsTheRequest() {
        notification.setApproved(false);
        assertFalse(notification.isApproved());
    }
    @Then("a rejection message {string} is sent to the user's email address {string}")
    public void aRejectionMessageIsSentToTheUserSEmailAddress(String emailMessage, String userEmail) {
        //email message
        if(!notification.isApproved()){
            System.out.println("Message sent !");
        }

    }



    @Given("he admin is on the Announcement page in the admin account with email {string}")
    public void heAdminIsOnTheAnnouncementPageInTheAdminAccountWithEmail(String adminEmail) {
        this.adminEmail = adminEmail;
        this.admin = getUser(adminEmail);
        assertNotNull(admin);
    }
    @Given("the admin wants to send an announcement message to users {string}")
    public void theAdminWantsToSendAnAnnouncementMessageToUsers(String userEmail) {
        this.userEmail = userEmail;
        this.user = getUser(userEmail);
        assertNotNull(user);

    }
    @When("the admin submits the message {string}")
    public void theAdminSubmitsTheMessage(String announcementMessage) {
        this.announcementMessage = announcementMessage;
        notification.createAnnouncement(admin,announcementMessage);

        int size = UserRepository.users.size();
        for(int i=0;i<size;i++){
            if(!UserRepository.users.get(i).email.equals(adminEmail)){
                UserRepository.users.get(i).addNotification(notification);
            }
        }
    }
    @Then("all users receive the announcement message")
    public void allUsersReceiveTheAnnouncementMessage() {
        boolean allUsersReceived = true;
        for (User user : UserRepository.users) {
            if (!user.isNotificationExit(notification)&&!user.type.equals("ADMIN")) {
                allUsersReceived = false;
                break;
            }
        }
        assertTrue(allUsersReceived);
    }


    @When("a user with email {string} submits a reservation request with reservation details {string}")
    public void aUserWithEmailSubmitsAReservationRequest(String userEmail,String reservationDetails) {
        this.user = getUser(userEmail);
        assertNotNull(userEmail);
        notification.createReservationRequest(user,reservationDetails);

    }
    @When("the service provider {string} receives a notification regarding the reservation request")
    public void theServiceProviderReceivesANotificationRegardingTheReservationRequest(String providerEmail) {
        this.serviceProvider = getUser(providerEmail);
        assertNotNull(providerEmail);
        serviceProvider.addNotification(notification);
    }
    @When("the service provider approves the request")
    public void theServiceProviderApprovesTheRequest() {
        notification.setApproved(true);
        assertTrue(notification.isApproved());
    }
    @Then("a confirmation message {string} is sent to the user as notification")
    public void aConfirmationMessageIsSentToTheUserAsNotification(String string) {
        notification.createReplyMessage(serviceProvider,true);
        user.addNotification(notification);
        assertTrue(user.isNotificationExit(notification));
    }

    @Given("the service provider rejects the request")
    public void theServiceProviderRejectsTheRequest() {
        notification.setApproved(false);
        assertFalse(notification.isApproved());
    }
    @Then("a rejection message {string} is sent to the user as notification")
    public void aRejectionMessageIsSentToTheUserAsNotification(String string) {
        notification.createReplyMessage(serviceProvider,false);
        user.addNotification(notification);
        assertTrue(user.isNotificationExit(notification));
    }





    public User getUser(String email){
        int size = UserRepository.users.size();
        for(int i=0;i<size;i++){
            if(UserRepository.users.get(i).email.equals(email)){

                return UserRepository.users.get(i);
            }
        }
        return null;
    }
}
