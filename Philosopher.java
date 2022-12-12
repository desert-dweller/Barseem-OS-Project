import java.util.concurrent.ThreadLocalRandom;

class Philosopher implements Runnable {
	private volatile Chopstick left_chopstick = new Chopstick(), right_chopstick = new Chopstick();
	private int MAX_EAT, num_eat, chair_number;
	private volatile long eating_time, thinking_time;

	enum State {
		HUNGRY, THINKING, EATING, SLEEPING;
	}

	private State current_state = State.HUNGRY;

	public Philosopher(int chair_num, Chopstick left, Chopstick right, int eat_num) {
		chair_number = chair_num;
		left_chopstick = left;
		right_chopstick = right;
		MAX_EAT = eat_num;
		current_state = State.HUNGRY;

		setRandomTimes();
	}

	public void run() {
		DiningPhils.printStatus();

		while (num_eat < MAX_EAT) {
			changeState(State.HUNGRY);

			think();

			changeState(State.HUNGRY);
			try {
				if (pickupChopsticks() == false)
					continue;
			} catch (NullPointerException e) {
				/* do nothing */}

			eat();
		}

		changeState(State.SLEEPING);
	}

	public void think() {
		changeState(State.THINKING);
		setRandomTimes();

		try {
			Thread.sleep(thinking_time);
		} catch (InterruptedException e) {
			/* do nothing */}
	}

	public boolean pickupChopsticks() {
		if (left_chopstick.inUse(chair_number) == false)
			left_chopstick.claim(chair_number);
		else
			return false;

		if (right_chopstick.inUse(chair_number) == false)
			right_chopstick.claim(chair_number);
		else {
			left_chopstick.release();
			return false;
		}

		return true;
	}

	public void eat() {
		changeState(State.EATING);
		try {
			Thread.sleep(eating_time);
		} catch (InterruptedException e) {
			/* do nothing */}

		// Release chopsticks after wake
		try {
			left_chopstick.release();
			right_chopstick.release();
		} catch (NullPointerException e) {
			/* do nothing */}

		num_eat++;
	}

	public void changeState(State new_state) {
		if (current_state != new_state) {
			current_state = new_state;
			DiningPhils.printStatus();
		} else
			return;
	}

	public String getState() {
		String str_state = "";
		if (current_state == State.HUNGRY)
			str_state = "Hungry";
		else if (current_state == State.THINKING)
			str_state = "Thinking";
		else if (current_state == State.EATING)
			str_state = "Eating";
		else if (current_state == State.SLEEPING)
			str_state = "Sleeping";

		return str_state;
	}

	public int getNumEat() {
		return num_eat;
	}

	public void setRandomTimes() {
		eating_time = ThreadLocalRandom.current().nextLong(5000);
		thinking_time = ThreadLocalRandom.current().nextLong(eating_time);
	}
}