# Creación y ejecución de un hilo

En este ejemplo crearemos varios hilos y lo ejecutaremos. En [Java](https://docs.oracle.com/javase/8/docs/api/) dispondremos dos formas básicas para poder trabajar con hilos:

1.   Definiendo una clase que herede de la clase `Thread`, clase que define el comportamiento de un hilo en Java, y escribiendo el código en el método `run(.)`. Ese es el código que ejecuta el hilo. Para poder iniciar la ejecución del hilo se invoca método `start()`[^nota1], no será suficiente con la invocación del **constructor** y obtener la referencia que representa al hilo.

2.   Definiendo una clase que implemente la interfaz `Runable`, que nos obliga a implementar el método `run(.)` que se comporta como en el caso anterior. Para poder ejecutar el hilo crearemos un objeto de la clase `Thread`. En el **constructor** se le pasa como parámetro la referencia a un objeto de la clase que hemos definido previamente. Y no debemos olvidar invocar el método `stat()`[^nota1], como en el caso anterior.

En el ejemplo utilizaremos el segundo método que es el más apropiado para poder trabajar con los hilos en Java. El segundo método es el que nos permite trabajar con los hilos de una forma más versátil, al permitir que cualquier objeto se pueda ejecutar en un hilo. Los pasos a realizar serán los siguientes:

1. Creamos una clase con el nombre `Calculator` que implementa la *interfaz*[^nota2]  `Runnable`

```java
public class Calculator implements Runnable {
```

2. Declaramos una variable de instancia que representará a un número entero y definimos el constructor de la clase.

```java
/**
 *  The number
 */
private int number;	
	
/**
 *  Constructor of the class
 * @param number : The number
 */
public Calculator(int number) {
    this.number=number;
}
```

3. Como la clase implementa la interfaz `Runnable` deberemos dar una implementación para el método `run()`. Este método tendrá el código que el hilo deberá ejecutar cuando tenga a su disposición la CPU.

```java
/**
 *  Method that do the calculations
 */
@Override
public void run() {
    for (int i=1; i<=10; i++){
        System.out.printf("%s: %d * %d = %d\n",Thread.currentThread().getName(),number,i,i*number);
    }
}
```

4.  Ya tenemos definida la clase que representará la tarea que debe realizar nuestro hilo. Ahora necesitaremos que nuestra aplicación pueda ejecutar ese hilo. Para ello utilizaremos la clase de la aplicación Java, es decir, la que implementa el método `main(.)`.

5. El método `main(.)` ejecutará un bucle de 10 iteraciones. En cada una de las iteraciones crearemos un objeto de la clase `Calculator` y la referencia a ese objeto se le pasará como argumento al constructor de un objeto de la clase `Thread`. Para poder ejecutar el hilo se invocará el método `star()` de la clase `Thread`.

```java
/**
 *  Main class of the example
 */
public class Main {

    /**
     * Main method of the example
     * @param args
     */
     public static void main(String[] args) {

    //Launch 10 threads that make the operation with a different number
        for (int i=1; i<=10; i++){
            Calculator calculator=new Calculator(i);
            Thread thread=new Thread(calculator);
            thread.start(); 
        }
    }
}
```

6. Ejecutamos la aplicación y comprobamos el resultado.

## Preguntas

 - ¿Si ejecutamos varias veces el programa tiene la misma salida?
    
-  ¿Se ejecutan los hilos en el orden en que fueron creados?
    
-  ¿Se ejecuta cada hilo hasta que completa su trabajo?

- ¿Termina la ejecución del método `main(.)`?


---

[^nota1]: Nunca se puede invocar el método `run()`. 

[^nota2]: En Java una *interfaz* es una estructura abstracta pura, es decir, en una *interfaz* todos los métodos son abstractos (no se implementa ninguno). Permite al diseñador establecer un comportamiento (nombres de métodos, listas de argumentos y tipos de retorno, pero no bloques de código). Ese comportamiento será implementado dentro de una clase, además de otros comportamientos de la clase.


<!--stackedit_data:
eyJoaXN0b3J5IjpbMjA1MjY0MjY4NywxNTc3MjE5Njk0LC03Nj
U3ODQxNzgsLTMxMjAxNDc1NF19
-->