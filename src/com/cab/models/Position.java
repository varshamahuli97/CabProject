package com.cab.models;

public class Position {

	public Position(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	private String latitude;
	private String longitude;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}