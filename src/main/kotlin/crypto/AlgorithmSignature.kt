package crypto

enum class AlgorithmSignature(
    val keyAlgorithm: String,
    val algorithmSignature: String,
) {
    RSA_SHA256("RSA", "SHA256withRSA"),
    RSA_SHA512("RSA", "SHA512withRSA"),
    DSA("DSA", "SHA256withDSA"),
    ECDSA("EC", "SHA256withECDSA"),
}