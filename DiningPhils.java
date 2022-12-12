// Programming Assignment #4 for CSCI3320 - Advanced Programming
// Written by Justin Shapiro

import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhils extends Thread {
	private static final int MAX_PHIL = 5; // number of Philosophers at the table
	private static int thread_creation_counter = 0; /*
													 * if a status update is requested before all threads have been
													 * created, only active threads will display on the status table
													 */
	public static Chopstick[] chopsticks = new Chopstick[MAX_PHIL];
	public static Philosopher[] philosophers = new Philosopher[MAX_PHIL];
	public static Thread[] thread_array = new Thread[MAX_PHIL];

	public static void main(String[] args) {
		int meals_allowed = 5; // if no arguments are provided, each Philosopher will eat five times
		if (args.length != 0)
			meals_allowed = Integer.parseInt(args[0]);

		for (int i = 0; i < MAX_PHIL; i++) {
			chopsticks[i] = new Chopstick();

			if (i < 4) // could change this to be circular?
				philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[i + 1], meals_allowed);
			else
				philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[0], meals_allowed);

			thread_array[i] = new Thread(philosophers[i], Integer.toString(i));
			thread_creation_counter++;

			thread_array[i].start();
		}
	}

	public static synchronized void printStatus() {
		System.out.println("\nPhilosopher    State          Times Eaten");
		System.out.println("-------------------------------------------");
		for (int i = 0; i < thread_creation_counter; i++) {
			System.out.format("%-14s %-14s %-6s %n", thread_array[i].getName(), philosophers[i].getState(),
					philosophers[i].getNumEat());
		}
	}
}