#!/bin/bash
# Gradle wrapper脚本
echo "开始构建APK..."
# 使用gradle构建
gradle assembleDebug
echo "构建完成！"
