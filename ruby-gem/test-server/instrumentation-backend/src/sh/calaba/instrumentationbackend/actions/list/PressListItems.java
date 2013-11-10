package sh.calaba.instrumentationbackend.actions.list;


import junit.framework.Assert;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;


public class PressListItems implements Action {

    @Override
    public Result execute(String... args) {
    	int line = Integer.parseInt(args[0]);
    	AbsListView absListView = (AbsListView) InstrumentationBackend.solo.getView(args[1]);
    	Assert.assertNotNull("ListView is null!", absListView);

    	int numberOfLines = absListView.getChildCount();
    	if (line > absListView.getChildCount())
            Assert.assertTrue("Can not click on line number " + line + " as there are only " + numberOfLines + " lines available", false);

    	View view = absListView.getChildAt(line);
		if(view != null) {
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			location[0] += view.getWidth() / 2;
			location[1] += view.getHeight() / 2;
			Log.i("PressListItems", String.format("Final location: %d, %d", location[0], location[1]));
			InstrumentationBackend.solo.clickOnScreen(location[0], location[1]);
    		return Result.successResult();
		} else {
			return Result.failedResult();
		}
    }

    @Override
    public String key() {
        return "press_list_item";
    }

}
