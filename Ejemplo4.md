# Controlar la interrupción de la ejecución de un hilo

En el ejemplo anterior, se ha presentado la forma en que se puede solicitar la interrupción de un hilo y cómo la clase `Thread` tiene elementos que permiten controlar esta interrupción. Dado que será **el propio hilo el que tendrá que atender la petición de interrupción**, y puede que la tarea que tenga asociada el propio hilo no sea trivial, deberemos tener mejores métodos que nos permitan tratar la interrupción de la ejecución de un hilo. En Java disponemos de la excepción [InterruptedException](https://docs.oracle.com/javase/8/docs/api/java/lang/InterruptedException.html) para este fin. Podremos lanzar esta excepción cuando se detecte la solicitud de interrupción de la ejecución de un hilo y tratarla, *capturarla*,  en el método `run()` del hilo que debe ser interrumpido.

En el ejemplo se presenta la implementación de un hilo que realiza la búsqueda de un fichero dentro de una estructura de directorios. Utilizaremos la excepción `InterruptedException` para controlar la interrupción de la ejecución de este hilo.

1. Crearemos una clase llamada `FileSearch` que implementará la interfaz `Runnable`.
2. Definiremos dos variables de instancia para indicar el fichero a buscar y el punto de partida de la búsqueda. Además definiremos el constructor de la clase.

```java
/**
 * This class search for files with a name in a directory
 */
public class FileSearch implements Runnable {

    /**
     * Initial path for the search
     */
    private String initPath;
    /**
     * Name of the file we are searching for
     */
    private String fileName;

    /**
     * Constructor of the class
     * 
     * @param initPath
     *            : Initial path for the search
     * @param fileName
     *            : Name of the file we are searching for
     */
    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }
...
```

3. Implementamos el método `run()`. Deberá comprobar si el inicio de la búsqueda es un directorio y de serlo invocará al método `directoryProcess(.)`. Este método puede lanzar una excepción `InterruptedException` y por tanto deberemos tratarla (*capturarla*).

```java
/**
 * Main method of the class
 */
@Override
public void run() {
    File file = new File(initPath);
    if (file.isDirectory()) {
        try {
            directoryProcess(file);
        } catch (InterruptedException e) {
            System.out.printf("%s: The search has been interrupted",Thread.currentThread().getName());
            cleanResources();
        }
    }
}
```

La forma que tiene Java para tratar con las excepciones *capuradas* es el bloque de sentencia:

```java
try {
    // Bloque de sentencias que pueden lanzar una excepción capturada
    // Cuando se produce la excepción se interrumpe el bloque por la
    //   sentencia que produce la excpeción y se pasa al bloque catch
} catch {//Tipo de excepción}
    // Bloque de sentencias para dar respuesta a la excepción
} ... 
    // Puede haber más de un bloque catch, uno por cada tipo de
    //   excepción 
```

4. Implementaremos el método `directoryProcess(.)`. Obtendremos los ficheros y directorios que contiene. Para cada directorio realizaremos una *invocación recursiva* pasando como parámetro cada uno de ellos. Para cada fichero invocamos el método `fileProcess(.)`. Después de comprobar todos los ficheros y directorios comprobaremos si se ha solicitado la interrupción del hilo. En caso afirmativo lanzaremos la excepción `InterruptedException`.

```java
/**
 * Method that process a directory
 * 
 * @param file
 *            : Directory to process
 * @throws InterruptedException
 *            : If the thread is interrupted
 */
 private void directoryProcess(File file) throws InterruptedException {

    // Get the content of the directory
    File list[] = file.listFiles();
    if (list != null) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].isDirectory()) {
                // If is a directory, process it
                directoryProcess(list[i]);
            } else {
                // If is a file, process it
                fileProcess(list[i]);
            }
        }
    }
    // Check the interruption
    if (Thread.interrupted()) {
        throw new InterruptedException();
    }
}
```

5. Implementamos el método `fileProcess(.)`. Comprobaremos el nombre con el del fichero que estamos buscando. Si coincide escribiremos un mensaje en la consola. Para finalizar comprobaremos si se ha solicitado la interrupción de la ejecución del hilo. En caso afirmativo procederemos como en el punto anterior.

```java
/**
 * Method that process a File
 * 
 * @param file
 *            : File to process
 * @throws InterruptedException
 *            : If the thread is interrupted
 */
private void fileProcess(File file) throws InterruptedException {
    // Check the name
    if (file.getName().equals(fileName)) {
        System.out.printf("%s : %s\n",Thread.currentThread().getName() ,file.getAbsolutePath());
    }

    // Check the interruption
    if (Thread.interrupted()) {
        throw new InterruptedException();
    }
}
```

6. Ahora debemos implementar el método `man(.)` de la aplicación. Se creará e inicializará un objeto de la clase `FileSearch` y se lo asociaremos a un objeto de la clase `Thread` para su ejecución.

```java
...
// Creates the Runnable object and the Thread to run it
FileSearch searcher=new FileSearch("..//..//..","build.xml");
Thread thread=new Thread(searcher);
		
// Starts the Thread
thread.start();
...
```

7. Esperamos 10 segundos antes de solicitar la interrupción de la ejecución del hilo.

```java
...
// Wait for ten seconds
try {
    TimeUnit.SECONDS.sleep(10);
} catch (InterruptedException e) {
    e.printStackTrace();
}
		
// Interrupts the thread
thread.interrupt();
...
```

8. Ejecutar la aplicación y comprobar los resultados.

## Preguntas

-   Si el fichero no se encuentra, ¿cuál sería el resultado?    
-   ¿Se interrumpirá la ejecución del hilo en cualquier momento?
-   ¿Qué partes de la tarea que debe realizar el hilo se consideran que han de finalizar?
-   ¿Dónde se tratará la excepción programada?
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTE4ODAyNzQ3OSwtMzQxNDI5NTA2XX0=
-->