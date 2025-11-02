package com.example.quickpractice.ui.theme.screen.exam.component.math

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quickpractice.util.ConvertUtils

/**
 * Parses LaTeX string and converts it to EquationElement structure
 */
object EquationParser {
    
    /**
     * Parses a mixed content string (text + LaTeX) and returns a list of EquationElements
     * 
     * @param input The input string containing text and LaTeX (e.g., "text $\frac{1}{x}$ text")
     * @param fontSize Font size for text elements
     * @param textColor Color for text elements
     * @param fontWeight Font weight for text elements
     * @return List of EquationElements representing the parsed content
     */
    @androidx.compose.runtime.Composable
    fun parseMixedContent(
        input: String, 
        fontSize: Dp = 16.dp,
        textColor: Color = Color.Black,
        fontWeight: FontWeight = FontWeight.Normal
    ): List<EquationElement> {
        val elements = mutableListOf<EquationElement>()
        val fontSizePx = ConvertUtils.DpToPx(fontSize)
        val isBold = fontWeight >= FontWeight.Bold
        
        var i = 0
        val textBuilder = StringBuilder()
        
        while (i < input.length) {
            // Handle newline characters - create line break elements
            // Check for \n (escaped newline in string)
            if (input[i] == '\\' && i + 1 < input.length && input[i + 1] == 'n') {
                // Add any accumulated text before the newline
                if (textBuilder.isNotEmpty()) {
                    elements.add(
                        EquationElement.Text(
                            textBuilder.toString(),
                            fontSizePx,
                            textColor = textColor,
                            isBold = isBold
                        )
                    )
                    textBuilder.clear()
                }
                // Add a line break marker (we'll handle this in MixedMathText)
                elements.add(
                    EquationElement.Text(
                        "\n",
                        fontSizePx,
                        textColor = textColor,
                        isBold = isBold
                    )
                )
                i += 2 // Skip both \ and n
                continue
            }
            
            // Skip escaped backslashes that are part of LaTeX commands (like \sqrt)
            // We don't want to add the backslash to textBuilder when it's part of a command
            // But we also need to handle $ delimiters, so check for $ first
            
            // Check for inline math delimiter $...$
            if (input[i] == '$' && i + 1 < input.length) {
                // Check if it's double dollar $$...$$
                if (input[i + 1] == '$') {
                    // Double dollar - display math (currently treated same as single $)
                    val mathEnd = input.indexOf("$$", i + 2)
                    if (mathEnd != -1) {
                        // Add any accumulated text
                        if (textBuilder.isNotEmpty()) {
                            elements.add(
                                EquationElement.Text(
                                    textBuilder.toString(),
                                    fontSizePx,
                                    textColor = textColor,
                                    isBold = isBold
                                )
                            )
                            textBuilder.clear()
                        }
                        
                        // Parse math content
                        // Remove any escaped backslashes (\\ becomes \)
                        val rawMathContent = input.substring(i + 2, mathEnd)
                        // Unescape: replace \\ with \ (in actual string, \\ is already \)
                        val mathContent = rawMathContent.replace("\\\\", "\\")
                        elements.add(parseMathExpression(mathContent, fontSizePx, textColor, isBold))
                        
                        i = mathEnd + 2
                    } else {
                        textBuilder.append(input[i])
                        i++
                    }
                } else {
                    // Single dollar - inline math
                    val mathEnd = input.indexOf('$', i + 1)
                    if (mathEnd != -1) {
                        // Add any accumulated text
                        if (textBuilder.isNotEmpty()) {
                            elements.add(
                                EquationElement.Text(
                                    textBuilder.toString(),
                                    fontSizePx,
                                    textColor = textColor,
                                    isBold = isBold
                                )
                            )
                            textBuilder.clear()
                        }
                        
                        // Parse math content
                        // Remove any escaped backslashes (\\ becomes \)
                        val rawMathContent = input.substring(i + 1, mathEnd)
                        // Unescape: replace \\ with \ (in actual string, \\ is already \)
                        val mathContent = rawMathContent.replace("\\\\", "\\")
                        elements.add(parseMathExpression(mathContent, fontSizePx, textColor, isBold))
                        
                        i = mathEnd + 1
                    } else {
                        textBuilder.append(input[i])
                        i++
                    }
                }
            } else {
                textBuilder.append(input[i])
                i++
            }
        }
        
        // Add any remaining text
        if (textBuilder.isNotEmpty()) {
            elements.add(
                EquationElement.Text(
                    textBuilder.toString(),
                    fontSizePx,
                    textColor = textColor,
                    isBold = isBold
                )
            )
        }
        
        return elements
    }
    
    /**
     * Parses a LaTeX math expression and converts it to EquationElement
     */
    private fun parseMathExpression(
        expression: String, 
        fontSize: Float,
        textColor: Color = Color.Black,
        isBold: Boolean = false
    ): EquationElement {
        val result = expression.trim()
        
        // Check if the entire expression is a single fraction
        val singleFractionRegex = Regex("^\\\\frac\\{([^}]+)\\}\\{([^}]+)\\}$")
        val singleMatch = singleFractionRegex.find(result)
        if (singleMatch != null) {
            val numeratorStr = singleMatch.groupValues[1]
            val denominatorStr = singleMatch.groupValues[2]
            
            val numerator = parseSimpleExpression(numeratorStr, fontSize, textColor, isBold)
            val denominator = parseSimpleExpression(denominatorStr, fontSize, textColor, isBold)
            
            return EquationElement.Fraction(
                numerator = numerator,
                denominator = denominator,
                lineColor = textColor
            )
        }
        
        // Handle multiple fractions and operations
        return parseComplexExpression(result, fontSize, textColor, isBold)
    }
    
    /**
     * Parses a simple expression (without fractions)
     * Handles superscripts, radicals, and plain text recursively
     */
    private fun parseSimpleExpression(
        expression: String, 
        fontSize: Float,
        textColor: Color = Color.Black,
        isBold: Boolean = false
    ): EquationElement {
        val trimmed = expression.trim()
        
        if (trimmed.isEmpty()) {
            return EquationElement.Text("", fontSize, textColor = textColor, isBold = isBold)
        }
        
        // Parse expression by splitting into parts and handling each LaTeX command
        val parts = mutableListOf<Pair<String, String>>() // (content, type: "text", "sqrt", "sup", "simpleSup")
        
        var i = 0
        while (i < trimmed.length) {
            // Check for sqrt first
            if (i < trimmed.length - 5 && trimmed.startsWith("\\sqrt{", i)) {
                // Find matching closing brace
                var braceCount = 0
                var j = i + 6
                var sqrtEnd = -1
                while (j < trimmed.length) {
                    when (trimmed[j]) {
                        '{' -> braceCount++
                        '}' -> {
                            if (braceCount == 0) {
                                sqrtEnd = j
                                break
                            }
                            braceCount--
                        }
                    }
                    j++
                }
                
                if (sqrtEnd != -1) {
                    val before = trimmed.substring(0, i)
                    val sqrtContent = trimmed.substring(i + 6, sqrtEnd)
                    val after = trimmed.substring(sqrtEnd + 1)
                    
                    if (before.isNotEmpty()) {
                        parts.add(Pair(before, "text"))
                    }
                    parts.add(Pair(sqrtContent, "sqrt"))
                    if (after.isNotEmpty()) {
                        return combinePartsWithRemaining(parts, after, fontSize, textColor, isBold)
                    }
                    break
                }
            }
            
            // Check for superscript with braces: base^{power}
            if (trimmed[i] == '^' && i + 1 < trimmed.length && trimmed[i + 1] == '{') {
                // Find the base (everything before ^)
                val baseEnd = i
                val base = trimmed.substring(0, baseEnd)
                
                // Find matching closing brace for power
                var braceCount = 0
                var j = i + 2
                var powerEnd = -1
                while (j < trimmed.length) {
                    when (trimmed[j]) {
                        '{' -> braceCount++
                        '}' -> {
                            if (braceCount == 0) {
                                powerEnd = j
                                break
                            }
                            braceCount--
                        }
                    }
                    j++
                }
                
                if (powerEnd != -1) {
                    val powerContent = trimmed.substring(i + 2, powerEnd)
                    val after = trimmed.substring(powerEnd + 1)
                    
                    if (base.isNotEmpty()) {
                        parts.add(Pair(base, "text"))
                    }
                    parts.add(Pair(powerContent, "sup"))
                    if (after.isNotEmpty()) {
                        return combinePartsWithRemaining(parts, after, fontSize, textColor, isBold)
                    }
                    break
                }
            }
            
            // Check for simple superscript: base^digit (single char base)
            if (i > 0 && trimmed[i] == '^' &&
                i + 1 < trimmed.length && trimmed[i + 1].isDigit()) {
                val base = trimmed.substring(i - 1, i)
                if (base.matches(Regex("[a-zA-Z]"))) {
                    val power = trimmed.substring(i + 1, i + 2)
                    val before = trimmed.substring(0, i - 1)
                    val after = trimmed.substring(i + 2)
                    
                    if (before.isNotEmpty()) {
                        parts.add(Pair(before, "text"))
                    }
                    parts.add(Pair(base, "text"))
                    parts.add(Pair(power, "simpleSup"))
                    if (after.isNotEmpty()) {
                        return combinePartsWithRemaining(parts, after, fontSize, textColor, isBold)
                    }
                    break
                }
            }
            
            i++
        }
        
        // If no LaTeX commands found, return as text
        if (parts.isEmpty() && !trimmed.contains('\\')) {
            return EquationElement.Text(trimmed, fontSize, textColor = textColor, isBold = isBold)
        }
        
        // Parse all parts found
        return parseParts(parts, fontSize, textColor, isBold)
    }
    
    /**
     * Combines parsed parts with remaining string
     */
    private fun combinePartsWithRemaining(
        parts: MutableList<Pair<String, String>>, 
        remaining: String, 
        fontSize: Float,
        textColor: Color = Color.Black,
        isBold: Boolean = false
    ): EquationElement {
        val parsedParts = parseParts(parts, fontSize, textColor, isBold)
        val remainingParsed = parseSimpleExpression(remaining, fontSize, textColor, isBold)
        return combineHorizontally(listOf(parsedParts, remainingParsed), fontSize)
    }
    
    /**
     * Parses a list of parts into EquationElements
     */
    private fun parseParts(
        parts: List<Pair<String, String>>, 
        fontSize: Float,
        textColor: Color = Color.Black,
        isBold: Boolean = false
    ): EquationElement {
        if (parts.isEmpty()) {
            return EquationElement.Text("", fontSize, textColor = textColor, isBold = isBold)
        }
        
        val elements = mutableListOf<EquationElement>()
        var i = 0
        
        while (i < parts.size) {
            val (content, type) = parts[i]
            
            when (type) {
                "text" -> {
                    if (content.isNotEmpty()) {
                        elements.add(
                            EquationElement.Text(
                                content,
                                fontSize,
                                textColor = textColor,
                                isBold = isBold
                            )
                        )
                    }
                    i++
                }
                "sqrt" -> {
                    val radicand = parseSimpleExpression(content, fontSize, textColor, isBold)
                    elements.add(EquationElement.Radical(radicand = radicand, lineColor = textColor))
                    i++
                }
                "sup" -> {
                    // Power part - need to combine with previous base
                    if (i > 0 && elements.isNotEmpty()) {
                        val base = elements.removeAt(elements.size - 1)
                        val power = parseSimpleExpression(content, fontSize * 0.7f, textColor, isBold)
                        elements.add(EquationElement.Superscript(base = base, power = power))
                    } else {
                        // No base found, treat as text
                        elements.add(
                            EquationElement.Text(
                                "^$content",
                                fontSize,
                                textColor = textColor,
                                isBold = isBold
                            )
                        )
                    }
                    i++
                }
                "simpleSup" -> {
                    // Simple superscript - combine with previous base
                    if (i > 0 && elements.isNotEmpty()) {
                        val base = elements.removeAt(elements.size - 1)
                        val power = EquationElement.Text(
                            content,
                            fontSize * 0.7f,
                            textColor = textColor,
                            isBold = isBold
                        )
                        elements.add(
                            EquationElement.Superscript(
                                base = base,
                                power = power,
                            )
                        )
                    } else {
                        elements.add(
                            EquationElement.Text(
                                "^$content",
                                fontSize,
                                textColor = textColor,
                                isBold = isBold
                            )
                        )
                    }
                    i++
                }
                else -> {
                    elements.add(
                        EquationElement.Text(
                            content,
                            fontSize,
                            textColor = textColor,
                            isBold = isBold
                        )
                    )
                    i++
                }
            }
        }
        
        return if (elements.size == 1) elements[0] else combineHorizontally(elements, fontSize)
    }
    
    /**
     * Finds fraction with proper brace matching
     */
    private fun findFraction(expression: String, startPos: Int): Pair<IntRange, Pair<String, String>>? {
        if (startPos >= expression.length) return null
        
        // Find \frac{ (note: in actual string, escaped \\ becomes single \)
        val fracStart = expression.indexOf("\\frac{", startPos)
        if (fracStart == -1) return null
        
        var i = fracStart + 6 // After "\frac{"
        var braceCount = 0
        val numeratorStart = i
        var numeratorEnd = -1
        
        // Find numerator end
        while (i < expression.length) {
            when (expression[i]) {
                '{' -> braceCount++
                '}' -> {
                    if (braceCount == 0) {
                        numeratorEnd = i
                        break
                    }
                    braceCount--
                }
            }
            i++
        }
        
        if (numeratorEnd == -1) return null
        
        // Check for denominator start
        if (numeratorEnd + 1 >= expression.length || expression[numeratorEnd + 1] != '{') {
            return null
        }
        
        i = numeratorEnd + 2 // After "}{"
        braceCount = 0
        val denominatorStart = i
        var denominatorEnd = -1
        
        // Find denominator end
        while (i < expression.length) {
            when (expression[i]) {
                '{' -> braceCount++
                '}' -> {
                    if (braceCount == 0) {
                        denominatorEnd = i
                        break
                    }
                    braceCount--
                }
            }
            i++
        }
        
        if (denominatorEnd == -1) return null
        
        val numerator = expression.substring(numeratorStart, numeratorEnd)
        val denominator = expression.substring(denominatorStart, denominatorEnd)
        
        // Range should cover from fracStart to denominatorEnd (inclusive)
        // denominatorEnd is the index of the closing brace '}', so we include it
        val range = IntRange(fracStart, denominatorEnd)
        
        // Double-check: verify the substring matches what we expect
        val expectedFraction = "\\frac{$numerator}{$denominator}"
        val actualSubstring = expression.substring(fracStart, denominatorEnd + 1)
        
        // If they don't match exactly, there might be extra characters
        if (!actualSubstring.equals(expectedFraction, ignoreCase = false)) {
            // Something is wrong - the range might not be correct
            // But continue anyway with what we extracted
        }
        
        return Pair(range, Pair(numerator, denominator))
    }
    
    /**
     * Parses a complex expression that may contain multiple operations
     */
    private fun parseComplexExpression(
        expression: String, 
        fontSize: Float,
        textColor: Color = Color.Black,
        isBold: Boolean = false
    ): EquationElement {
        val operatorRegex = Regex("\\s*([+\\-=])\\s*")
        
        val parts = mutableListOf<Pair<String, Boolean>>() // (content, isOperator)
        
        // Find fractions using proper brace matching
        var searchPos = 0
        val fractionMatches = mutableListOf<Pair<IntRange, Pair<String, String>>>()
        while (searchPos < expression.length) {
            val fractionMatch = findFraction(expression, searchPos)
            if (fractionMatch != null) {
                val (range, _) = fractionMatch
                // Verify the range is correct by checking what it covers
                val fractionStr = expression.substring(range.first, range.last + 1)
                // Make sure it properly ends with a closing brace
                if (fractionStr.endsWith("}")) {
                    fractionMatches.add(fractionMatch)
                    // Move search position to after this fraction
                    searchPos = range.last + 1
                } else {
                    // Range seems incorrect, skip this position and continue
                    searchPos = range.first + 1
                }
            } else {
                break
            }
        }
        
        // Find operators - but exclude operators that are inside fractions
        val operatorMatches = mutableListOf<Pair<IntRange, String>>()
        operatorRegex.findAll(expression).forEach { match ->
            // Check if this operator is inside any fraction range
            val opRange = match.range
            val isInsideFraction = fractionMatches.any { (fracRange, _) ->
                opRange.first >= fracRange.first && opRange.last <= fracRange.last
            }
            if (!isInsideFraction) {
                operatorMatches.add(Pair(opRange, match.groupValues[1]))
            }
        }
        
        // Combine and sort all matches
        val allMatchesList = mutableListOf<Triple<Int, IntRange, Any>>()
        fractionMatches.forEach { (range, data) ->
            allMatchesList.add(Triple(0, range, data))
        }
        operatorMatches.forEach { (range, op) ->
            allMatchesList.add(Triple(1, range, op))
        }
        allMatchesList.sortBy { it.second.first }
        
        var currentPos = 0
        for ((type, range, data) in allMatchesList) {
            // Add text before this match (only if there's a gap)
            if (range.first > currentPos) {
                val textBefore = expression.substring(currentPos, range.first)
                // Only add non-empty, non-whitespace text
                // Filter out any text that looks like it's part of a fraction (e.g., "-2}")
                val trimmed = textBefore.trim()
                if (trimmed.isNotEmpty() && !trimmed.matches(Regex("^[-0-9]+\\}$"))) {
                    parts.add(Pair(trimmed, false))
                }
            }
            
            // Add the matched content
            if (type == 0) {
                // Fraction - use the extracted numerator and denominator
                val fractionData = data as Pair<*, *>
                // Verify the range is correct by checking the actual string
                val actualFractionStr = expression.substring(range.first, range.last + 1)
                // Ensure it starts with \frac{ and ends with }
                if (!actualFractionStr.startsWith("\\frac{") || !actualFractionStr.endsWith("}")) {
                    // Range might be wrong, try to find the correct end
                    // But for now, just use the data we extracted
                }
                // Store both the full string and the extracted parts for later use
                parts.add(Pair("FRACTION:${fractionData.first}:${fractionData.second}", false))
            } else {
                // Operator
                val operator = data as String
                parts.add(Pair(operator, true))
            }
            
            // Update currentPos to after this match (range.last is inclusive, so +1 to move past it)
            currentPos = range.last + 1
        }
        
        // Add remaining text (but filter out any leftover fraction parts)
        if (currentPos < expression.length) {
            val remaining = expression.substring(currentPos).trim()
            if (remaining.isNotEmpty() && !remaining.matches(Regex("^[-0-9]+\\}$"))) {
                parts.add(Pair(remaining, false))
            }
        }
        
        // Build horizontal group of elements
        val elements = mutableListOf<EquationElement>()
        for ((content, isOperator) in parts) {
            if (isOperator) {
                // Add more spacing around operators for better readability
                elements.add(
                    EquationElement.Text(
                        "  $content  ",
                        fontSize,
                        textColor = textColor,
                        isBold = isBold
                    )
                )
            } else {
                // Check if it's a pre-parsed fraction
                if (content.startsWith("FRACTION:")) {
                    val parts = content.substring(9).split(":", limit = 2)
                    if (parts.size == 2) {
                        val numeratorStr = parts[0]
                        val denominatorStr = parts[1]
                        
                        val numerator = parseSimpleExpression(numeratorStr, fontSize, textColor, isBold)
                        val denominator = parseSimpleExpression(denominatorStr, fontSize, textColor, isBold)
                        val fraction = EquationElement.Fraction(
                            numerator = numerator,
                            denominator = denominator,
                            lineColor = textColor
                        )
                        elements.add(fraction)
                    } else {
                        elements.add(parseSimpleExpression(content, fontSize, textColor, isBold))
                    }
                } else {
                    // Not a fraction, parse as simple expression
                    elements.add(parseSimpleExpression(content, fontSize, textColor, isBold))
                }
            }
        }
        
        // Combine elements horizontally with spacing
        return combineHorizontally(elements, fontSize)
    }
    
    /**
     * Combines multiple EquationElements horizontally with spacing
     */
    private fun combineHorizontally(elements: List<EquationElement>, fontSize: Float = 16f): EquationElement {
        if (elements.isEmpty()) {
            return EquationElement.Text("", fontSize)
        }
        
        if (elements.size == 1) {
            return elements[0]
        }
        
        // Add spacing between elements (increase for better readability)
        val spacingWidth = fontSize * 0.5f // Spacing proportional to font size
        val spacingElement = EquationElement.Text(" ", fontSize, horizontalPadding = spacingWidth)
        
        var result = elements[0]
        for (i in 1 until elements.size) {
            result = result + spacingElement + elements[i]
        }
        return result
    }
}

