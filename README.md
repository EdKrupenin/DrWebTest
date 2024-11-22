# DrWebTest

**DrWebTest** — это Android-приложение, которое сканирует установленные приложения на устройстве и отображает информацию о них, включая название, иконку, имя пакета и контрольную сумму APK-файла.

## 📋 Основные функции

- **Просмотр списка установленных приложений**:
  - Отображает список всех приложений, установленных на устройстве, отсортированный по алфавиту.
- **Просмотр деталей приложения**:
  - Переход на экран деталей для отображения версии, имени пакета, иконки и контрольной суммы APK.
- **Запуск приложения**:
  - Возможность запустить выбранное приложение прямо из списка.

---

## 🚀 Технологии

- **Язык разработки**: Kotlin
- **Архитектура**: MVVM + Clean Architecture
- **DI (Dependency Injection)**: Hilt
- **UI**: Jetpack Compose
- **Асинхронная обработка**: Kotlin Coroutines + StateFlow
- **Сетевое взаимодействие**: Retrofit
- **Тестирование**:
  - Unit-тесты: JUnit 4 + MockK
  - Интеграционные тесты: Hilt Android Test (опционально)

---

## 🛠️ Установка

1. **Клонировать репозиторий:**

   ```bash
   git clone https://github.com/your-username/DrWebTest.git
   cd DrWebTest
   ```
2. **Синхронизировать зависимости Gradle**:
  - После открытия проекта Android Studio автоматически загрузит все необходимые зависимости.

3. **Запустить проект**:
  - Подключите физическое устройство или настройте эмулятор Android.
  - Нажмите кнопку "Run" в Android Studio.

---

## 🔑 Основные модули

### Репозитории
Реализуют взаимодействие с данными:

- **AppRepository**:
  - Получение списка приложений через `PackageManager`.
  - Вычисление контрольной суммы APK-файла через `ChecksumUtils`.

### UI
- **AppNavigation**:
  - Реализует навигацию между экранами (Jetpack Compose + Navigation).
- **AppListScreen**:
  - Отображает список приложений.
- **AppDetailScreen**:
  - Показывает детали выбранного приложения.

### ViewModel
Используется для управления состоянием и взаимодействия с репозиториями:

- **MainActivityViewModel**:
  - Загружает список приложений.
  - Управляет событиями запуска приложения и выбора деталей.

---

## 🛠️ Использование Prebuilt OpenSSL для Android

В проекте используется [**Prebuilt-OpenSSL-Android**](https://github.com/Sharm/Prebuilt-OpenSSL-Android) — репозиторий с готовыми сборками OpenSSL для Android. Это значительно упрощает интеграцию OpenSSL в проект, устраняя необходимость компиляции библиотеки с исходного кода для каждой архитектуры устройства.

---

### 📋 Почему выбран Prebuilt OpenSSL
1. **Сборки для Android**:
  - Репозиторий предоставляет готовые бинарные файлы OpenSSL, скомпилированные для популярных архитектур Android (armeabi-v7a, arm64-v8a, x86, x86_64).

2. **Экономия времени**:
  - Использование готовых сборок позволяет избежать сложного процесса сборки OpenSSL с исходников, включая настройку Android NDK и CMake.

3. **Поддержка современных версий OpenSSL**:
  - Регулярные обновления с последними стабильными версиями OpenSSL.

4. **Удобная интеграция**:
  - Библиотеки легко подключаются через папку `jniLibs`, что позволяет быстро настроить их использование в Android Studio.

---
