package sh.calaba.instrumentationbackend.actions.actionbar;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;

public class PressHomeButton implements Action {
	@Override
    public Result execute(String... args) {
		InstrumentationBackend.instrumentation.runOnMainSync(new Runnable() {
			public void run() {
				InstrumentationBackend.solo.clickOnActionBarHomeButton ();
			}
		});
        return Result.successResult();
    }

    @Override
    public String key() {
        return "press_ab_home_button";
    }
}
