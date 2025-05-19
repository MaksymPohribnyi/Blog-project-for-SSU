@Login
Feature: Login
  As a Registered User of the application
  I want to validate the Login functionality
  In order to check if it works as desired

  Background: A Registered User navigates to Login page
    Given I am a registered user
    And I navigate to the "Login" page

  @SuccessfulLogin
  Scenario Outline: Successful login using valid credentials
    When I fill in "username" with "<username>"
    And I fill in "password" with "<password>"
    And I click on the "Log In" button
    Then I should be successfully logged in
    And I should land on the "Home" page
    And I should see "Profile" and "Logout" links

    Examples: 
      | username  | password  |
      | asdf.asdf | Asdf@1234 |

  @failedLogin
  Scenario Outline: Failed login using wrong credentials
    When I fill in "username" with "<username>"
    And I fill in "password" with "<password>"
    And I click on the "Log In" button
    Then I should be redirected on the "Login" page
    And I should see "error" message as "<alert>"

    Examples: 
      | username | password | alert                                 |
      | araj     | !23      | Invalid username/password, Try again! |
      | ARAJ     | !23      | Invalid username/password, Try again! |
      | aRaJ     | !23      | Invalid username/password, Try again! |
      | Test     | !23      | Invalid username/password, Try again! |
