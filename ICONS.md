# MedNote Android App - 图标说明

## 需要添加的图标文件

请在 `app/src/main/res/mipmap-*` 目录下添加以下PNG图标文件：

### mipmap-mdpi (48x48)
- ic_launcher.png
- ic_launcher_round.png

### mipmap-hdpi (72x72)
- ic_launcher.png
- ic_launcher_round.png

### mipmap-xhdpi (96x96)
- ic_launcher.png
- ic_launcher_round.png

### mipmap-xxhdpi (144x144)
- ic_launcher.png
- ic_launcher_round.png

### mipmap-xxxhdpi (192x192)
- ic_launcher.png
- ic_launcher_round.png

## 快速生成图标

你可以使用以下工具生成应用图标：

1. Android Studio -> Image Asset Studio
2. 在线工具：https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html
3. 使用你自己的设计工具

## 临时解决方案

如果暂时没有图标，可以使用Android Studio的默认图标或者使用以下命令生成简单的占位图标：

```bash
# 在Android Studio中，右键点击res文件夹
# 选择 New -> Image Asset
# 按照向导生成图标
```

## 注意

- 图标应该使用紫色主题 (#6200EE) 以匹配应用主题
- 建议使用医疗相关的图标设计
- 确保图标在不同尺寸下都清晰可见
