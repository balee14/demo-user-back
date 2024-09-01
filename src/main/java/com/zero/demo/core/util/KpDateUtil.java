package com.zero.demo.core.util;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Log4j2
public final class KpDateUtil {

	/**
	 * 오늘 날짜 기준으로 Milli를 long으로 반환한다.
	 * @return
	 */
	public static Long getLocalDateTimeToLong() {
		//		Long testzone = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * yyyy-MM-ddTHH:mm:ss
	 * @return
	 */
	public static String getDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.toString();
	}

	/**
	 * formatter에 따라 표시
	 * 기본 설정은 yyyyMMdd
	 * @return
	 */
	public static String getDateYyyyMMdd(String formatter) {
		if (formatter.isEmpty()) {
			formatter = "yyyyMMdd";
		}
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(formatter));
	}

	/**
	 * 해당 년도를 리턴 한다.
	 * @return
	 */
	public static String localDateYear() {
		LocalDate now = LocalDate.now();

		return String.valueOf(now.getYear());
	}

	/**
	 * 해당 월을 리턴 한다. 1월 = "01"
	 * @return
	 */
	public static String localDateMonthValue() {
		LocalDate now = LocalDate.now();
		int monthValue = now.getMonthValue();

		return String.format("%02d", monthValue);
	}

}
