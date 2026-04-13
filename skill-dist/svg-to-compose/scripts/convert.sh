#!/bin/bash
# convert.sh: Wrapper for svg-to-compose CLI
# Arguments: package_name accessor_name input_dir output_dir type(SVG|DRAWABLE)

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR_PATH="$SCRIPT_DIR/../assets/svg-to-compose-all.jar"

if [ "$#" -ne 5 ]; then
    echo "Usage: $0 <package_name> <accessor_name> <input_dir> <output_dir> <type: SVG|DRAWABLE>"
    exit 1
fi

# Ensure Java 21 is used if available, otherwise try default
if [ -d "/usr/lib/jvm/java-1.21.0-openjdk-amd64" ]; then
    export JAVA_HOME="/usr/lib/jvm/java-1.21.0-openjdk-amd64"
    PATH="$JAVA_HOME/bin:$PATH"
fi

java -jar "$JAR_PATH" "$@"
