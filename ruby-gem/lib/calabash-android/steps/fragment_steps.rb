Then /^I should see the fragment whose (name|id) is "([^\"]*)"$/ do | kind, value |
	performAction('wait_for_fragment', kind, value)
end
