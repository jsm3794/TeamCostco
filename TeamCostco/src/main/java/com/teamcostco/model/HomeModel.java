package main.java.com.teamcostco.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeModel {
	private List<String> buttonLabels;
	
	// 상수
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public HomeModel() {
		buttonLabels = new ArrayList<>();
		buttonLabels.add("A");
		buttonLabels.add("B");
		buttonLabels.add("C");
		buttonLabels.add("D");
		buttonLabels.add("E");
		buttonLabels.add("F");
		buttonLabels.add("G");
		buttonLabels.add("H");
		buttonLabels.add("I");
		buttonLabels.add("J");
	}

	public List<String> getButtonLabels() {
		return buttonLabels;
	}
}
