package sh.calaba.instrumentationbackend.actions.fragment;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;

public class WaitForFragment implements Action {
	@Override
    public Result execute(String... args) {
		boolean isID = args[0] == "id";
		boolean result = false;
		if (isID) {
			int id = TestHelpers.getIdFromString(args[1]);
			result = InstrumentationBackend.solo.waitForFragmentById(id);
		} else
			result = InstrumentationBackend.solo.waitForFragmentByTag(args[1]);
		return result ? Result.successResult() : Result.failedResult();
    }

    @Override
    public String key() {
        return "wait_for_fragment";
    }
}
