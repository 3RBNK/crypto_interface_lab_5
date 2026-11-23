package cli

import crypto.AlgorithmSignature
import java.nio.file.Path


enum class Mode {
    KEYGEN,
    SIGN,
    VERIFY
}

data class Argument(
    val mode: Mode,
    val argument: AlgorithmSignature,
    val pathList: List<Path>
)

object ArgumentParser {
    fun parser(args: Array<String>): Argument {
        require(args.isNotEmpty()) {
            "Аргументы не могут быть пустыми"
        }

        val mode = when (args[0].lowercase()) {
            "keygen" -> Mode.KEYGEN
            "sign" -> Mode.SIGN
            "verify" -> Mode.VERIFY
            else -> throw IllegalArgumentException("Неизвестный режим: ${args[0]}")
        }

        val expectedSize = when (mode) {
            Mode.KEYGEN -> 4
            Mode.SIGN -> 5
            Mode.VERIFY -> 5
        }

        require(args.size == expectedSize) {
            "Некорректное количество аргументов"
        }

        val argument = when (args[1].lowercase()) {
            "rsa-sha256" -> AlgorithmSignature.RSA_SHA256
            "rsa-sha512" -> AlgorithmSignature.RSA_SHA512
            "dsa" -> AlgorithmSignature.DSA
            "ecdsa" -> AlgorithmSignature.ECDSA
            else -> throw IllegalArgumentException("Неизвестный алгоритм: ${args[1]}")
        }

        val pathList = args.drop(2).map { Path.of(it) }

        return Argument(
            mode = mode,
            argument = argument,
            pathList = pathList
        )
    }
}
