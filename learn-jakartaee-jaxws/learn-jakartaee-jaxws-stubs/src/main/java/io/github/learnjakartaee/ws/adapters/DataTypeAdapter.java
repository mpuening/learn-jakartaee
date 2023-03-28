package io.github.learnjakartaee.ws.adapters;

import java.util.Calendar;
import java.util.Date;

import jakarta.xml.bind.DatatypeConverter;

public class DataTypeAdapter {

	public static Date parseDate(String s) {
		if (s == null) {
			return null;
		}
		return DatatypeConverter.parseDate(s).getTime();
	}

	public static String printDate(Date dt) {
		if (dt == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return DatatypeConverter.printDate(c);
	}

	public static Date parseDateTime(String s) {
		if (s == null) {
			return null;
		}
		return DatatypeConverter.parseDateTime(s).getTime();
	}

	public static String printDateTime(Date dt) {
		if (dt == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return DatatypeConverter.printDateTime(c);
	}
}
