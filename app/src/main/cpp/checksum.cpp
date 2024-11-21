#include <jni.h>
#include <string>
#include <openssl/sha.h>
#include <fstream>
#include <sstream>
#include <vector>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_drwebtest_utils_ChecksumUtils_calculateSHA1(JNIEnv *env, jobject, jstring filePath) {
    const char *nativeFilePath = env->GetStringUTFChars(filePath, nullptr);

    std::ifstream file(nativeFilePath, std::ios::binary);
    if (!file.is_open()) {
        env->ReleaseStringUTFChars(filePath, nativeFilePath);
        return env->NewStringUTF("Error: File not found");
    }

    SHA_CTX shaContext;
    SHA1_Init(&shaContext);

    std::vector<unsigned char> buffer(8192);
    while (file.good()) {
        file.read(reinterpret_cast<char *>(buffer.data()), buffer.size());
        SHA1_Update(&shaContext, buffer.data(), file.gcount());
    }

    unsigned char hash[SHA_DIGEST_LENGTH];
    SHA1_Final(hash, &shaContext);

    file.close();

    std::ostringstream result;
    for (unsigned char byte : hash) {
        result << std::hex << std::setw(2) << std::setfill('0') << static_cast<int>(byte);
    }

    env->ReleaseStringUTFChars(filePath, nativeFilePath);
    return env->NewStringUTF(result.str().c_str());
}