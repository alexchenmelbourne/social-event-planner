package com.example.android.donebar;

import java.util.Date;
import java.util.UUID;

public class Event {
	String eventTitle;
	Date eventDate;
	String eventVenue;
	String eventLocation;
	String eventNote;
	String eventAttendees;
	String eventId;
	String eventDateString;

	public Event() {

	}

	public Event(String title, Date date, String venue, String note,
			String attendees) {
		eventTitle = title;
		eventDate = date;
		eventDateString = date.toString();
		eventVenue = venue;
		eventNote = note;
		eventAttendees = attendees;
	}

	public String generateId() {
		return eventId = UUID.randomUUID().toString();
	}

	public String getTitle() {
		return eventTitle;
	}

	public void setTitle(String title) {
		this.eventTitle = title;
	}

	public String getVenue() {
		return eventVenue;
	}

	public void setVenue(String venue) {
		this.eventVenue = venue;
	}

	public String getDate() {
		return eventDate.toString();
	}

	public void setDateString(String date) {
		this.eventDateString = date;
	}
	public String getDateString() {
		return eventDateString;
	}

	public String getNote() {
		return eventNote;
	}

	public void setNote(String note) {
		this.eventNote = note;
	}

	public String getAttendees() {
		return eventAttendees;
	}

	public void setAttendees(String attendees) {
		this.eventAttendees = attendees;
	}
}
