Feature:Core HR


   #HemalathaM
  @HRA_Manually_Create_New_Hire
  Scenario: HRA - Manually Create a new Hire
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Hire Employee page
    When the manager completes the hire setup page for a new employee and clicks Continue button
    And the manager completes the When and Why page for a new employee and clicks Continue button
    And the manager completes the Personal Details page for a new employee
    And the manager completes the National Identifier page for a new employee and clicks Continue button
    And the manager completes the Communication Info page with phone and email details and clicks Continue button
    And the manager completes the Address page for a new employee and clicks Continue button
    And the manager completes the Assignment Details page for a new employee and clicks Continue button
    And the manager reviews the assignment information and clicks Continue button
    And the manager completes the Payroll page for a new employee and clicks Continue button
    And the manager completes the Salary page for a new employee and clicks Submit button
    Then Clicks on Employee image and Clicks on Signout link

    
  @HRA_Emergency_Contacts
  Scenario: HRA - Family and Emergency Contacts
    Given the manager is logged into Oracle HCM as a HRA
    And is on the HRA Family and Emergency Contacts page
    When the manager adds a new family contact for a direct report with valid personal details, relationship information, emergency details, address details, and national identifier details
    And Click on the Submit Button-Family and Emergency Contact
    And the manager adds a coworker as contact for a direct report with valid details
    And Clicks on the Submit Button
    Then Clicks on Employee image and Clicks on Signout link

  @ESS_View_Compensation
  Scenario: ESS - Compensation - View My Compensation
    Given the employee is logged into Oracle HCM as an Employee
    And is on the View My Compensation page
    When the employee views compensation details
    Then Clicks on Employee image and Clicks on Signout link

  @ESS_Emergency_Contacts
  Scenario: ESS - Family and Emergency Contactss
    Given the employee is logged into Oracle HCM as an Employee
    And is on the Family and Emergency Contacts page
    When the employee adds a new family contact with valid personal details, relationship information, emergency details, address details, and national identifier details
    And Click on the Submit Button-Family and Emergency Contact
    And the employee adds a coworker as contact with valid details
    And Clicks on the Submit Button
    Then Clicks on Employee image and Clicks on Signout link

  
  @MSS_Activity_Center
  Scenario: MSS - Activity Center
    Given the manager is logged into Oracle HCM as a Manager
    And is on the Activity Center page
    When the manager views and manages team activities
    Then Clicks on Employee image and Clicks on Signout link

  @MSS_Location_Change
  Scenario: MSS - Location Change
    Given the manager is logged into Oracle HCM as a Manager
    And is on the Location Change page
    When the manager submits a location change request for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link
  
  @HRA_Manager_Assignment_Related_Change_Correct
  Scenario: HRA Manager - Assignment Related Change Correct
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Historical Assignment Related Change page
    When the manager submits a correction for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @HRA_Manager_Assignment_Related_Change_Delete
  Scenario: HRA Manager - Assignment Related Change Delete
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Historical Assignment Related Change page
    When the manager submits a deletion for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @HRA_AdHoc_Salary_Initiate
  Scenario: HRA - AdHoc Salary Initiate
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Ad Hoc Salary page for myteam
    When the manager initiates an ad hoc salary change request for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link
  
  @HRA_Working_Hours_Change
  Scenario: HRA - Working Hours - Change
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Working Hours Change page
    When the manager submits a working hours change request for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @MSS_Ad_Hoc_Salary_Approve
  Scenario: MSS - Ad Hoc Salary - Approve
    Given the manager is logged into Oracle HCM as a Manager
    And is on the Ad Hoc Salary page
    When the manager approves an ad hoc salary request for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link
  
  @ESS_Retirement
  Scenario: ESS - Retirement or Journey
    Given the employee is logged into Oracle HCM as an Employee
    And is on the Retirement or Resignation page
    When the employee submits retirement details with valid retirement date, action, and reason
    Then Clicks on Employee image and Clicks on Signout link
  
  @Compensation_Ad_Hoc_Salary_Approve
  Scenario: Compensation - Ad Hoc Salary - Approve
    Given the compensation manager is logged into Oracle HCM as a Compensation Manager
    And is on the Ad Hoc Salary page
    When the compensation manager approves an ad hoc salary request for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @HRA_Manually_Create_Hire_L503
  Scenario: HRA - Manually Create a new Hire - L503
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Hire Employee page
    When the manager completes the hire setup page for a new employee and clicks Continue button
    And the manager completes the When and Why page for a new employee and clicks Continue button
    And the manager completes the Personal Details page for a new employee
    And the manager completes the National Identifier page for a new employee and clicks Continue button
    And the manager completes the Communication Info page with phone and email details and clicks Continue button
    And the manager completes the Address page for a new employee and clicks Continue button
    And the manager completes the Assignment Details page for a new employee and clicks Continue button
    And the manager reviews the assignment information and clicks Continue button
    # And the manager completes the Payroll page for a new employee and clicks Continue button
    # And the manager completes the Salary page for a new employee and clicks Submit button
    Then Clicks on Employee image and Clicks on Signout link

  
  @HRA_Manually_Create_Hire_MgtNonOfficer
  Scenario: HRA - Manually Create a new Hire - Mgt non Officer
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Hire Employee page
    When the manager completes the hire setup page for a new employee and clicks Continue button
    And the manager completes the When and Why page for a new employee and clicks Continue button
    And the manager completes the Personal Details page for a new employee
    And the manager completes the National Identifier page for a new employee and clicks Continue button
    And the manager completes the Communication Info page with phone and email details and clicks Continue button
    And the manager completes the Address page for a new employee and clicks Continue button
    And the manager completes the Assignment Details page for a new employee and clicks Continue button
    And the manager reviews the assignment information and clicks Continue button
    And the manager completes the Payroll page for a new employee and clicks Continue button
    And the manager completes the Salary page for a new employee and clicks Submit button
    Then Clicks on Employee image and Clicks on Signout link


  # @Compensation_Ad_Hoc_Salary_Approve
  # Scenario: Compensation - Ad Hoc Salary - Approve
  #   Given the compensation manager is logged into Oracle HCM as a Compensation Manager
  #   And is on the Ad Hoc Salary page
  #   When the compensation manager approves an ad hoc salary request for a direct report with valid details
  #   Then Clicks on Employee image and Clicks on Signout link
  
  @ESS_Retirement_or_Withdrawal
  Scenario: ESS - Retirement or Withdrawal
    Given the employee is logged into Oracle HCM as an Employee
    And is on the Retirement or Resignation page for withdrawal
    When The employee withdrawa the retirement request
    Then Clicks on Employee image and Clicks on Signout link

  @HRA_Cancel_Work_Relationship
  Scenario: HRA Manager - Cancel  Work Relationship
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Work Relationship page
    When the manager cancels a work relationship for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @ESS_Resignation
  Scenario:ESS - Resignation or Journey
    Given the employee is logged into Oracle HCM as an Employee
    And is on the Retirement or Resignation page
    When the employee submits resignation details with valid resignation date, action, and reason
    Then Clicks on Employee image and Clicks on Signout link
  

  @HRA_Direct_Reports_Change
  Scenario: HRA - Direct Reports - Change
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Direct Reports page
    When the manager changes the reporting manager for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link


  #Ketharajus
   @HRA_Manually_Create_New_Hire_L1-2
  Scenario: HRA - Manually Create a new Hire - L1-2
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Hire Employee page
    When the manager completes the hire setup page for a new employee and clicks Continue button
    And the manager completes the When and Why page for a new employee and clicks Continue button
    And the manager completes the Personal Details page for a new employee
    And the manager completes the National Identifier page for a new employee and clicks Continue button
    And the manager completes the Communication Info page with phone and email details and clicks Continue button
    And the manager completes the Address page for a new employee and clicks Continue button
    And the manager completes the Assignment Details page for a new employee and clicks Continue button
    And the manager reviews the assignment information and clicks Continue button
    And the manager completes the Payroll page for a new employee and clicks Continue button
    And the manager completes the Salary page for a new employee and clicks Submit button
    Then Clicks on Employee image and Clicks on Signout link


  @ESS_Resignation_OR_Withdrawal
  Scenario: ESS - Resignation or Withdrawal
    Given the employee is logged into Oracle HCM as an Employee
    And is on the Retirement or Resignation page for withdrawal
    When The employee withdrawa the resignation request
    Then Clicks on Employee image and Clicks on Signout link


  @MSS_View_Compensation_Info
  Scenario: MSS - Compensation Info - View Compensation Info
    Given the employee is logged into Oracle HCM as an Employee
    And is on the View My Compensation page
    When the employee views compensation details
    Then Clicks on Employee image and Clicks on Signout link

  @HRA_Location_Change
  Scenario: HRA - Location Change
    Given the manager is logged into Oracle HCM as a Manager
    And is on the HRA Location Change page
    When the manager submits a HRA location change request for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @MSS_Enter_Retirement
  Scenario: MSS - Enter Employee Retirement 
    Given the manager is logged into Oracle HCM as a Manager
    And is on the Terminate Employment page
    When the manager submits retirement details for a direct report with valid retirement date, action, and reason
    Then Clicks on Employee image and Clicks on Signout link

    
  @HRA_Add_Document_Records
  Scenario: HRA - Add Document Records
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Document Records page
    When the manager adds a new document record for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @MSS_Direct_Reports_Change
  Scenario: MSS - Direct Reports - Change
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Direct Reports page
    When the manager changes the reporting manager for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @HRA_Transfer_Initiate
  Scenario: HRA - Transfer - Initiate
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Transfer page
    When the manager initiates a transfer for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  #  @MSS_Ad_Hoc_Salary_Approve
  # Scenario: MSS - Ad Hoc Salary - Approve
  #   Given the manager is logged into Oracle HCM as a Manager
  #   And is on the Ad Hoc Salary page
  #   When the manager approves an ad hoc salary request for a direct report with valid details
  #   Then Clicks on Employee image and Clicks on Signout link

  @HRA_Transfer_Global
  Scenario: HRA - Transfer - Global
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Transfer page
    When the manager initiates a global transfer for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @Compensation_Parity_AddorView
  Scenario: Compensation - Parity - Add or View
    Given the compensation manager is logged into Oracle HCM as a Compensation Manager
    And is on the View My Compensation page
    When the compensation manager adds or views parity details for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  @MSS_Promote_ApproveOrDeny
  Scenario: MSS - Promote - Approve or Deny
    Given the manager is logged into Oracle HCM as a Manager
    And is on the Promotion or Deny page
    When the manager approves a promotion request for a direct report with valid details
    Then Clicks on Employee image and Clicks on Signout link

  # #HemalathaM
  # @HRA_Manually_Create_New_Hire
  # Scenario: HRA - Manually Create a new Hire
  #   Given the manager is logged into Oracle HCM as a HRA
  #   And is on the Hire Employee page
  #   When the manager completes the hire setup page for a new employee and clicks Continue button
  #   And the manager completes the When and Why page for a new employee and clicks Continue button
  #   And the manager completes the Personal Details page for a new employee
  #   And the manager completes the National Identifier page for a new employee and clicks Continue button
  #   And the manager completes the Communication Info page with phone and email details and clicks Continue button
  #   And the manager completes the Address page for a new employee and clicks Continue button
  #   And the manager completes the Assignment Details page for a new employee and clicks Continue button
  #   And the manager reviews the assignment information and clicks Continue button
  #   And the manager completes the Payroll page for a new employee and clicks Continue button
  #   And the manager completes the Salary page for a new employee and clicks Submit button
  #   Then Clicks on Employee image and Clicks on Signout link

  @HRA_Manually_Create_New_Hire_L3
  Scenario: HRA - Manually Create a new Hire - L3
    Given the manager is logged into Oracle HCM as a HRA
    And is on the Hire Employee page
    When the manager completes the hire setup page for a new employee and clicks Continue button
    And the manager completes the When and Why page for a new employee and clicks Continue button
    And the manager completes the Personal Details page for a new employee
    And the manager completes the National Identifier page for a new employee and clicks Continue button
    And the manager completes the Communication Info page with phone and email details and clicks Continue button
    And the manager completes the Address page for a new employee and clicks Continue button
    And the manager completes the Assignment Details page for a new employee and clicks Continue button
    And the manager reviews the assignment information and clicks Continue button
    And the manager completes the Payroll page for a new employee and clicks Continue button
    And the manager completes the Salary page for a new employee and clicks Submit button
    Then Clicks on Employee image and Clicks on Signout link


  # @MSS_Ad_Hoc_Salary_Approve
  # Scenario: MSS - Ad Hoc Salary - Approve
  #   Given the manager is logged into Oracle HCM as a Manager
  #   And is on the Ad Hoc Salary page
  #   When the manager approves an ad hoc salary request for a direct report with valid details
  #   Then Clicks on Employee image and Clicks on Signout link

  #Hema latestcode update
  @ESS_Document_Records
  Scenario:ESS - Document Records View or download
    Given the employee is logged into Oracle HCM as an Employee
    And is on the Document Records pages
    When the employee views or downloads their document records
    Then Clicks on Employee image and Clicks on Signout link
 
  @ESS_Activity_Center
  Scenario:ESS - My Activity Center
    Given the employee is logged into Oracle HCM as an Employee
    And is on the My Activity Center page
    When the employee views and manages their activities
    Then Clicks on Employee image and Clicks on Signout link
 
  @ESS_Name_Change
  Scenario:ESS - Name Change - Submit
    Given the employee is logged into Oracle HCM as an Employee
    And is on the Name Change page
    When the employee submits a name change request with valid name change details
    Then Clicks on Employee image and Clicks on Signout link
 
 
  @HRA_Name_Change_Approve_Submit
  Scenario: HRA - Name Change Approve - Submit
     Given the manager is logged into Oracle HCM as a HRA
    When the manager opens the transfer from the worklist
    And approves or rejects the transfer
    Then Clicks on Employee image and Clicks on Signout link
 
 
  @Validate_Updated_Name
  Scenario: ESS - Name Change - Validate
    Given the manager is logged into Oracle HCM as a HRA
    And the user opens Personal Details
    When the updated name is displayed
    Then Clicks on Employee image and Clicks on Signout link
 
 
  @MSS-Transfer-Approve_or_Deny
  Scenario: Manager processes transfer approval or Deny
     Given the manager is logged into Oracle HCM as a HRA
    When the manager opens the transfer from the worklist MSS
    And approves or rejects the transfer MSS
    Then Clicks on Employee image and Clicks on Signout link
 
  @HRA-Promote-Initiate
  Scenario: HRA-Promote-Initiate
  Given the manager is logged into Oracle HCM as a HRA
  When User searches for the employee and selects Promote
  And User enters promotion details and submits
  Then Clicks on Employee image and Clicks on Signout link
 
 @HRA-Non-Worker-Surviving_Spouse-Add
 Scenario: HRA - Non-Worker - Surviving Spouse - Add
  Given the manager is logged into Oracle HCM as a HRA
  When User searches employee and selects Add Non-Worker
  And User enters required details and submits
  Then Clicks on Employee image and Clicks on Signout link
 
  @HRA_Change_Assignment_Management_to_Union
    Scenario: HRA Change Assignment Management to Union
    Given the manager is logged into Oracle HCM as a HRA
    When I change the assignment for a management employee with reason "Return to Union"
    And the assignment is updated successfully
    Then Clicks on Employee image and Clicks on Signout link
    And Delete the Created record
 
 
  @MSS-Manager_or_Supervisor-Change
  Scenario: Change manager for an employee
   Given the manager is logged into Oracle HCM as a HRA
    When User changes manager for an employee
    Then Manager change request is submitted successfully
    Then Clicks on Employee image and Clicks on Signout link

  # @MSS_Ad_Hoc_Salary_Approve
  # Scenario: MSS - Ad Hoc Salary - Approve
  #   Given the manager is logged into Oracle HCM as a Manager
  #   And is on the Ad Hoc Salary page
  #   When the manager approves an ad hoc salary request for a direct report with valid details
  #   Then Clicks on Employee image and Clicks on Signout link

#  @Compensation_Ad_Hoc_Salary_Approve
#   Scenario: Compensation - Ad Hoc Salary - Approve
#     Given the compensation manager is logged into Oracle HCM as a Compensation Manager
#     And is on the Ad Hoc Salary page
#     When the compensation manager approves an ad hoc salary request for a direct report with valid details
#     Then Clicks on Employee image and Clicks on Signout link

