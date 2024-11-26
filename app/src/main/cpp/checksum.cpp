#include <jni.h>
#include <string>
#include <openssl/sha.h>
#include <fstream>
#include <vector>
#include <iomanip>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_drwebtest_utils_ChecksumUtils_calculateSHA1(JNIEnv *env, jobject,
                                                             jstring filePath) {
    const char *nativeFilePath = env->GetStringUTFChars(filePath, nullptr);
    if (!nativeFilePath) {
        env->ThrowNew(env->FindClass("java/lang/IllegalArgumentException"), "File path is null");
        return nullptr;
    }

    try {
        auto file = std::ifstream(nativeFilePath, std::ios::binary);
        env->ReleaseStringUTFChars(filePath, nativeFilePath);
        if (!file.is_open()) {
            env->ThrowNew(env->FindClass("java/io/FileNotFoundException"), "File not found");
            return nullptr;
        }

        SHA_CTX shaContext;
        if (!SHA1_Init(&shaContext)) {
            env->ThrowNew(env->FindClass("java/lang/RuntimeException"), "Failed to initialize SHA-1");
            return nullptr;
        }

        auto buffer = std::vector<unsigned char>(8192);
        while (file.good()) {
            file.read(reinterpret_cast<char *>(buffer.data()), buffer.size());
            SHA1_Update(&shaContext, buffer.data(), file.gcount());
        }
        file.close();

        unsigned char hash[SHA_DIGEST_LENGTH];
        if (!SHA1_Final(hash, &shaContext)) {
            env->ThrowNew(env->FindClass("java/lang/RuntimeException"), "Failed to finalize SHA-1");
            return nullptr;
        }

        char hashString[SHA_DIGEST_LENGTH * 2 + 1];
        for (int i = 0; i < SHA_DIGEST_LENGTH; ++i) {
            sprintf(&hashString[i * 2], "%02x", hash[i]);
        }

        return env->NewStringUTF(hashString);

    } catch (const std::exception &e) {
        env->ReleaseStringUTFChars(filePath, nativeFilePath);
        env->ThrowNew(env->FindClass("java/lang/RuntimeException"), e.what());
        return nullptr;
    } catch (...) {
        env->ReleaseStringUTFChars(filePath, nativeFilePath);
        env->ThrowNew(env->FindClass("java/lang/RuntimeException"), "Unknown error occurred");
        return nullptr;
    }
}
