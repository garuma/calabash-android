require 'json'

Then /^the shared preferences named "([^\"]*)" should( not)? contain the pair "([^\"]*)" = "([^\"]*)"$/ do | name, negate, key, value |
	result = performAction('get_preferences', name)
	if !result['success'] or result['bonusInformation'].empty?
		raise StandardError.new( "The shared preference named '#{name}' didn't exist or couldn't be retrieved" )
	end
	result = Hash[result['bonusInformation'].map do |x|
		              parsed = JSON[x]
		              [parsed['key'], parsed['value']]
	              end]
	if !result.has_key?(key)
		raise StandardError.new( "The key '#{key}' didn't exist" ) unless negate
	end
	if !negate and result[key] != value
		raise StandardError.new( "Expected value '#{value}' for key '#{key}' but got '#{result[key]}'" )
	end
	if negate and result[key] == value
		raise StandardError.new( "Expected value different than '#{value}' for key '#{key}'" )
	end
end

