package io.github.kingsword09.svg2compose

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size < 5) {
        println("Usage: <package_name> <accessor_name> <input_dir> <output_dir> <type(SVG|DRAWABLE)>")
        println("Example: com.myapp.icons AppIcons assets src/main/kotlin SVG")
        exitProcess(1)
    }

    val packageName = args[0]
    val accessorName = args[1]
    val inputDir = File(args[2])
    val outputDir = File(args[3])
    val vectorType = try {
        VectorType.valueOf(args[4].toUpperCase())
    } catch (e: IllegalArgumentException) {
        println("Invalid type: ${args[4]}. Must be SVG or DRAWABLE")
        exitProcess(1)
    }

    if (!inputDir.exists()) {
        println("Input directory does not exist: ${inputDir.absolutePath}")
        exitProcess(1)
    }

    if (!outputDir.exists()) {
        println("Output directory does not exist, creating: ${outputDir.absolutePath}")
        outputDir.mkdirs()
    }

    println("Parsing vectors...")
    println("  Package: $packageName")
    println("  Accessor: $accessorName")
    println("  Input: ${inputDir.absolutePath}")
    println("  Output: ${outputDir.absolutePath}")
    println("  Type: $vectorType")

    try {
        Svg2Compose.parse(
            applicationIconPackage = packageName,
            accessorName = accessorName,
            vectorsDirectory = inputDir,
            outputSourceDirectory = outputDir,
            type = vectorType,
            generatePreview = false
        )
        println("Done!")
    } catch (e: Exception) {
        println("Error during parsing: ${e.message}")
        e.printStackTrace()
        exitProcess(1)
    }
}
