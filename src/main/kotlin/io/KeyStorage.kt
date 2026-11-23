package io

import java.nio.file.Path
import java.security.KeyPair


class KeyStorage {
    fun save(
        keyPair: KeyPair,
        privateKeyPath: Path,
        publicKeyPath: Path,
    ) {
        val fileManager = FileManager()

        fileManager.writeFile(privateKeyPath, keyPair.private.encoded)
        fileManager.writeFile(publicKeyPath, keyPair.public.encoded)
    }
}