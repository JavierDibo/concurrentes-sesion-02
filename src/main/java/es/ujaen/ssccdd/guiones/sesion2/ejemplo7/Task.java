package es.ujaen.ssccdd.guiones.sesion2.ejemplo7;

/**
 * Runnable class than throws and Exception
 *
 */
public class Task implements Runnable {


	/**
	 * Main method of the class
	 */
	@Override
	public void run() {
		// The next instruction always throws and exception
		int numero=Integer.parseInt("TTT");
	}

}
