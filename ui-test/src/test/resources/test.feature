Feature: Test stuff is wired up properly

	Scenario:
		Given User navigates to https://www.google.co.uk
		Then User is on https://www.google.co.uk

	Scenario:
		Given User navigates to https://www.google.co.uk
		When User types wik in Google search bar
		And User types ipe in Google search bar
		And User types dia in Google search bar
		Then Google URL contains wikipedia