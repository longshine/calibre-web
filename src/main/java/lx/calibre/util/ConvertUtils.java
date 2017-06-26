package lx.calibre.util;

public final class ConvertUtils {

	private ConvertUtils() {
	}

	public static long toLong(Object obj) {
		return ((Number) obj).longValue();
	}

	public static String toString(Object obj) {
		return obj != null ? obj.toString() : null;
	}

	public static Double toDouble(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		} else {
			return Double.valueOf(obj.toString());
		}
	}

}
