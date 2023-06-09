package es.ujaen.ssccdd.guiones.sesion2.ejemplo8;

import java.util.concurrent.TimeUnit;


/**
 * Main class of the example.
 *
 */
public class SafeMain {

	/**
	 * Main method of the example
	 * @param args
	 */
	public static void main(String[] args) {
		// Creates a task
		SafeTask task=new SafeTask();
		
		// Creates and start three Thread objects for that Task
		for (int i=0; i<3; i++){
			Thread thread=new Thread(task);
                        thread.start();
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
