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