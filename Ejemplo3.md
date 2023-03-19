# Interrumpir la ejecución de un hilo

Una aplicación **Java termina su ejecución cuando todos los hilos que tenga asociados terminen** (más concretamente, todos los hilos no `demons` o no se invoque el método `System.exit()`). Puede que no podamos esperar a la terminación de todos los hilos o que queramos cancelar la tarea asociada a algún hilo.

Java proporciona los elementos necesarios para solicitar la finalización de la ejecución de un hilo. Este método no garantiza si el hilo ha finalizado su ejecución debiendo comprobar el estado de ejecución del hilo. Además, será responsabilidad del hilo atender la petición o no, por lo que podría ignorarla y continuar con su ejecución.

En el ejemplo se presentará un programa que creará hilos y, pasados 5 segundos, finaliza la ejecución de los mismos.

1. Definimos una clase llamada `PrimeGenerator` que heredará de `Thread`.
2. Implementamos el método `run()` para que ejecute un bucle infinito. Iniciaremos el bucle en 1 y proseguiremos con un incremento de 1. En cada iteración comprobaremos si es un número primo o no.

```java
/**
 *  This class generates prime numbers until is interrumped
 */
public class PrimeGenerator extends Thread {

    /**
     *  Central method of the class
     */
    @Override
    public void run() {
        long number=1L;
		
        // This bucle never ends... until is interrupted
        while (true) {
            if (isPrime(number)) {
                System.out.printf("Number %d is Prime\n",number);
            }
...
```

3. Antes de finalizar la iteración, comprobaremos si se ha solicitado la interrupción invocando el método `isInterrupted()`. Devolverá `true` si ha habido una solicitud de interrupción.

```java
...
// When is interrupted, write a message and ends
if (isInterrupted()) {
    System.out.printf("The Prime Generator has been Interrupted\n");
    return;
}
number++;
...
```

4. Deberemos implementar el método `isPrime(.)` que devolverá `true` si el número que comprobamos es primo y `false` en otro caso.
5. Ahora pasaremos a implementar el método `main(.)` de la clase que representa la aplicación Java.
6. Crearemos un objeto de la clase `PrimeGenerator` e iniciaremos su ejecución.

```java
// Launch the prime numbers generator
Thread task=new PrimeGenerator();
task.start();
```

7. Esperamos 5 segundos y solicitamos la interrupción del hilo.

```java
// Wait 5 seconds
try {
    TimeUnit.SECONDS.sleep(5);
} catch (InterruptedException e) {
    e.printStackTrace();
}
		
// Interrupt the prime number generator
task.interrupt();
```

8. Ejecutamos para comprobar el resultado.

  
## Preguntas

-   ¿El tiempo de ejecución siempre será de 5 segundos? -- Si, mas o menos    
-   ¿Qué pasa si se solicita la interrupción cuando se está calculando si un número es primo? -- No calcula el siguiente
-   ¿Siempre se atiende la solicitud de interrupción? -- Si
-   ¿Fallará la solicitud de interrupción? -- No
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTc0ODE0NjUwMiw1NDExNzg2MTZdfQ==
-->