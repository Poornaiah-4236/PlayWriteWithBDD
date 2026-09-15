Feature: Login
  As a user of the Practice Test Automation login page
  I want to log in with my credentials
  So that I can access the protected area

  @Login @Smoke
  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I log in with username "student" and password "Password123"
    Then I should see the "Logged In Successfully" message
    And I should see a logout link

  @Login @Negative
  Scenario Outline: Login fails with invalid credentials
    Given I am on the login page
    When I log in with username "<username>" and password "<password>"
    Then I should see an error message "<errorMessage>"

    Examples:
      | username      | password      | errorMessage                  |
      | incorrectUser | Password123   | Your username is invalid!     |
      | student       | incorrectPass | Your password is invalid!     |
