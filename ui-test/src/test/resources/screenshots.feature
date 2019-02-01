Feature: Test screenshot tool

	Scenario: Google logo matches design
		Given User navigates to https://www.google.co.uk
		Then Google logo matches design

	Scenario: Google search page matches design
		Given User navigates to https://www.google.co.uk
		Then Google search page matches design