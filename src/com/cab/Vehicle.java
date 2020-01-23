package com.cab;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cab.models.Position;
import com.cab.models.Track;

public class Vehicle extends Thread {
	private static final String STOP = "stop";
	private static final String PAUSE = "pause";
	private static final String START = "start";
	private static final String positions = "position";
	private static final String status = "trip_status";
	private static final String longitude = "longitude";
	private static final String attributes = "attributes";
	private static final String latitude = "latitude";
	private String name;
	private String vehicleId;
	private Position position;
	private Position[] track;
	private int statusOfCharge;
	private Position nextPosition;
	private String tripStatus;
	private String vehicleJson;
	private Thread vehicle;
	private Scanner sc;

	Vehicle(int track_id, String filename) {
		vehicleJson = filename;
		FileReader jsonFile;
		try {
			jsonFile = new FileReader(vehicleJson);
			Object obj = new JSONParser().parse(jsonFile);
			JSONObject jo = (JSONObject) obj;
			jsonFile.close();
			name = ((JSONObject) jo.get("vehichleInfo")).get("name").toString();
			vehicleId = ((JSONObject) jo.get("vehichleInfo")).get("vehicleIdentificationnumber").toString();
			position = new Position(((JSONObject) jo.get(positions)).get(latitude).toString(),
					((JSONObject) jo.get(positions)).get(longitude).toString());
			statusOfCharge = Integer.parseInt(((JSONObject) jo.get(attributes)).get("soc").toString());
			JSONObject heading = (JSONObject) ((JSONObject) jo.get(attributes)).get("heading");
			nextPosition = new Position(heading.get(latitude).toString(), heading.get(longitude).toString());
			tripStatus = ((JSONObject) jo.get(attributes)).get(status).toString();
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
				FileReader jsonFile = new FileReader(vehicleJson);
				Object obj = new JSONParser().parse(jsonFile);
				JSONObject jo = (JSONObject) obj;
				jsonFile.close();
				tripStatus = ((JSONObject) jo.get(attributes)).get(status).toString().trim();
				System.out.println(tripStatus);
				int count = 0;
				if (tripStatus.equals(START)) {
					int time = Integer.parseInt(prop.getProperty(START));
					position.setLatitude(((JSONObject) jo.get(positions)).get(latitude).toString().trim());
					position.setLongitude(((JSONObject) jo.get(positions)).get(longitude).toString().trim());
					for (int i = 0; i < Track.getTrackSize(); i++) {
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
					getTripStatus(jo, time);
				} else if (tripStatus.equals(PAUSE)) {
					int time = Integer.parseInt(prop.getProperty(PAUSE));
					getTripStatus(jo, time);
				} else if (tripStatus.equals(STOP)) {
					int time = Integer.parseInt(prop.getProperty(STOP));
					getTripStatus(jo, time);
				}
				try (FileWriter file = new FileWriter(vehicleJson)) {
					System.out.println(jo.toString());
					file.write(jo.toString());
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

	private void getTripStatus(JSONObject jo, int time) throws InterruptedException {
		tripStatus = ((JSONObject) jo.get(attributes)).get(status).toString().trim();
		System.out.println("Enter new Status");
		String userinput = sc.nextLine();
		if (tripStatus.equals(START)) {
			while (userinput.equals(START)) {
				System.out.println("Please enter the correct status again!");
				System.out.println("Enter new Status");
				userinput = sc.nextLine();
			}

		}
		((JSONObject) jo.get(attributes)).put(status, userinput);
		Thread.sleep(time * 1000);
	}

}
