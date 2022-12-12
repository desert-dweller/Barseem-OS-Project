class Chopstick {
	static final int NO_OWNER = 5;
	private volatile int belongs_to = NO_OWNER; /*
												 * If belongs_to == 5, no philosopher has reserved this chopstick. This
												 * provides a double-check so that there never will exist a case where a
												 * philospher rudely steals a chopstick
												 */

	private volatile boolean chopstick = false; /*
												 * A chopstick with a value of false means that the chopstick is not in
												 * use. When a Chopstick object is created, it will not be in use
												 */

	public boolean inUse(int chair_number) {
		if (belongs_to == NO_OWNER)
			return false;
		else if (chair_number != belongs_to)
			return true;
		else if (chopstick == true)
			return true;

		return false;
	}

	public void claim(int chair_number) {
		belongs_to = chair_number;
		chopstick = true;
	}

	public void release() {
		belongs_to = NO_OWNER;
		chopstick = false;
	}
}