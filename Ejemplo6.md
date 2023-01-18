# Esperar para la finalización de la ejecución e un hilo

Hay ciertas circunstancias, en la ejecución de una aplicación, que necesitan saber que un hilo ha finalizado su ejecución antes de continuar. Por ejemplo, tenemos una aplicación que necesita la inicialización de una serie de recursos que deberá utilizar más adelante en su ejecución. Podemos realizar esa ejecución en un hilo y continuar con el programa, pero deberemos esperar a que finalice el hilo de ejecución antes de poder acceder a esos recursos.

En estos casos tenemos a nuestra disposición el método `join()` de la clase `Thread`. Cuando el método es invocado usando un objeto de la clase `Thread`, se suspende la ejecución del que realiza la llamada hasta que el objeto finaliza su ejecución.

Pasaremos a presentar un ejemplo de inicialización de recursos para aprender el uso del método `join()`.

1. Definimos una clase llamada `DataSourcesLoader` que implementa la interfaz `Runnable`.  
2. Implementamos el método `run()`. Primero mostraremos un mensaje en la consola indicando que se ha iniciado el proceso. Luego esperaremos por 4 segundos, y para finalizar. escribiremos otro mensaje en la consola indicando que hemos terminado.

```java
/**
 * Main method of the class
 */
@Override
public void run() {
		
    // Writes a messsage
    System.out.printf("Begining data sources loading: %s\n",new Date());
    // Sleeps four seconds
    try {
        TimeUnit.SECONDS.sleep(4);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    // Writes a message
    System.out.printf("Data sources loading has finished: %s\n",new Date());
}
```

3. Definimos otra clase llamada `NetworkConnectionsLoader` que implementa la interfaz `Runnable`.
4. Implementamos el método `run()` de forma similar al caso anterior. Pero esta vez esperamos por 6 segundos antes de finalizar.
5. Implementamos el método `main(.)` de la aplicación Java. Crearemos dos objetos, uno de la clase `DataSourcesLoader` y otro de la clase `NetworkConnectionsLoader`. Los asociamos cada uno de ellos a un objeto de la clase `Thread` antes de ejecutar los hilos.

```java
...
// Creates and starts a DataSourceLoader runnable object
DataSourcesLoader dsLoader = new DataSourcesLoader();
Thread thread1 = new Thread(dsLoader,"DataSourceThread");
thread1.start();

// Creates and starts a NetworkConnectionsLoader runnable object
NetworkConnectionsLoader ncLoader = new NetworkConnectionsLoader();
Thread thread2 = new Thread(ncLoader,"NetworkConnectionLoader");
thread2.start();
...
```

6. Esperamos hasta la finalización de los dos hilos. Para terminar con la aplicación mostramos un mensaje final que indicará que se han completado las tareas de configuración.

```java
...
// Wait for the finalization of the two threads
try {
    thread1.join();
    thread2.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}

// Waits a message
System.out.printf("Main: Configuration has been loaded: %s\n",new Date());
...
```

7. Ejecutamos la aplicación para comprobar el resultado.

## Preguntas

-   ¿El programa principal podrá terminar antes que los hilos que ejecuta?    
-   ¿Terminan los hilos siempre en el mismo orden? ¿Por qué?
-   ¿Cuántas veces se suspende la ejecución del programa principal? ¿Por qué?

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQ5MDU1NjVdfQ==
-->