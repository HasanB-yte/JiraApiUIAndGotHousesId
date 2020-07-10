Feature: Total number of issues per Assignee

  Scenario: get number of issues for Memis
    When the user sends get request to get total number of issues assigned to assignee on API
    Then the user validates total number of stories for same assignee on IU

    Scenario: get all ids and house on GOT
      When user sends a get request on APi to "https://api.got.show/api/map/characters"
      Then the user get all houses and ids and store them in a map

