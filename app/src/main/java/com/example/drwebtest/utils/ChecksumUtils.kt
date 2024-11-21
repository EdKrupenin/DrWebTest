package com.example.drwebtest.utils

class ChecksumUtils {
    init {
        System.loadLibrary("checksum")
    }

    external fun calculateSHA1(filePath: String): String
}
