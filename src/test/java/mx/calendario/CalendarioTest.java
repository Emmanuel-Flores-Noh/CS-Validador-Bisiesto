package mx.calendario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Calendario.esBisiesto")
class CalendarioTest {

    @ParameterizedTest(name = "{0} es bisiesto")
    @ValueSource(ints = {1996, 2004, 2020, 2024})
    void añoDivisibleEntre4NoCentenario_esBisiesto(int año) {
        // Arrange
        Calendario calendario = new Calendario();

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertTrue(resultado,
                "El año " + año + " debería ser bisiesto");
    }

    @ParameterizedTest(name = "{0} no es bisiesto")
    @ValueSource(ints = {1999, 2001, 2023, 2025})
    void añoNoDivisibleEntre4_noEsBisiesto(int año) {
        // Arrange
        Calendario calendario = new Calendario();

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertFalse(resultado,
                "El año " + año + " no debería ser bisiesto");
    }

    @Test
    void centenario1900NoDivisibleEntre400_noEsBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int año = 1900;

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertFalse(resultado,
                "El año " + año + " no es divisible entre 400");
    }

    @Test
    void centenario2100NoDivisibleEntre400_noEsBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int año = 2100;

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertFalse(resultado,
                "El año " + año + " no es divisible entre 400");
    }

    @Test
    void centenario2000DivisibleEntre400_esBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int año = 2000;

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertTrue(resultado,
                "El año " + año + " es divisible entre 400");
    }

    @Test
    void centenario1600DivisibleEntre400_esBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int año = 1600;

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertTrue(resultado,
                "El año " + año + " es divisible entre 400");
    }

    @Test
    void casoLimiteAñoCero_esBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int año = 0; // divisible entre 400 (año astronómico 0 = 1 a.C.)

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertTrue(resultado,
                "El año " + año + " es divisible entre 400");
    }

    @Test
    void casoLimiteAñoNegativoMenos4_esBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int año = -4; // en Java -4 % 4 == 0

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertTrue(resultado,
                "El año " + año + " es divisible entre 4");
    }

    @Test
    void casoLimiteAño1_noEsBisiesto() {
        // Arrange
        Calendario calendario = new Calendario();
        int año = 1;

        // Act
        boolean resultado = calendario.esBisiesto(año);

        // Assert
        assertFalse(resultado,
                "El año " + año + " no es divisible entre 4");
    }
}
