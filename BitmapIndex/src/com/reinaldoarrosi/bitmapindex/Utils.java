package com.reinaldoarrosi.bitmapindex;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;

public class Utils {
	public static String toString(Object value) {
		if (value == null)
			return null;

		if (value instanceof Float)
			return new BigDecimal((Float) value).stripTrailingZeros().toPlainString();
		else if (value instanceof Double)
			return new BigDecimal((Double) value).stripTrailingZeros().toPlainString();
		else
			return String.valueOf(value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Class<T> clazz, Collection<T> collection) {
		T[] array = (T[]) Array.newInstance(clazz, collection.size());
		collection.toArray(array);

		return array;
	}
}
