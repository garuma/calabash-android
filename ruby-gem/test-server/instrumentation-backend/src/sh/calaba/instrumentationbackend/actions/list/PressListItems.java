package sh.calaba.instrumentationbackend.actions.list;


import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;


public class PressListItems implements Action {

    @Override
    public Result execute(String... args) {
    	int id = TestHelpers.getIdFromString(args[1]);
    	InstrumentationBackend.solo.clickInList(Integer.parseInt(args[0]), id);
    	return Result.successResult();
    }

    @Override
    public String key() {
        return "press_list_item";
    }

}
