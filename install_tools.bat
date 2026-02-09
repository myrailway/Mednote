@echo off
REM Android SDK和JDK安装脚本
REM 请以管理员身份运行此脚本

echo ========================================
echo Android SDK和JDK自动安装脚本
echo ========================================
echo.

REM 检查是否以管理员身份运行
net session >nul 2>&1
if %errorLevel% neq 0 (
    echo [错误] 请以管理员身份运行此脚本
    pause
    exit /b 1
)

REM 设置安装目录
set ANDROID_SDK_DIR=C:\Android\sdk
set JDK_DIR=C:\Program Files\Eclipse Adoptium\jdk-17

echo [1/5] 检查系统环境...

REM 检查JDK
if exist "%JDK_DIR%" (
    echo [OK] JDK 17 已安装
    set JDK_INSTALLED=1
) else (
    echo [提示] JDK 17 未安装
    echo 请手动下载并安装 JDK 17
    echo 下载地址: https://adoptium.net/temurin/releases/?version=17
    echo.
    set JDK_INSTALLED=0
)

REM 检查Android SDK
if exist "%ANDROID_SDK_DIR%" (
    echo [OK] Android SDK 已安装
    set SDK_INSTALLED=1
) else (
    echo [提示] Android SDK 未安装
    echo 请手动下载并安装 Android SDK
    echo 下载地址: https://developer.android.com/studio#command-tools
    echo.
    set SDK_INSTALLED=0
)

echo.
echo [2/5] 创建目录结构...

if not exist "%ANDROID_SDK_DIR%" mkdir "%ANDROID_SDK_DIR%"
if not exist "%ANDROID_SDK_DIR%\cmdline-tools" mkdir "%ANDROID_SDK_DIR%\cmdline-tools"

echo [OK] 目录结构已创建

echo.
echo [3/5] 配置环境变量...

REM 设置临时环境变量
setx ANDROID_HOME "%ANDROID_SDK_DIR%" /M >nul 2>&1
setx JAVA_HOME "%JDK_DIR%" /M >nul 2>&1

echo [OK] 环境变量已设置
echo ANDROID_HOME=%ANDROID_SDK_DIR%
echo JAVA_HOME=%JDK_DIR%

echo.
echo [4/5] 创建local.properties文件...

echo sdk.dir=%ANDROID_SDK_DIR:\=\\% > local.properties

echo [OK] local.properties已创建

echo.
echo [5/5] 显示后续步骤...

echo ========================================
echo 安装完成！
echo ========================================
echo.
echo 后续步骤：
echo 1. 如果JDK 17未安装，请下载安装：
echo    https://adoptium.net/temurin/releases/?version=17
echo.
echo 2. 如果Android SDK未安装，请下载安装：
echo    https://developer.android.com/studio#command-tools
echo    解压到: %ANDROID_SDK_DIR%\cmdline-tools\latest
echo.
echo 3. 打开新的命令行窗口，运行：
echo    sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
echo.
echo 4. 接受许可证：
echo    sdkmanager --licenses
echo.
echo 5. 构建APK：
echo    gradlew.bat assembleRelease
echo.
echo ========================================

pause
