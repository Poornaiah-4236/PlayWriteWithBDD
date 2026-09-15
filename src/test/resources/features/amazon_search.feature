Feature: Amazon product search
  As a shopper on Amazon
  I want to search for products
  So that I can find items relevant to my search term

  @Amazon
  Scenario Outline: Search returns relevant results
    Given I am on the Amazon home page
    When I search for product "<product>"
    Then search results should be displayed
    And each result title should contain "<product>"

    Examples:
      | product     |
      | phones      |
      | laptops     |
      | headphones  |

  @Amazon
  Scenario: Search with a nonsense term returns no results
    Given I am on the Amazon home page
    When I search for product "zzznonexistentproductxyz123"
    Then no results message should be displayed
