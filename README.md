# Compilador en Java 

Este repositorio contiene un **compilador modular y funcional desarrollado desde cero** como parte de la formación en Ingeniería de Sistemas (UNICEN). El sistema toma un código fuente escrito en un lenguaje estructurado de diseño específico, realiza el análisis léxico, sintáctico y semántico, genera código intermedio mediante notación postfija (polaca inversa) y finalmente produce **código máquina ejecutable (Assembler Intel x86 de 32 bits)** para procesadores Pentium.

El proyecto fue diseñado y construido de forma equitativa y colaborativa en un equipo de 3 integrantes, participando todos activamente en cada fase de la arquitectura de software de base.

---

## Características Destacadas del Lenguaje Soportado

El compilador responde a un conjunto de requerimientos gramaticales y técnicos asignados específicamente por la cátedra:

* **Tipos de Datos de Alta Precisión:** Soporte para enteros largos de 32 bits (`longint`) y punto flotante de 64 bits con notación científica/exponencial opcional (`double`, e.g., exponente con la letra `d`).
* **Constantes Hexadecimales:** Reconocimiento en etapa léxica de formatos bajo el prefijo `0x`.
* **Cadenas Multilínea:** Delimitadas por corchetes `[ ... ]` capaces de ocupar múltiples líneas (la Tabla de Símbolos las almacena normalizadas, removiendo saltos de línea y compactando espacios).
* **Estructuras de Datos y Subrangos:** * Definición de subrangos acotados a partir de tipos básicos usando `typedef` (ej: `typedef enterito := integer[-10,10];`).
    * Soporte para `Struct` parametrizados delimitados por paréntesis para evitar ambigüedades con bloques sintácticos (ej: `typedef Struct <integer, double> { a, b } ts1;`).
* **Estructuras de Control Avanzadas:** Bucles `WHILE` tradicionales y estructuras post-condicionales `REPEAT ... WHILE (condición);`.
* **Asignaciones Múltiples:** Capacidad de procesar asignaciones en paralelo separadas por comas (ej: `a, b, c := 1+d, e, f+5;`).
* **Sentencia de Salto Incondicional (`goto`):** Reconocimiento del token etiqueta (`nombre:`) y resolución diferida de la dirección de salto en las fases posteriores.
* **Conversiones Explícitas:** Operador de conversión de tipos `TOD(<expresión>)`.

---

## Arquitectura y Decisiones de Diseño

El compilador adopta una arquitectura desacoplada en 4 etapas principales implementadas sobre **Java**:

### 1. Analizador Léxico (Lexer)
* **Procesamiento:** Basado en una **Matriz de Transición de Estados** $[N][M]$ y una **Matriz de Acciones Semánticas** asociadas mediante código controlado por un *Switch-Case*.
* **Patrones de Diseño:** La clase de `AccionesSemanticas` se estructuró bajo el patrón **Singleton** para optimizar la instanciación única en memoria durante el procesamiento de caracteres.
* **Estrategia de Tokens:** El Lexer separa los operadores unarios del número base (manejando los signos negativos en el Parser). Se implementó un sistema de recirculación de caracteres (`LectorTexto`) con símbolos especiales (`?`) para evitar desbordamientos al final de línea o del archivo.

### 2. Analizador Sintáctico y Semántico (Parser)
* **Herramientas:** Construido sobre **YACC / BYACC/J**.
* **Resolución de Ambigüedades:** Se rediseñó la gramática abstracta en reglas intermedias específicas (como `bloque_sentencia_ejecutable`) para mitigar y solucionar conflictos complejos de tipo *Never Reduce*, *Reduce/Reduce* y *Shift/Reduce*.
* **Tabla de Símbolos:** Implementada mediante un mapa dinámico `<String, DatosTablaSimbolos>`. Resuelve las colisiones de **Reglas de Alcance (*Scope*)** renombrando dinámicamente las claves con la sintaxis `variable@ambito`, logrando que variables homónimas coexistan de manera aislada.

### 3. Generación de Código Intermedio
* **Técnica:** **Polaca Inversa (Notación Postfija)** administrada dinámicamente en un `ArrayList<String>`.
* **Bifurcaciones:** Las estructuras `if-then-else` y `while` se resuelven mediante el uso de pilas (`Stack<Integer>`) que almacenan punteros temporales en estado "PENDIENTE", aplicando parches de direcciones (*backpatching*) en una sola pasada.
* **Tratamiento de Funciones:** Se procesan listas independientes de polacas (`ArrayList<ArrayList<String>>`) discriminadas mediante una bandera booleana `esUnaFuncion` para mantener aislado el contexto del flujo principal.

### 4. Generación de Código Máquina (Assembler x86)
* La clase `GeneradorCodigo` traduce secuencialmente la estructura postfija a instrucciones válidas para procesadores de 32 bits.
* **Optimización de Registros:** Estrategia de **Seguimiento de Registros** (*Register Tracking*) en el arreglo de registros generales (`eax, ebx, ecx, edx`). Esto redujo un ~50% la necesidad de variables temporales en memoria.
* **Aritmética FPU:** Uso nativo de la unidad de punto flotante para operaciones con datos de tipo `DOUBLE`.

---

## Robustez y Manejo de Errores en Tiempo de Ejecución (*Runtime*)

El sistema no solo reporta errores de compilación (léxicos/sintácticos con número de línea), sino que inyecta código de control en el encabezado del archivo `.asm` de salida para evaluar situaciones críticas en tiempo de ejecución:

1.  **División por Cero:** Validación previa mediante instrucciones condicionales tanto para aritmética entera (`longint`) como flotante (`double` vía FPU).
2.  **Overflow en Productos:** Monitoreo del desborde mediante la instrucción `JO` (*Jump on Overflow*).
3.  **Validación de Rangos y Subtipos:** Inyección de llamadas de control (`CALL`) que evalúan los límites mínimos y máximos permitidos antes de consolidar una asignación.

---

## Reflexiones, Autocrítica y Deuda Técnica

Este proyecto representó nuestro primer gran desafío enfrentando la complejidad del desarrollo de software de sistema, aportando un entendimiento profundo sobre cómo los lenguajes de alto nivel interactúan íntimamente con el procesador.

Al analizar el proyecto retrospectivamente, identificamos puntos clave de mejora:

* **Estructura del Proyecto:** Somos conscientes de que **la organización y arquitectura de archivos actual no es la más óptima**. Refleja el proceso de aprendizaje incremental durante la cursada académica. Con la experiencia adquirida hoy en día, el proyecto se habría iniciado bajo una estructura de directorios y paquetes mucho más sólida, modular y fácil de seguir.
* **Manejo de Errores Gramaticales:** En la versión inicial, ciertos casos de sintaxis inválida (como la omisión de puntos y comas `;` o comas `,` en asignaciones múltiples) producían excepciones de ejecución en YACC en lugar de recuperarse limpiamente. Una mejora prioritaria sería refactorizar la gramática incorporando el token nativo `error` de YACC de manera más estratégica.
* **Desacoplamiento Semántico:** Migrar validaciones que quedaron ligadas a la fase de traducción intermedia directamente hacia un analizador semántico dedicado basado en el patrón *Visitor*.