# Tratar excepciones no comprobadas en la ejecución de un hilo

Hay dos tipos de excepciones en Java:

-   **Excepciones comprobadas**: Estas excepciones deben ser especificadas en una cláusula `throws` del método donde puedan producirse o tratadas en el método en una cláusula `caugth`. Por ejemplo, [IOException](https://docs.oracle.com/javase/8/docs/api/java/io/IOException.html) o [ClassNotFoundException](https://docs.oracle.com/javase/8/docs/api/java/lang/ClassNotFoundException.html).    
-   **Excepciones no comprobadas**: Estas excepciones no pueden ser *pasadas* o *tratadas*. Por ejemplo, [NumberFormatException](https://docs.oracle.com/javase/8/docs/api/java/lang/NumberFormatException.html).
    
Cuando una **excepción comprobada** se lanza dentro del método `run()` debe ser tratada dentro de este método porque no tiene a su disposición la cláusula `throws`. Cuando una **excepción no comprobada** se lanza dentro del método `run()`  se tratará de forma genérica y mostrará el estado de la pila en la consola y terminará el programa.

Java nos proporciona un mecanismo para tratar las excepciones no comprobadas que evitará que deba terminar la ejecución del programa. Pasaremos a presentar un ejemplo para mostrar el mecanismo que será necesario.

1. Primero definimos una clase con nombre `ExceptionHandler` que nos permitirá tratar las excepciones no comprobadas. La clase implementa la interfaz `UncaughtExceptionHandler` y debemos implementar el método `uncaughtException(.)`. En el ejemplo la implementación del método mostrará en la consola información relativa a la excepción que se ha producido.

```java
/**
 * Class that process the uncaught exceptions throwed in a Thread
 *
 */
public class ExceptionHandler implements UncaughtExceptionHandler {

    /**
     * Main method of the class. It process the uncaught excpetions throwed
     * in a Thread
     * @param t The Thead than throws the Exception
     * @param e The Exception throwed
     */
    @Override	
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured\n");
        System.out.printf("Thread: %s\n",t.getId());
        System.out.printf("Exception: %s: %s\n",e.getClass().getName(),e.getMessage());
        System.out.printf("Stack Trace: \n");
        e.printStackTrace(System.out);
        System.out.printf("Thread status: %s\n",t.getState());
    }
}
```

2. Definimos una clase con nombre `Task` que implementa la interfaz `Runnable`. Solo nos aseguraremos que en el método `run()` crearemos el código necesario para producir una excepción no comprobada.

```java
...
@Override
public void run() {
    // The next instruction always throws and exception
    int numero=Integer.parseInt("TTT");
}
...
```

4. Para finalizar implementamos el método `main(.)` de la aplicación donde crearemos un objeto de la clase `Task` que lo asociaremos a un objeto de la clase `Thread`. Asignaremos el objeto que tratará la excepciones no comprobadas del hilo a un objeto `ExceptionHandler` antes de iniciar la ejecución del hilo.

```java
public static void main(String[] args) {
    // Creates the Task
    Task task=new Task();
    // Creates the Thread
    Thread thread=new Thread(task);
    // Sets de uncaugh exceptio handler
    thread.setUncaughtExceptionHandler(new ExceptionHandler());
    // Starts the Thread
    thread.start();
		
    try {
        thread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
		
    System.out.printf("Thread has finished\n");
}
```

4. Comprobar el resultado de la aplicación.

## Preguntas

-   ¿Ha terminado correctamente la aplicación?    
-   ¿Qué utilidad podría tener este mecanismo?
-   ¿En qué casos no podría evitarse la finalización del programa?

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEzOTg3NzA3MDJdfQ==
-->