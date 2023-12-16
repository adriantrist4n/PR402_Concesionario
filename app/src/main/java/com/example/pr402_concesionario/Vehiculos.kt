// Clase base Vehiculo
open class Vehiculo(
    var ruedas: Int,
    var motor: String,
    var numAsientos: Int,
    var color: String,
    var modelo: String
)

// Clase Coche que hereda de Vehiculo
class Coche(
    ruedas: Int,
    motor: String,
    numAsientos: Int,
    color: String,
    modelo: String
) : Vehiculo(ruedas, motor, numAsientos, color, modelo)

// Clase Moto que hereda de Vehiculo
class Moto(
    ruedas: Int,
    motor: String,
    numAsientos: Int,
    color: String,
    modelo: String
) : Vehiculo(ruedas, motor, numAsientos, color, modelo) {
    init {
        if (numAsientos > 2) {
            throw IllegalArgumentException("Las motos no pueden tener más de dos asientos")
        }
    }
}

// Clase Patinete que hereda de Vehiculo
class Patinete(
    ruedas: Int,
    motor: String,
    color: String,
    modelo: String
) : Vehiculo(ruedas, motor, 0, color, modelo)

// Clase Furgoneta que hereda de Vehiculo
class Furgoneta(
    ruedas: Int,
    motor: String,
    var cargaMaxima: Int,
    color: String,
    modelo: String
) : Vehiculo(ruedas, motor, 0, color, modelo) {
    init {
        if (ruedas > 6) {
            throw IllegalArgumentException("Las furgonetas no pueden tener más de 6 ruedas")
        }
    }
}

// Clase Trailer que hereda de Vehiculo
class Trailer(
    ruedas: Int,
    motor: String,
    var cargaMaxima: Int,
    color: String,
    modelo: String
) : Vehiculo(ruedas, motor, 0, color, modelo) {
    init {
        if (ruedas < 6) {
            throw IllegalArgumentException("Los tráileres deben tener al menos 6 ruedas")
        }
    }
}
