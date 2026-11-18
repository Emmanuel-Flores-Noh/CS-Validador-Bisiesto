# Validador de año bisiesto

## 1. Preparación del proyecto

Se creó un proyecto **Maven** (Java 21) con la dependencia de JUnit 5 (`junit-jupiter`, que incluye
`junit-jupiter-params` para las pruebas parametrizadas) y el plugin Surefire para ejecutar las pruebas
con `mvn test`.

```
validador-bisiesto/
├── pom.xml
└── src/
    ├── main/java/mx/calendario/Calendario.java
    └── test/java/mx/calendario/CalendarioTest.java
```

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.11.4</version>
    <scope>test</scope>
</dependency>
```

## 2. Clase `Calendario`

```java
package mx.calendario;

/**
 * Utilidades del calendario gregoriano.
 */
public class Calendario {

    /**
     * Indica si un año es bisiesto según el calendario gregoriano.
     *
     * @param year año a evaluar
     * @return {@code true} si el año es bisiesto; {@code false} en otro caso
     */
    public boolean esBisiesto(int year) {
        if (year % 400 == 0) {
            return true;   // 1600, 2000, 2400...
        }
        if (year % 100 == 0) {
            return false;  // 1700, 1800, 1900, 2100...
        }
        return year % 4 == 0; // 2024, 1996...
    }
}
```

### ¿Por qué importa el orden de las comprobaciones?

Las reglas se solapan, todo múltiplo de 400 también lo es de 100 y de 4.

Por eso se evalúa primero lo más específico (entre 400), luego la excepción (entre 100) y al final la regla general (entre 4). Si se comprobara primero
`año % 100 == 0`, el 2000 se rechazaría sin llegar a la regla de los 400 y si se comprobara primero
`año % 4 == 0`, el 1900 se aceptaría como bisiesto.

## 3. Casos de prueba

| Categoría | Años | Resultado esperado | Tipo de prueba |
|---|---|---|---|
| Divisible entre 4, no centenario | 1996, 2004, 2020, 2024 | bisiesto | `@ParameterizedTest` |
| No divisible entre 4 | 1999, 2001, 2023, 2025 | no bisiesto | `@ParameterizedTest` |
| Centenario no divisible entre 400 | 1900, 2100 | no bisiesto | `@Test` |
| Centenario divisible entre 400 | 1600, 2000 | bisiesto | `@Test` |
| Caso límite: año 0 | 0 | bisiesto (0 es divisible entre 400) | `@Test` |
| Caso límite: año negativo | -4 | bisiesto (en Java `-4 % 4 == 0`) | `@Test` |
| Caso límite: primer año | 1 | no bisiesto | `@Test` |

## 4. Clase `CalendarioTest` (Paso 4)

Usamos `@ParameterizedTest` + `@ValueSource` para los casos simples, `@Test`
independiente para cada centenario, `assertTrue`/`assertFalse` con mensajes que muestran el año probado,
también usamos la estructura AAA (*Arrange, Act, Assert*) en cada prueba y nombres que describen el comportamiento esperado.
En total se ejecutan **15 pruebas** (8 parametrizadas + 7 `@Test`).

```java
package mx.calendario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Calendario.esBisiesto")
class CalendarioTest {

    // Indicamos un texto de salida para la prueba
    @ParameterizedTest(name = "{0} es bisiesto")
    // Indicamos que toma diversos valores de pruebas en forma de parámetro la prueba
    @ValueSource(ints = {1996, 2004, 2020, 2024})
    void divisibleEntre4NoCentenario_esBisiesto(int year) {
        // Arrange
        Calendario calendario = new Calendario();

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertTrue(resultado,
                "El año " + year + " debería ser bisiesto");
    }

    @ParameterizedTest(name = "{0} no es bisiesto")
    @ValueSource(ints = {1999, 2001, 2023, 2025})
    void noDivisibleEntre4_noEsBisiesto(int year) {
        // Arrange
        Calendario calendario = new Calendario();

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertFalse(resultado,
                "El año " + year + " no debería ser bisiesto");
    }

    @Test
    void centenario1900_noEsBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int year = 1900;

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertFalse(resultado,
                "El año " + year + " no es divisible entre 400");
    }

    @Test
    void centenario2100_noEsBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int year = 2100;

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertFalse(resultado,
                "El año " + year + " no es divisible entre 400");
    }

    @Test
    void centenario2000_esBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int year = 2000;

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertTrue(resultado,
                "El año " + year + " es divisible entre 400");
    }

    /**
     * Es divisible entre 4
     */
    @Test
    void centenario1600_esBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int year = 1600;

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertTrue(resultado,
                "El año " + year + " es divisible entre 400");
    }

    @Test
    void cero_esBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int year = 0; // divisible entre 400 (año astronómico 0 = 1 a.C.)

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertTrue(resultado,
                "El año " + year + " es divisible entre 400");
    }

    @Test
    void negativoCuatro_esBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int year = -4; // en Java -4 % 4 == 0

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertTrue(resultado,
                "El año " + year + " es divisible entre 4");
    }

    @Test
    void primer_noEsBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int year = 1;

        // Act
        boolean resultado = calendario.esBisiesto(year);

        // Assert
        assertFalse(resultado,
                "El año " + year + " no es divisible entre 4");
    }
}
```

## 5. Evidencia de ejecución

### 5.1 Todas las pruebas pasan con la implementación correcta

![Ejecución de mvn test con la implementación correcta: 15 pruebas, 0 fallos](pruebas-pasan.png)

### 5.2 Las pruebas detectan una implementación incorrecta (Paso 5)

Se modificó temporalmente `esBisiesto` eliminando la regla de los 400:

![Cambio temporal: se elimina la comprobación año % 400](cambio-incorrecto.png)

Al ejecutar de nuevo `mvn test`, fallan exactamente las pruebas que dependen de esa regla (1600, 2000 y el
caso límite del año 0), y el mensaje de cada aserción indica qué año se probó:

![Ejecución de mvn test con la implementación incorrecta: 3 fallos](pruebas-fallan.png)

Después se restauró la versión correcta y las 15 pruebas volvieron a pasar.