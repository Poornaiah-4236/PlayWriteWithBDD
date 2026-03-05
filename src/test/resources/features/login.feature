#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Sample Feature Definition Template
@LoginTest
Feature: Title of your feature
  @LoginTest
  Scenario Outline: Login Test
  Given User launches the application
  #When User enters username "<username>" and password "<password>"
  #Then User should see homepage

Examples:
  | username | password |
  | admin    | admin123 |
  @Amazon
  Scenario Outline: Amazon product search
  Given User launches the application
  When User searches for product "<product>"
  #Then User should see homepage

Examples:
  | product | password |
  | phones    | admin123 |
  
