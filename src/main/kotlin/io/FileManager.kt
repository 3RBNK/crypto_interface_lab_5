package io

import java.nio.file.Files
import java.nio.file.Path

class FileManager {
    fun readFile(path: Path): ByteArray {
        return Files.readAllBytes(path)
    }

    fun writeFile(path: Path, data: ByteArray) {
        Files.write(path, data)
    }
}