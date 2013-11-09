package sh.calaba.instrumentationbackend.actions.map;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;

/**
 * eg: tap_map_marker_by_title, "my marker"
 * eg: tap_map_marker_by_title, "my marker", "10000"   (keep trying for 10 seconds)
 * 
 * @author Nicholas Albion
 */
public class TapMap implements Action {

	@Override
	public Result execute(String... args) {
		double lat = Double.parseDouble(args[0]);
		double lon = Double.parseDouble(args[1]);
		long timeout = (args.length > 2) ? Long.parseLong(args[2]) : 10000;
		if( InstrumentationBackend.solo.tapMap(lat, lon, timeout) ) {
			return Result.successResult();
		}
		return new Result(false, "Could not tap map at '" + lat + ", " + lon + "' to tap after waiting " + timeout + " ms");
	}

	@Override
	public String key() {
		return "tap_map_at_position";
	}
}
