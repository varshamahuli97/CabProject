package com.cab.models;

public class Attributes {

	private String tripStatus;
	private Position heading;
	private String soc;

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public Position getHeading() {
		return heading;
	}

	public void setHeading(Position heading) {
		this.heading = heading;
	}

	public String getSoc() {
		return soc;
	}

	public void setSoc(String soc) {
		this.soc = soc;
	}

	@Override
	public String toString() {
		return "Attributes [tripStatus=" + tripStatus + ", heading=" + heading + ", soc=" + soc + "]";
	}
	

}