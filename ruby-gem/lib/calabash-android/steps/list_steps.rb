# By default "get_list_item_text" returns an array of arrays of text for each entry in the first ListView
# The "get_list_item_text" action also supports: 
# (all items of 2nd list) <code>performAction( 'get_list_item_text', '2' )</code>
# (1st item of 2nd list) <code>performAction( 'get_list_item_text', '2' , '1' )</code>
Then /^I should see following list:$/ do | expected_table |
  result = performAction('get_list_item_text')
  response_table = result['bonusInformation']
  response_table.each_with_index do | row_data, index |
    row_data = JSON.parse( row_data )
    response_table[index] = row_data
  end
  
  expected_table.diff!(response_table)
end

# Note: This step is currently intended as more of an example rather than a rock-solid, well-tested step.
#       (The server implementation works well for me, but my test steps that use it are application-specific)
# Similarly to the "get_list_item_text" action, the "get_list_item_properties" action defaults to
# all rows of the first ListView, but can be instructed to target a specific row (or all rows) of a specific ListView.
# Example TextView row: {"id":"title", "text":"My Title", "color":0, "background":0, "compoundDrawables":["left"]}
# Example ViewGroup row: {"children":[{"id":"title", "text":"My Title"}, {"id":"subtitle", "text":"Second line"}]}
# Example TableLayout row: {"cells":[{"column":0, "id":"colA", "text": "This is a Test"}]}

def recursive_find_view(parent, view_id)
	parent['children'].each do | view |
		if (view['children'])
			found = recursive_find_view(view, view_id)
			return found unless !found
		end
		if (view['id'] == view_id)
			return view
		end
	end
end

Then /^the "([^\"]*)" for row (\d+) in list "([^\"]*)" should be "([^\"]*)"$/ do | view_id, row, listID, value |
	response = performAction('get_list_item_properties', row, listID)['bonusInformation']
	response = JSON.parse(response[0])

	if (response['children'])
		view = recursive_find_view(response, view_id)
		raise "Could not find view with ID: #{view_id}" unless view
		raise "Text is #{view['text']}, expected #{value}" unless view['text'] == value
	else
		raise "ID is #{response['id']}, expected #{view_id}" unless( response['id'] == view_id )
		raise "Text is #{response['text']}, expected #{view_id}" unless( response['text'] == value )
	end
end

Then /^I tap on row (\d+) of the list with id "([^\"]*)"$/ do | row, listId |
	performAction('press_list_item', row, listId)
end
