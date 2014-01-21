package org.mule.kicks.util;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * The function of this class is to establish a relation happens before between
 * two maps representing SFDC accounts.
 * 
 * It's assumed that these maps are well formed maps from SFDC thus they both
 * contain an entry with the expected key. Never the less validations are being
 * done.
 * 
 * @author cesargarcia
 */
public class AccountDateComparator {
	private static final String LAST_REFERENCED_DATE = "LastReferencedDate";

	/**
	 * Validate which contact has the latest last modification date.
	 * 
	 * @param accountA
	 *            SFDC contact map
	 * @param accountB
	 *            SFDC contact map
	 * @return true if the last activity date from accountA is after the one
	 *         from accountB
	 */
	public static boolean isAfter(Map<String, String> contactA, Map<String, String> contactB) {
		Validate.notNull(contactA, "The account A should not be null");
		Validate.notNull(contactB, "The account B should not be null");

		Validate.isTrue(contactA.containsKey(LAST_REFERENCED_DATE), "The account A map should containt the key " + LAST_REFERENCED_DATE);
		Validate.isTrue(contactB.containsKey(LAST_REFERENCED_DATE), "The account B map should containt the key " + LAST_REFERENCED_DATE);

		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		DateTime lastModifiedDateOfA = formatter.parseDateTime(contactA.get(LAST_REFERENCED_DATE));
		DateTime lastModifiedDateOfB = formatter.parseDateTime(contactB.get(LAST_REFERENCED_DATE));

		return lastModifiedDateOfA.isAfter(lastModifiedDateOfB);
	}
}
