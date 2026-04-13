#!/bin/bash
set -e

# Change directory to the project root (where the script is located)
cd "$(dirname "$0")"

# 1. Build
echo "Building shadowJar..."
JAVA_HOME=/usr/lib/jvm/java-1.21.0-openjdk-amd64 ./gradlew shadowJar

# 2. Sync
echo "Syncing JAR..."
# Find any *all.jar in build/libs.
SRC_JAR=$(ls build/libs/*-all.jar | head -n 1)
if [[ -z "$SRC_JAR" ]]; then
    echo "Error: Could not find shadow jar in build/libs."
    exit 1
fi
cp "$SRC_JAR" skill-dist/svg-to-compose/assets/svg-to-compose-all.jar

# 3. Package
echo "Packaging skill..."
# Re-zips the skill-dist/svg-to-compose directory into svg-to-compose.skill
# Ensure we're in the right directory for zipping (SKILL.md should be at the root of the archive)
rm -f svg-to-compose.skill
(cd skill-dist/svg-to-compose && zip -rq ../../svg-to-compose.skill .)

# 4. Install
echo "Installing skill..."
if command -v gemini &> /dev/null; then
    gemini skills install svg-to-compose.skill --scope workspace
else
    echo "Helpful Note: 'gemini' command is not in your current PATH."
    echo "To install the skill manually, please run:"
    echo "gemini skills install svg-to-compose.skill --scope workspace"
fi

echo "Skill updated successfully!"
