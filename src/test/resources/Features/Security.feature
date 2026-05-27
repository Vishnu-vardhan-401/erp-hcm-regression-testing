Feature: Security

  @CE_Time_Entry_Clerk
  Scenario: CE Time Entry Clerk
    Given the user is logged into Oracle HCM as a CE Time Entry Clerk and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE Time Entry Clerk role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE Time Entry Clerk role

  @CE_Compensation_Labor_Relations_Staff_Exclude_Retirees
  Scenario: CE Compensation Labor Relations Staff Exclude Retirees
    Given the user is logged into Oracle HCM as a CE Compensation Labor Relations Staff Exclude Retirees and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE Compensation Labor Relations Staff Exclude Retirees role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE Compensation Labor Relations Staff Exclude Retirees role

  @CE_Payroll_Staff_Full_Population
  Scenario: CE Payroll Staff Full Population
    Given the user is logged into Oracle HCM as a CE Payroll Staff Full Population and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE Payroll Staff Full Population role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE Payroll Staff Full Population role

  @CE_Tech_Support_View_Only_Data
  Scenario: CE Tech Support View Only Data
    Given the user is logged into Oracle HCM as a CE Tech Support View Only Data and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE Tech Support View Only Data role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE Tech Support View Only Data role
  
  @CE_Treasury_Full_Population
  Scenario: CE Treasury Full Population
    Given the user is logged into Oracle HCM as a CE Treasury Full Population and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE Treasury Full Population role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE Treasury Full Population role

  @CE_Compensation_Staff 
  Scenario: CE Compensation Staff 
    Given the user is logged into Oracle HCM as a CE Compensation Staff and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE Compensation Staff role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE Compensation Staff role