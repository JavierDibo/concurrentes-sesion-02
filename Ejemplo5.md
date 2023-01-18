# Suspender y reanudar la ejecución de un hilo

Cuando se solicita la interrupción de un hilo no siempre se quiere que dicha interrupción sea definitiva, es decir, puede que queramos detener la ejecución del hilo por un periodo de tiempo. O para realizar alguna comprobación periódica, es decir, cada cierto tiempo el hilo estará ejecutándose. De esta forma no se utilizarán recursos del sistema si no son necesarios. En este ejemplo utilizaremos el método `sleep(.)`[^nota1] que nos permitirá interrumpir la ejecución de un hilo por un tiempo determinado.

En este ejemplo se desarrollará un hilo que invoca el método `sleep(.)` para mostrar por la salida estándar la fecha actual cada segundo.

1. Definimos una clase llamada `FileClock` que implemente la interfaz `Runnable`.
2. Implementamos el método `run()`. Realizamos un bucle con 10 iteraciones, para cada iteración presentamos por la salida estándar un objeto de la clase `Date` y llamamos al método `sleep(.)` por un segundo. Como el método `sleep(.)` puede lanzar la excepción `InterruptedException`, incluimos el código para tratarla. Es una buena práctica incluir código que libere o cierre los recursos que el hilo esté utilizando cuando es interrumpido.

```java
public class FileClock implements Runnable {

    /**
     * Main method of the class
     */
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s\n", new Date());
            try {
                // Sleep during one second
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.printf("The FileClock has been interrupted\n");
                cleanResources();
            }
        }
    }
        
    /**
     * Method for cleaning the resources. In this case, is empty
     */
     private void cleanResources() {
     }
}
```

3. Ahora vamos a implementar el método `main(.)` de la aplicación Java. Crearemos un objeto de la clase `FileClock` y se lo asociaremos a un objeto de la clase `Thread` antes de iniciar su ejecución.

```java
...
// Creates a FileClock runnable object and a Thread
// to run it
FileClock clock=new FileClock();
Thread thread=new Thread(clock);
		
// Starts the Thread
thread.start();
...
```

4. Esperamos por 5 segundos invocando el método `sleep(.)`. Pasado ese tiempo interrumpimos la ejecución del hilo.

```java
...
try {
    // Waits five seconds
    TimeUnit.SECONDS.sleep(5);
} catch (InterruptedException e) {
    e.printStackTrace();
}
// Interrupts the Thread
thread.interrupt();
...
```

5. Ejecutamos la aplicación y comprobamos el resultado.


## Preguntas

-   ¿Qué resultado debería ser el esperado?    
-   ¿Se interrumpe correctamente la ejecución del hilo?
-   ¿Hay alguna razón para explicar el resultado del ejemplo?

---
[^nota1]: A lo largo de las prácticas utilizaremos el método `sleep(.)` para simular tiempo de ejecución no exclusivamente con la intención de interrumpir la ejecución temporal de un hilo.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTcxMDI5NTYwM119
-->