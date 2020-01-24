package com.cab.models;
public class VehicleInfo {

private String name;
private String vehicleIdentificationnumber;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getVehicleIdentificationnumber() {
return vehicleIdentificationnumber;
}

public void setVehicleIdentificationnumber(String vehicleIdentificationnumber) {
this.vehicleIdentificationnumber = vehicleIdentificationnumber;
}

@Override
public String toString() {
	return "VehicleInfo [name=" + name + ", vehicleIdentificationnumber=" + vehicleIdentificationnumber + "]";
}

}