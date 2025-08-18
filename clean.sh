#!/bin/bash
echo "🧹 Limpiando caches de Gradle y KSP..."

# Ruta de tu proyecto (ajusta si es necesario)
PROJECT_DIR="/Users/paultorres/Documents/Projects/Kotlin/kotlinGeoListApp"

# Elimina caches de la app
rm -rf "$PROJECT_DIR/app/build/ksp"
rm -rf "$PROJECT_DIR/app/build/kspCaches"
rm -rf "$PROJECT_DIR/app/build/intermediates"

# Elimina caches globales de Gradle
rm -rf "$PROJECT_DIR/.gradle"

# Limpia con gradle
cd "$PROJECT_DIR" || exit
./gradlew clean

echo "✅ Limpieza completa. Ahora ejecuta ./gradlew build"
