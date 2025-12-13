package com.adamglin.zithian.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import java.io.OutputStream

class DeviceProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }

        val symbols = resolver.getSymbolsWithAnnotation("com.adamglin.zithian.Device")
            .filterIsInstance<KSPropertyDeclaration>()
            .toList()

        if (symbols.isEmpty()) {
            return emptyList()
        }

        invoked = true
        generateFile(symbols)
        return emptyList()
    }

    private fun generateFile(symbols: List<KSPropertyDeclaration>) {
        val packageName = "com.adamglin.zithian.generated"
        val fileName = "RegisteredDevices"

        val dependencies = Dependencies(true, *symbols.mapNotNull { it.containingFile }.toTypedArray())

        try {
            codeGenerator.createNewFile(
                dependencies = dependencies,
                packageName = packageName,
                fileName = fileName
            ).use { stream ->
                writeContent(stream, packageName, symbols)
            }
        } catch (e: Exception) {
            logger.error("Failed to generate file: ${e.message}")
        }
    }

    private fun writeContent(stream: OutputStream, packageName: String, symbols: List<KSPropertyDeclaration>) {
        val builder = StringBuilder()
        builder.append("package $packageName\n\n")

        // Determine common type from annotated properties
        val types = symbols.mapNotNull { it.type.resolve().declaration.qualifiedName?.asString() }.distinct()
        val commonType = if (types.size == 1) types.first() else "Any"

        builder.append("val registeredDevices: List<$commonType> = listOf(\n")

        symbols.forEach { property ->
            val pkg = property.packageName.asString()
            val name = property.simpleName.asString()
            val qualifiedName = if (pkg.isNotEmpty()) "$pkg.$name" else name
            builder.append("    $qualifiedName,\n")
        }

        builder.append(")\n")

        stream.write(builder.toString().toByteArray())
    }
}

