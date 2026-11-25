import cli.Argument
import cli.Mode
import crypto.AlgorithmSignature
import crypto.ServiceSignature
import io.FileManager
import io.KeyStorage
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

class Runner(
    val config: Argument,
) {
    fun run(): Boolean? =
        when (config.mode) {
            Mode.KEYGEN -> {
                keygen()
                null
            }

            Mode.SIGN -> {
                sign()
                null
            }

            Mode.VERIFY -> verify()
        }

    private fun keygen() {
        val keyGenerator = ServiceSignature(config.argument)
        val keyPair = keyGenerator.generateKeyPair()

        val keyStorage = KeyStorage()
        val (privateKeyPath, publicKeyPath) = config.pathList

        keyStorage.save(
            keyPair,
            privateKeyPath,
            publicKeyPath
        )
    }

    private fun sign() {
        val (privateKeyPath, filePath, signFilePath) = config.pathList
        val fileManager = FileManager()
        val algorithm = config.argument

        val privateKey = fileManager
            .readFile(privateKeyPath)
            .toPrivateKey(algorithm)

        val signature = ServiceSignature(algorithm)

        val data = fileManager.readFile(filePath)
        val signFile = signature.sign(data, privateKey)

        fileManager.writeFile(signFilePath, signFile)
    }

    private fun verify(): Boolean {
        val (publicKeyPath, filePath, signFilePath) = config.pathList
        val fileManager = FileManager()
        val algorithm = config.argument

        val publicKey = fileManager
            .readFile(publicKeyPath)
            .toPublicKey(algorithm)

        val data = fileManager.readFile(filePath)
        val signature = fileManager.readFile(signFilePath)

        return ServiceSignature(algorithm).verify(data, signature, publicKey)
    }

    private fun ByteArray.toPrivateKey(algorithm: AlgorithmSignature): PrivateKey {
        return KeyFactory
            .getInstance(algorithm.keyAlgorithm)
            .generatePrivate(PKCS8EncodedKeySpec(this))
    }

    private fun ByteArray.toPublicKey(algorithm: AlgorithmSignature): PublicKey {
        return KeyFactory
            .getInstance(algorithm.keyAlgorithm)
            .generatePublic(X509EncodedKeySpec(this))
    }
}
