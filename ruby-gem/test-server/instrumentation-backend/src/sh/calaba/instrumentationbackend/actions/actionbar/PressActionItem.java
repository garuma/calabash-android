package sh.calaba.instrumentationbackend.actions.actionbar;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;

public class PressActionItem implements Action {
	@Override
    public Result execute(String... args) {
		int id = TestHelpers.getIdFromString(args[0]);
		if (id == 0)
			return new Result(false, "Provided id didn't exists: '" + args[0] + "'");

        InstrumentationBackend.solo.clickOnActionBarItem(id);
        return Result.successResult();
    }

    @Override
    public String key() {
        return "press_ab_action_item";
    }
}
