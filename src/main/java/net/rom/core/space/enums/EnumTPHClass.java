package net.rom.core.space.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum EnumTPHClass.
 *
 * @author AJ
 */
public enum EnumTPHClass {

	/** Temp: -100 thru -50. */
	HP("hP", "Hypopsychroplanet", -100.0F, -50.0F),
	
	/** Temp: -49 thru -0. */
	P("P", "Psychroplanet", -49.0F, 0.0F),
	
	/** Temp: 1 thru 50. */
	M("M", "Mesoplanet", 1.0F, 49.0F),
	
	/** Temp: 51 thru 100. */
	T("T", "Theroplanet", 50.0F, 100.0F),
	
	/** Temp: 101 thru 150. */
	HT("hT", "hyperthermoplanet", 101.0F, 150.0F);

	/** The class name. */
	private String className;
	
	/** The name. */
	private String name;
	
	/** The temp low. */
	private float tempLow;
	
	/** The temp high. */
	private float tempHigh;

	/**
	 * Instantiates a new enum TPH class.
	 *
	 * @param className the class name
	 * @param name the name
	 * @param tempLow the temp low
	 * @param tempHigh the temp high
	 */
	EnumTPHClass(String className, String name, float tempLow, float tempHigh) {
		this.className = className;
		this.name = name;
		this.tempLow = tempLow;
		this.tempHigh = tempHigh;
	}

	/**
	 * Instantiates a new enum TPH class.
	 *
	 * @param className the class name
	 * @param name the name
	 */
	EnumTPHClass(String className, String name) {
		this.className = className;
		this.name = name;
	}

	/**
	 * Gets the temp high.
	 *
	 * @return the temp high
	 */
	public float getTempHigh() {
		return this.tempHigh;
	}

	/**
	 * Gets the temp low.
	 *
	 * @return the temp low
	 */
	public float getTempLow() {
		return this.tempLow;
	}

	/**
	 * Gets the class name.
	 *
	 * @return the className
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the TPH from temp.
	 *
	 * @param temp the temp
	 * @return the TPH from temp
	 */
	public static EnumTPHClass getTPHFromTemp(float temp) {
		if (between(temp, -100.0F, -50.0F)) {
			return EnumTPHClass.HP;
		}
		if (between(temp, -49.0F, 0.0F)) {
			return EnumTPHClass.P;
		}
		if (between(temp, 1.0F, 49.0F)) {
			return EnumTPHClass.M;
		}
		if (between(temp, 50.0F, 100.0F)) {
			return EnumTPHClass.T;
		}
		if (between(temp, 101.0F, 150.0F)) {
			return EnumTPHClass.HT;
		}
		if (isLessThan(temp, -100.0F)) {
			return EnumTPHClass.HP;
		} else {
			return EnumTPHClass.HT;
		}

	}

	/**
	 * Between.
	 *
	 * @param i the i
	 * @param minValueInclusive the min value inclusive
	 * @param maxValueInclusive the max value inclusive
	 * @return true, if successful
	 */
	public static boolean between(float i, float minValueInclusive, float maxValueInclusive) {
		return (i >= minValueInclusive && i <= maxValueInclusive);
	}

	/**
	 * Checks if is more than.
	 *
	 * @param i the i
	 * @param max the max
	 * @return true, if is more than
	 */
	public static boolean isMoreThan(float i, float max) {
		return i >= max;
	}

	/**
	 * Checks if is less than.
	 *
	 * @param i the i
	 * @param min the min
	 * @return true, if is less than
	 */
	public static boolean isLessThan(float i, float min) {
		return i <= min;
	}

}
