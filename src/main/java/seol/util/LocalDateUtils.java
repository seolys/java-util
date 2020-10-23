package seol.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;

public class LocalDateUtils {

	public static final String LOCAL_DATE_FORMAT_WITH_HYPHEN = "yyyy-MM-dd";
	public static final String LOCAL_DATE_FORMAT = "yyyyMMdd";

	/**
	 * 주로 엔터티의 속성에서 gql 스키마로 줄 때 이 하이픈 형식을 쓴다.
	 */
	public static String toString(LocalDate attribute) {
		return toString(attribute, LOCAL_DATE_FORMAT_WITH_HYPHEN);
	}

	public static String toString(LocalDate attribute, String format) {
		if (attribute == null) {
			return "";
		}
		return attribute.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 주로 엔터티에 넣을 때 하이픈 없는 형식을 쓴다.
	 */
	public static String toStringWithNoHyphen(LocalDate attribute) {
		if (attribute == null) {
			return "";
		}
		return attribute.format(DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
	}

	public static LocalDate from(String attribute) {
		if (StringUtils.isEmpty(attribute)) {
			return null;
		}
		final String format = getFormat(attribute);
		validationCheck(format, attribute);
		return LocalDate.parse(attribute, DateTimeFormatter.ofPattern(format));
	}

	private static String getFormat(String attribute) {
		return attribute.length() == 10 ? LOCAL_DATE_FORMAT_WITH_HYPHEN : LOCAL_DATE_FORMAT;
	}

	private static void validationCheck(String format, String localDate) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);
		try {
			df.parse(localDate);
		} catch (ParseException e) {
			String errorMessage = "일자 형식이 일치하지 않습니다. : " + localDate;
			throw new IllegalArgumentException(errorMessage);
		}
	}
}