package com.jayway.android.robotium.solo;

import java.util.ArrayList;
import java.util.List;

import android.app.Instrumentation;
import android.graphics.Point;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;

/**
 * @author Nicholas Albion
 */
public class MapViewUtils {
	private final Instrumentation inst;
	private final ViewFetcher viewFetcher;
	private final Sleeper sleeper;
	private final Waiter waiter;
	private final Clicker clicker;
	
	/**
	 * Constructs this object.
	 *
	 * @param inst the {@code Instrumentation} instance.
	 * @param viewFetcher the {@code ViewFetcher} instance.
	 * @param sleeper the {@code Sleeper} instance
	 */
	public MapViewUtils( Instrumentation inst, ViewFetcher viewFetcher, Sleeper sleeper, Waiter waiter, Clicker clicker) {
		this.inst = inst;
		this.viewFetcher = viewFetcher;
		this.sleeper = sleeper;
		this.waiter = waiter;
		this.clicker = clicker;
	}
	
	private MapView getMapView() {
		return waiter.waitForAndGetView( 0, MapView.class );
	}
	
	/**
	 * @param lat
	 * @param lon
	 */
	public void setCenter( double lat, double lon ) {
		MapView mapView = getMapView();
		mapView.getMap ().moveCamera (CameraUpdateFactory.newLatLng (new LatLng (lat, lon)));
	}
	
	public double[] getMapCenter() {
		MapView mapView = getMapView();
		LatLng center = mapView.getMap ().getCameraPosition ().target;
		return new double[] { center.latitude, center.longitude };
	}
	
	/**
	 * @param lat
	 * @param lon
	 */
	public void panTo( double lat, double lon ) {
		MapView mapView = getMapView();
		mapView.getMap().animateCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
	}
	
	public int getZoom() {
		MapView mapView = getMapView();
		return (int) mapView.getMap().getCameraPosition().zoom;
	}
	
	public int setZoom( int zoomLevel ) {
		MapView mapView = getMapView();
		mapView.getMap ().moveCamera (CameraUpdateFactory.zoomTo (zoomLevel));
		return zoomLevel;
	}
		
	public boolean zoomIn() {
		MapView mapView = getMapView();
		mapView.getMap ().moveCamera (CameraUpdateFactory.zoomIn ());
		return true;
	}
	
	public boolean zoomOut() {
		MapView mapView = getMapView();
		mapView.getMap ().moveCamera (CameraUpdateFactory.zoomOut ());
		return true;
	}
	
	/**
	 * @return [top, right, bottom, left] in decimal degrees
	 */
	public List<String> getBounds() {
		ArrayList<String> bounds = new ArrayList<String>(4);
		return bounds;
	}

	public boolean tapMapAt(final double lat, final double lon, final long timeout) {
		final Point coordinates = new Point ();

		inst.runOnMainSync(new Runnable() {
			@Override
			public void run() {
				MapView mapView = getMapView();
				GoogleMap map = mapView.getMap();
				LatLng location = new LatLng(lat, lon);

				Projection proj = map.getProjection();

				// Try not to change the map but if the location is not within screen realm we have to
				if (!proj.getVisibleRegion().latLngBounds.contains(location))
					map.moveCamera(CameraUpdateFactory.newLatLng(location));

				proj = map.getProjection();
				Point c = proj.toScreenLocation(location);
				int[] viewOnScreen = new int[2];
				mapView.getLocationOnScreen(viewOnScreen);
				coordinates.set(c.x + viewOnScreen[0], c.y + viewOnScreen[1]);
			}
		});

		clicker.clickOnScreen(coordinates.x, coordinates.y);

		return true;
	}
}
