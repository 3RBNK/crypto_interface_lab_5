package crypto

import java.security.*

class ServiceSignature(
    private val algorithm: AlgorithmSignature
) {
    fun generateKeyPair(): KeyPair {
        val generator = KeyPairGenerator.getInstance(algorithm.keyAlgorithm)

        when (algorithm) {
            AlgorithmSignature.RSA_SHA256,
            AlgorithmSignature.RSA_SHA512 -> {
                generator.initialize(3072)
            }

            AlgorithmSignature.DSA -> {
                generator.initialize(2048)
            }

            AlgorithmSignature.ECDSA -> {
                generator.initialize(256)
            }
        }

        return generator.generateKeyPair()
    }

    fun sign(data: ByteArray, privateKey: PrivateKey): ByteArray {
        val signer = Signature.getInstance(algorithm.algorithmSignature)

        signer.initSign(privateKey)
        signer.update(data)

        return signer.sign()
    }

    fun verify(data: ByteArray, signature: ByteArray, publicKey: PublicKey): Boolean {
        val verify = Signature.getInstance(algorithm.algorithmSignature)

        verify.initVerify(publicKey)
        verify.update(data)

        return verify.verify(signature)
    }
}