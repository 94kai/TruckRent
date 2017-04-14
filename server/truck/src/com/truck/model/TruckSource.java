package com.truck.model;

public class TruckSource {
	private String load_date;
	private String start_place;
	private String stop_place;
	private String start_place_point;
	private String introcduce;
	private String publish_date;
	private String state;
	private String truck_id;
	private Truck truck;
	private User user;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Truck getTruck() {
		return truck;
	}

	public void setTruck(Truck truck) {
		this.truck = truck;
	}

	public String getLoad_date() {
		return load_date;
	}
	
	public String getTruck_id() {
		return truck_id;
	}

	public void setTruck_id(String truck_id) {
		this.truck_id = truck_id;
	}

	public void setLoad_date(String load_date) {
		this.load_date = load_date;
	}
	public String getStart_place() {
		return start_place;
	}
	public void setStart_place(String start_place) {
		this.start_place = start_place;
	}
	public String getStop_place() {
		return stop_place;
	}
	public void setStop_place(String stop_place) {
		this.stop_place = stop_place;
	}
	public String getStart_place_point() {
		return start_place_point;
	}
	public void setStart_place_point(String start_place_point) {
		this.start_place_point = start_place_point;
	}
	public String getIntrocduce() {
		return introcduce;
	}
	public void setIntrocduce(String introcduce) {
		this.introcduce = introcduce;
	}
	public String getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
