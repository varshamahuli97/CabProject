package com.cab.models;

public class CabInfo {

	private Attributes attributes;
	private VehicleInfo vehicleInfo;
	private Position position;

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public VehicleInfo getVehichleInfo() {
		return vehicleInfo;
	}

	public void setVehichleInfo(VehicleInfo vehichleInfo) {
		this.vehicleInfo = vehichleInfo;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "CabInfo [attributes=" + attributes + ", vehicleInfo=" + vehicleInfo + ", position=" + position + "]";
	}
	

}
