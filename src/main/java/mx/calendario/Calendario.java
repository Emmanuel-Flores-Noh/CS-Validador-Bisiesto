package mx.calendario;

/**
 * Utilidades del calendario gregoriano.
 */
public class Calendario {

    /**
     * Indica si un año es bisiesto según el calendario gregoriano.
     *
     * @param año año a evaluar
     * @return {@code true} si el año es bisiesto; {@code false} en otro caso
     */
    public boolean esBisiesto(int año) {
        if (año % 400 == 0) {
            return true;   // 1600, 2000, 2400...
        }
        if (año % 100 == 0) {
            return false;  // 1700, 1800, 1900, 2100...
        }
        return año % 4 == 0; // 2024, 1996...
    }
}
