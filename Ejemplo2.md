# Incluir y obtener información de un hilo

[Java](https://docs.oracle.com/javase/8/docs/api/) proporciona cierta información útil para que podamos tener un mejor control de los hilos cuando los utilizamos en nuestras aplicaciones. La información que puede ser útil en el manejo de un hilo es:

-   `Id`: Es un identificador único para cada hilo que se crea.
-   `Name`: Nos permite asignar un nombre al hilo.
-   `Priority`: Asigna un valor numérico entre 1 y 10 que nos permite dar diferentes prioridades a los hilos para que puedan acceder a la CPU con mayor facilidad. Todos los hilos que se crean en una aplicación tienen la misma prioridad. No se recomienda cambiar la prioridad a los hilos.
-   `Status`: Almacena el estado de ejecución en que se encuentra un hilo. Hay 6 estados: `new`, `runnable`, `blocked`, `waiting`, `timewaiting` o `terminated`.

![][estadoHilo]

En el ejemplo se cambiará el nombre y la prioridad para 10 hilos y posteriormente presentarán información sobre su estado antes de su finalización. El hilo presentará la tabla de multiplicar.

1. Utilizaremos la misma clase `Calculator` utilizada en el ejemplo anterior.
2. Ahora modificaremos la método `main(.)` de la clase que representa nuestra aplicación Java.
3. Crearemos un array que nos permita almacenar 10 objetos de la clase `Thread` y su estado.

```java
...
// Thread priority infomation 
System.out.printf("Minimum Priority: %s\n",Thread.MIN_PRIORITY);
System.out.printf("Normal Priority: %s\n",Thread.NORM_PRIORITY);
System.out.printf("Maximun Priority: %s\n",Thread.MAX_PRIORITY);
		
Thread threads[];
Thread.State status[];
		
// Launch 10 threads to do the operation, 5 with the max
// priority, 5 with the min
threads=new Thread[10];
status=new Thread.State[10];
...
```

4. Crearemos 10 objetos de la clase `Calculator` que asociaremos a cada uno de los objetos de la clase `Thread`. Asociaremos a cada hilo una prioridad y un nombre para poder diferenciarlos.

```java
...
for (int i=0; i<10; i++){
    threads[i]=new Thread(new Calculator(i));
    if ((i%2)==0){
        threads[i].setPriority(Thread.MAX_PRIORITY);
    } else {
        threads[i].setPriority(Thread.MIN_PRIORITY);
    }
        threads[i].setName("Thread "+i);
}
...
```

5. Crearemos un objeto de la clase `PrintWriter`[^nota1] que nos permitirá almacenar en un fichero el estado de ejecución para cada uno de los hilos.

```java
...
// Wait for the finalization of the threads. Meanwhile, 
// write the status of those threads in a file
try (FileWriter file = new FileWriter(".\\data\\log.txt");PrintWriter pw = new PrintWriter(file);){
			
    for (int i=0; i<10; i++){
        pw.println("Main : Status of Thread "+i+" : "+threads[i].getState());
        status[i]=threads[i].getState();
    }
...
```

6. . Iniciaremos la ejecución de los 10 hilos. Durante la ejecución de los hilos se comprobará si cambia su estado y se almacenará ese cambio.

```java
...
for (int i=0; i<10; i++){
    threads[i].start();
}
			
boolean finish=false;
while (!finish) {
    for (int i=0; i<10; i++){
        if (threads[i].getState()!=status[i]) {
            writeThreadInfo(pw, threads[i],status[i]);
            status[i]=threads[i].getState();
        }
    }
				
    finish=true;
    for (int i=0; i<10; i++){
        finish=finish &&(threads[i].getState()==State.TERMINATED);
    }
}
...
```

7. Se creará el método `writeThreadInfo(.)` que almacenará información relacionada de los hilos en el fichero.

```java
/**
 *  This method writes the state of a thread in a file
 * @param pw : PrintWriter to write the data
 * @param thread : Thread whose information will be written
 * @param state : Old state of the thread
 */
 private static void writeThreadInfo(PrintWriter pw, Thread thread, State state) {
    pw.printf("Main : Id %d - %s\n",thread.getId(),thread.getName());
    pw.printf("Main : Priority: %d\n",thread.getPriority());
    pw.printf("Main : Old State: %s\n",state);
    pw.printf("Main : New State: %s\n",thread.getState());
    pw.printf("Main : ************************************\n");
}
```

8. Ejecutar el programa y comprobar el resultado en el fichero **log.txt**.

  

## Preguntas

-   ¿Si ejecutamos varias veces el programa tiene la misma salida?    
-   ¿Se ejecutan los hilos en el orden en que fueron creados?
-   ¿Se ejecuta cada hilo hasta que completa su trabajo?
-   ¿Termina la ejecución del método `main(.)`?


[estadoHilo]: https://gitlab.com/ssccdd/guionsesion2/raw/master/img/estadoHilo.jpg "Estados de un Hilo en Java"

---
[^nota1]: La clase `PrintWriter` de la biblioteca [java.io](https://docs.oracle.com/javase/8/docs/api/java/io/package-summary.html) se utiliza para escribir ficheros de texto, la clase `File` y `Scanner` para leer ficheros de texto.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTcyMTA4NzM2MSwtMTM4NzA3MDcwMiwxNT
kxNjU2OTg2XX0=
-->