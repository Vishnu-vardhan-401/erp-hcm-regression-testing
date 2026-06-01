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
  
  @CE_HR_Employment_Data_View_Only_Excl_Exec_Retiree_LEB
  Scenario: CE HR Employment Data View Only Excl Exec Retiree LEB
    Given the user is logged into Oracle HCM as a CE HR Employment Data View Only Excl Exec Retiree LEB and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE HR Employment Data View Only Excl Exec Retiree LEB role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE HR Employment Data View Only Excl Exec Retiree LEB role
  
  @CE_HR_Employment_Data_View_Only_Full_Population
  Scenario: CE HR Employment Data View Only Full Population
    Given the user is logged into Oracle HCM as a CE HR Employment Data View Only Full Population and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE HR Employment Data View Only Full Population role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE HR Employment Data View Only Full Population role
  
  @CE_HR_Director_Full_Population
  Scenario: CE HR Director Full Population
    Given the user is logged into Oracle HCM as a CE HR Director Full Population and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE HR Director Full Population role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE HR Director Full Population role

  @CE_HR_Data_View_Only_LI_Data_Analytics_Full_Population
  Scenario: CE HR Data View Only LI Data Analytics Full Population
    Given the user is logged into Oracle HCM as a CE HR Data View Only LI Data Analytics Full Population and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE HR Data View Only LI Data Analytics Full Population role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE HR Data View Only LI Data Analytics Full Population role

  @CE_HR_QA_Compliance_Full_Population
  Scenario: CE HR QA Compliance Full Population
    Given the user is logged into Oracle HCM as a CE HR QA Compliance Full Population and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the CE HR QA Compliance Full Population role
    Then the user should verify that only the approved tiles and menu items are enabled for the CE HR QA Compliance Full Population role
  
  @HR_Production_Support
  Scenario: HR Production Support
    Given the user is logged into Oracle HCM as a HR Production Support and on Dashboard
    When the user reviews the Quick Action items and tiles displayed for the HR Production Support role
    Then the user should verify that only the approved tiles and menu items are enabled for the HR Production Support role
    