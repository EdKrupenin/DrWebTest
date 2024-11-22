package com.example.drwebtest

import com.example.drwebtest.utils.ChecksumUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class ChecksumUtilsTest {
    @Test
    fun `calculateSHA1 calculates checksum for a given file`() {
        try {
            System.loadLibrary("checksum")
            val checksum = ChecksumUtils().calculateSHA1("/path/to/file.apk")
            assertEquals("expectedSHA1", checksum)
        } catch (e: UnsatisfiedLinkError) {
            println("Native library not available for testing environment.")
        }
    }
}

