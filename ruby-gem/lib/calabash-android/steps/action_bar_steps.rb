Then /^I press the home button$/ do
	performAction('press_ab_home_button')
end

Then /^I press the action bar item with id "([^\"]*)"$/ do | id |
	performAction('press_ab_action_item')
end
