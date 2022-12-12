PA4 WRITEUP
-----------------------

@@@@@@@@@@@
The assignment submission for PA4 contains a DiningPhils.java file that contains a solution to the Dining Philosophers problem that is starvation and deadlock free. After compilation, if no number is specified by the user when the program is run, the program will choose 5 as the default for the number of times each Philosopher eats. 

The program allows multiple Philosophers to eat at one time because each Philosopher is represented in the program as a thread: since there are five Philosophers, there are 5 threads. Each Philosopher has access to the Philosopher class, where the meal takes place, at the same time. 

Because multithreaded programs are prone to deadlocks, I implemented a deadlock free solution when designing the program. Deadlock is avoided in the program by catching NullPointerExceptions during the phase where the Philosophers pick up their Chopsticks. Additionally, deadlocks are avoided by implementing a Chopstick class that creates custom Chopstick objects that have methods that ensure that no two Philosophers can use the same Chopstick.

The solution also guarantees that no Philosopher will starve due to there being no deadlocks. When a Philosopher is finished eating, a method in the Philosopher class calls a method of the Chopstick class to free up the Chopsticks for the other Philosophers. Therefore, although some Philosophers may wait longer than others to eat, each Philosopher will get the change to eat. 

The hardest part of this assignment was figuring out to catch a NullPointerException every time a Chopstick instance is invoked. If this exception is not caught, the program ends up in a deadlock. 

Overall, I spent around 15 hours working on the assignment. Around 5 of these hours were dedicated to planning out the design of the program (see "Blueprint" below). 

@@@@@@@@@@@

Blueprint for my solution of Dijkstra’s “Dining Philosophers” problem:

1.	The driver class DiningPhils will define the number of Philosophers, create a variable called num_eat that indicates the number of times each Philosopher eats, create an array of Chopsticks, and create an array of Philosophers that is uninitialized. A loop will iterate through the array of Philosophers and initialize each Philosopher with its number, left Chopstick, and right Chopstick. An alternative if-condition will be specified if the Philosopher is number 4 so the right Chopstick is actually the first Chopstick in the Chopstick array, rather than the 6th element in a 5 element array. Once a Philosopher in an array in initialized, a thread is created and started using the two-parameter Thread constructor that takes in a Runnable “target” and a name for the Thread. Each Thread will simply be named based on their instantiation (0, 1, etc.) Once a Philosopher Thread is started, it will simultaneously execute while the other threads also start and execute simultaneously. 

2.	There will be a simple Chopstick class that will be responsible for defining Boolean-type objects whose modifications are universal to the program. If the array of Chopsticks were just an array of Boolean values, each time a Philosopher picks up a Chopstick, they will be able to use it. We want the Chopsticks to be objects so that they are passed by reference to all the threads ensuring that no two Philosophers can use the same Chopstick.

3.	All the computation will be done in a secondary class called Philosopher that obviously implements Runnable. As per the instructions, this class will have private member variables left_chopstick and right_chopstick. There will be a guest_number member variable to assign a number to a Philosopher. There will also be a current_state member variable to indicate the current state of a philosopher. Lastly, there will be a num_eat variable that will count the number of times a Philosopher has eaten. If the Philosopher has eaten more than the specified number of times, the Thread will terminate. In the Philosopher constructor, this Philosopher will be initialized with the values passed in from the DiningPhils class. 

The overridden run() function will perform the actions of each Philosopher. First a Philosopher will think, then they will pick up their chopsticks. If the Philosopher succeeds in picking up both their chopsticks, they will eat. Once they are finished eating, they will put down their chopsticks. At each phase, the synchronized printStates() function will be called to generate a table with the state of all the threads. The printStates() function is synchronized so only one thread has access to it at a time, ensuring that two tables will not be printed on top of each other. Below are the actions that will occur during each phase of this process:
•	Thinking: The current Philosopher Thread will sleep for a random amount of time. The range of thinking time to be less than 5 seconds and also less than the Philosopher’s eating_time. 
•	Pick up chopsticks: The current Philosopher will modify the Chopstick objects to indicate that they are using them. If either of the Chopsticks are in use, the function will return with false, triggering an interrupt in run(). This is to prevent deadlock. If wait() and notify() were used here, there would exist the possibility that all Philosophers would be holding their left Chopstick. Therefore, a Philosopher will continue multiple periods of thinking until a Chopstick becomes available at a convenient time for them.
•	Eat: A philosopher will eat for a random number amount of time each time they eat. Let’s define the range of eating_time to be less than 5 seconds but always greater than a Philosopher’s thinking_time. After eating, the appropriate modifications will be made to both Chopstick objects so they are available to the other Philosophers. 
