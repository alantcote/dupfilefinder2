package cotelab.dupfilefinder2;

import javafx.util.StringConverter;

/**
 * A {@link StringConverter} that converts from String to String.
 */
public class String2StringConverter extends StringConverter<String> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String fromString(String arg0) {
		return arg0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(String arg0) {
		return arg0;
	}

}
