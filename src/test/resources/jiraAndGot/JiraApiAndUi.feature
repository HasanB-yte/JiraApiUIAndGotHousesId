Feature: Generate stories and bug on Jira

  Scenario Outline: jira login

    When the user uses Post method to generate a authorization cookie for Jira back end testing
    And the user creates user stories via APi with "<summary>", "<description>", "<issueType>"
    Then the user verifies on ui "<summary>", "<description>", "<issueType>"
    Examples:
      | summary                   | description                                                               | issueType |
      | Techtorial student portal | As a Techtorial student, I'd like to be able login to see my account      | Story     |
      | Amazon product listing    | As an Amazon user, i'd like to see all my orders when I login             | Story     |
      | Facebook news feed        | As a Facebook user, I don't like to see news feed that i'm not subscribed | Story     |
      | Att 5G signal             | As an ATT customer, I dn't want ot see fake 5G icon                       | Story     |
      | DirecTv                   | As a DirecTv customer, in guide I'd like to only see my favorite channels | Story     |
      | Techtorial login button   | Techtorail student login button not responding                            | Bug       |
      | Amazon order listing      | orders not visible                                                        | Bug       |
      | Facebook friends list     | there are individuals in the frieds list whom are not accepted as friend  | Bug       |
      | Att 5G                    | Att signal is not G                                                       | Bug       |
      | Directv guide             | channel listing is not correct                                            | Bug       |



