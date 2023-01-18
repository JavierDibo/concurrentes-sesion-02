# Usar variables locales en un hilo

Uno de los aspectos críticos en las aplicaciones concurrentes es la posibilidad de compartir información entre los hilos. Esto es especialmente importante en los objetos de clases que heredan de la clase `Thread` o implementa la interfaz `Runnable`.

Si se crea un objeto de una clase que implementa la interfaz `Runnable` y se ejecutan varios objetos de la clase `Thread` que tengan el mismo objeto `Runnable`, todos los hilos comparten las mismas variables. Esto significa que, si se cambia una variable en un hilo, todos los hilos se verán afectados por el cambio. Puede que estemos interesados en que hayan valores de una variable que no se compartan entre los diferentes hilos. Java nos proporciona un mecanismo sencillo llamado **variables locales en un hilo** que tiene una gran eficiencia.

Presentaremos un ejemplo donde se evidencia el problema de compartir información entre hilos y posteriormente aplicaremos el mecanismo que nos permitirá corregir el problema.

1. Empezaremos presentando un ejemplo del problema al compartir información. Definimos una clase llamada `UnsafeTask` que implementa la interfaz `Runnable`. Definimos una variable de instancia que nos permita almacenar un objeto de la clase `Date`.
2. Implementamos el método `run()`. El método inicializa la variable de instancia a la fecha actual y la presenta en la consola. Luego se suspende por un tiempo aleatorio la ejecución y para finalizar vuelve a presentar por la consola la fecha.

```java
...
@Override
public void run() {
    startDate=new Date();
    System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate);
    try {
        TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(),startDate);
}
...
```

3. Implementamos el método `main(.)` de la aplicación. Creamos un objeto de la clase `UnsafeTask` y lo asociaremos a tres objetos diferentes de la clase `Thread` y ejecutaremos cada uno de los hilos. Se suspenderá la ejecución de cada hilo por dos segundos.

```java
public static void main(String[] args) {
    // Creates the unsafe task
    UnsafeTask task=new UnsafeTask();
		
    // Throw three Thread objects
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
```

4. Ejecutar la aplicación y comprobar el resultado.

## Pregunta

-   ¿Cuál es el problema que se produce en la ejecución?
    
5. Vamos a utilizar el mecanismo de **variable local para un hilo** que dará solución al problema presentado anteriormente.
6. Definimos una clase llamada `SafeTask` que implementa la interfaz `Runnable`.
7. Definimos un objeto de la clase [ThreadLocal<.>](https://docs.oracle.com/javase/8/docs/api/java/lang/ThreadLocal.html). Este objeto tiene una implementación implícita que incluye el método `initialValue(.)`. Lo utilizaremos para devolver la fecha actual.

```java
...
/**
 * ThreadLocal shared between the Thread objects
 */
private static ThreadLocal<Date> startDate= new ThreadLocal<Date>() {
    protected Date initialValue(){
        return new Date();
    }
};
...
```

8. El método `run()`  se adaptará para utilizar el objeto donde se almacena la fecha y la estructura es la misma que en el ejemplo anterior.

```java
@Override
public void run() {
    // Writes the start date
    System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate.get());
    try {
        TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    // Writes the start date
    System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(),startDate.get());
}
```

9. El método `main(.)` es el mismo que en el ejemplo anterior pero adaptándolo para utilizar el objeto de la clase `SafeTask`.
10. Ejecuta la aplicación y comprueba el resultado.

## Preguntas

-   ¿Qué diferencia hay con el ejemplo anterior?    
-   ¿Hay algo que te resulte curioso en la ejecución?
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQwNTQxMTM1OF19
-->