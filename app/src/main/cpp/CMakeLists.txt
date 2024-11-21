cmake_minimum_required(VERSION 3.18.1)

project("checksum")

# Указываем путь к папке с заголовками OpenSSL
include_directories(${CMAKE_SOURCE_DIR}/include)

# Добавляем импортируемые библиотеки OpenSSL
add_library(crypto SHARED IMPORTED)
set_target_properties(crypto PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libcrypto.so)

add_library(ssl SHARED IMPORTED)
set_target_properties(ssl PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libssl.so)

# Добавляем вашу библиотеку checksum
add_library(
        checksum
        SHARED
        checksum.cpp
)

# Линкуем библиотеки
find_library(
        log-lib
        log
)

target_link_libraries(
        checksum
        ssl
        crypto
        ${log-lib}
)
