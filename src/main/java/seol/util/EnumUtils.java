package seol.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class EnumUtils extends org.apache.commons.lang3.EnumUtils {

	public static <T extends Enum> String toString(T object) {
		return toString(object, (String) null);
	}

	public static <T extends Enum> String toString(T object, String defaultValue) {
		return toString(object, Objects::toString, defaultValue);
	}

	public static <T extends Enum> String toString(T object, Function<T, String> getStringFunction) {
		return toString(object, getStringFunction, null);
	}

	public static <T extends Enum> String toString(T object, Function<T, String> getStringFunction, String defaultValue) {
		return Optional.ofNullable(object)
				.map(getStringFunction)
				.orElse(defaultValue);
	}

	private EnumUtils() {
	}

}
