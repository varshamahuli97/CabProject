package com.cab.models;

public class Track {
	private static Position[][] tracks = {
			{ new Position("2", "1"), new Position("3", "2"), new Position("4", "3"), new Position("5", "3") },
			{ new Position("4", "1"), new Position("5", "8"), new Position("7", "9"), new Position("6", "4") },
			{ new Position("6", "1"), new Position("6", "5"), new Position("5", "1"), new Position("5", "4") },
			{ new Position("3", "1"), new Position("3", "7"), new Position("5", "3"), new Position("8", "3") } };

	public static Position[] getMyTrack(int index) {
		return tracks[index];
	}

	public static int getTrackSize() {
		return tracks.length;
	}

}
