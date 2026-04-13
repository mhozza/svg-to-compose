---
name: svg-to-compose
description: "Converts SVG and Android Vector Drawable XML files to Compose ImageVector code. Use when adding or updating icon resources in a Jetpack Compose project, specifically to migrate assets or build icon packs."
---

# svg-to-compose

This skill provides the ability to convert vector graphic assets (SVG and Android Vector Drawable XML) directly into Jetpack Compose `ImageVector` Kotlin source code.

## Workflow

1.  **Identify Assets**: Locate the SVG or Vector Drawable XML files that need conversion.
2.  **Define Configuration**:
    *   `package_name`: The Kotlin package for the generated files (e.g., `com.myapp.icons`).
    *   `accessor_name`: The name of the object to access icons (e.g., `AppIcons`).
    *   `input_dir`: Directory containing the source vector files.
    *   `output_dir`: Target directory for the generated Kotlin code.
    *   `type`: Either `SVG` or `DRAWABLE` (for Android Vector Drawable XML).
3.  **Execute Conversion**: Use the `convert.sh` tool bundled with this skill.

## Tool Usage

The skill includes a conversion script located at `scripts/convert.sh`.

```bash
# General usage
scripts/convert.sh <package_name> <accessor_name> <input_dir> <output_dir> <type: SVG|DRAWABLE>

# Example: Convert SVG icons
scripts/convert.sh com.myapp.icons LineaIcons ./assets/linea-icons ./src/main/kotlin SVG

# Example: Convert Android Vector Drawables
scripts/convert.sh com.myapp.icons AppIcons ./res/drawables ./src/main/kotlin DRAWABLE
```

## Tips

- **Icon Packs**: For organized icon packs, use subdirectories in the input directory. The tool supports subgroups (e.g., `IconPack/group/icon.svg` results in `IconPack.Group.Icon`).
- **Accessor Access**: Generated icons are accessed as `AccessorName.IconName` or `AccessorName.Group.IconName`.
- **Batch Processing**: The tool recursively processes all files matching the specified type in the input directory.
