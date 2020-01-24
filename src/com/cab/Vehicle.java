package com.cab;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cab.models.Attributes;
import com.cab.models.CabInfo;
import com.cab.models.Position;
import com.cab.models.Track;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Vehicle extends Thread {
	private static final String STOP = "stop";
	private static final String PAUSE = "pause";
	private static final String START = "start";
	private static final String positions = "position";
	private static final String status = "tripStatus";
	private static final String longitude = "longitude";
	private static final String attributes = "attributes";
	private static final String latitude = "latitude";
	private String name;
	private String vehicleId;
	private Position position;
	private Position[] track;
	private String statusOfCharge;
	private Position nextPosition;
	private String tripStatus;
	private String vehicleJson;
	private Thread vehicle;
	private Scanner sc;
	private CabInfo a;

	Vehicle(int track_id, String filename) {
		vehicleJson = filename;
		FileReader jsonFile;
		try {
			jsonFile = new FileReader(vehicleJson);
			JSONObject jo = (JSONObject) new JSONParser().parse(jsonFile);
			jsonFile.close();
			a = new Gson().fromJson(jo.toString(), CabInfo.class);
			System.out.println(jo.toString());
			name = a.getVehichleInfo().getName();
			vehicleId = a.getVehichleInfo().getVehicleIdentificationnumber();
			position = new Position(a.getPosition().getLatitude(), a.getPosition().getLongitude());
			statusOfCharge = a.getAttributes().getSoc();
			nextPosition = new Position(a.getAttributes().getHeading().getLatitude(),
					a.getAttributes().getHeading().getLongitude());
			tripStatus = a.getAttributes().getTripStatus();
			track = Track.getMyTrack(track_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vehicle = new Thread();
		sc = new Scanner(System.in);
		vehicle.start();
	}

	public void run() {
		checkCabStatusAndUpdateLatestStatus();

	}

	private void checkCabStatusAndUpdateLatestStatus() {
		try (InputStream input = new FileInputStream("config.properties")) {

			Properties prop = new Properties();
			prop.load(input);
			while (true) {
				// FileReader jsonFile = new FileReader(vehicleJson);
				// Object obj = new JSONParser().parse(jsonFile);
				// JSONObject jo = (JSONObject) obj;
				// jsonFile.close();
				tripStatus = a.getAttributes().getTripStatus();
				System.out.println(tripStatus);
				int count = 0;
				if (tripStatus.equals(START)) {
					int time = Integer.parseInt(prop.getProperty(START));
					position.setLatitude(a.getAttributes().getHeading().getLatitude());
					position.setLongitude(a.getAttributes().getHeading().getLatitude());
					for (int i = 0; i < Track.getTrackSize();i++) {
						track = Track.getMyTrack(i);
						Position pos = track[0];
						if (position.getLatitude().equals(pos.getLatitude())
								&& position.getLongitude().equals(pos.getLongitude())) {
							System.out.println("Our Vehicle is on track " + i);
							count = count + 1;
							break;
						}
						break;
					}
					if (count == 0) {
						System.out.println("Our Vehicle is not on track , Please check");
					}
					getTripStatus(a, time);
				} else if (tripStatus.equals(PAUSE)) {
					int time = Integer.parseInt(prop.getProperty(PAUSE));
					getTripStatus(a, time);
				} else if (tripStatus.equals(STOP)) {
					int time = Integer.parseInt(prop.getProperty(STOP));
					getTripStatus(a, time);
				}
				try (FileWriter file = new FileWriter(vehicleJson)) {
					ObjectMapper Obj = new ObjectMapper();
					String jsonStr = Obj.writeValueAsString(a);
					System.out.println(jsonStr);
					file.write(jsonStr);
					System.out.println("Successfully updated json object to file...!!");
					file.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getTripStatus(CabInfo jo, int time) throws InterruptedException {
		tripStatus = a.getAttributes().getTripStatus();
		System.out.println("Enter new Status");
		String userinput = sc.nextLine();
		if (tripStatus.equals(START)) {
			while (userinput.equals(START)) {
				System.out.println("Please enter the correct status again!");
				System.out.println("Enter new Status");
				userinput = sc.nextLine();
			}

		}
		a.getAttributes().setTripStatus(userinput);
		Thread.sleep(time * 1000);
	}

}
