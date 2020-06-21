Feature: Login
  In order to perform successful navigation
  As a User
  I want to be able to reach the desired Webpage url

  Scenario: In order to Navigate a webpage
    Given User navigates to Webpage
    When User validates the Webpage title
    Then User navigates the webpage
    And User reaches the desired screen successfully
    When User verifies the screen is correct
    Then User can perform actions
