@echo off
REM 快速构建APK脚本

echo ========================================
echo MedNote APK 构建脚本
echo ========================================
echo.

REM 检查local.properties
if not exist "local.properties" (
    echo [错误] local.properties 文件不存在
    echo 请先运行 install_tools.bat 配置环境
    pause
    exit /b 1
)

REM 检查Gradle Wrapper
if not exist "gradlew.bat" (
    echo [错误] gradlew.bat 不存在
    pause
    exit /b 1
)

echo [1/3] 清理之前的构建...
call gradlew.bat clean
if %errorlevel% neq 0 (
    echo [错误] 清理失败
    pause
    exit /b 1
)

echo [OK] 清理完成
echo.

echo [2/3] 构建Release APK...
call gradlew.bat assembleRelease
if %errorlevel% neq 0 (
    echo [错误] 构建失败
    echo 请检查：
    echo 1. JDK 17 是否已安装
    echo 2. Android SDK 是否已安装
    echo 3. 网络连接是否正常
    pause
    exit /b 1
)

echo [OK] 构建完成
echo.

echo [3/3] 查找APK文件...
if exist "app\build\outputs\apk\release\app-release.apk" (
    echo ========================================
    echo 构建成功！
    echo ========================================
    echo.
    echo APK文件位置：
    echo app\build\outputs\apk\release\app-release.apk
    echo.
    echo 文件大小：
    for %%A in ("app\build\outputs\apk\release\app-release.apk") do echo %%~zA 字节
    echo.
    echo 您可以将此APK文件传输到手机上安装
    echo ========================================
    
    REM 询问是否打开文件夹
    set /p OPEN_FOLDER="是否打开APK所在文件夹？(Y/N): "
    if /i "%OPEN_FOLDER%"=="Y" (
        explorer "app\build\outputs\apk\release"
    )
) else (
    echo [错误] APK文件未找到
    echo 构建可能未成功完成
)

echo.
pause
