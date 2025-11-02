package com.example.quickpractice.ui.theme.screen.exam.component.math

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickpractice.util.ConvertUtils

/**
 * Component to display mixed text and LaTeX math content using EquationElement
 * Supports multi-line text wrapping
 * 
 * Optimized: Uses regular Text Composable if no math expressions are found,
 * otherwise uses EquationElement with Canvas for rendering math
 */
@Composable
fun MixedMathText(
    text: String,
    fontSize: Dp = 16.dp,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier
) {
    // Check if text contains math expressions (LaTeX delimiters)
    // Look for $...$ or $$...$$ patterns with LaTeX commands
    val containsMath = run {
        var i = 0
        while (i < text.length) {
            if (text[i] == '$') {
                // Check for single $ or double $$
                val isDouble = i + 1 < text.length && text[i + 1] == '$'
                val startPos = if (isDouble) i + 2 else i + 1
                val endMarker = if (isDouble) "$$" else "$"
                val endPos = text.indexOf(endMarker, startPos)
                
                if (endPos != -1) {
                    // Found a math block, check if it contains LaTeX commands
                    val mathContent = text.substring(startPos, endPos)
                    if (mathContent.contains("\\frac") || 
                        mathContent.contains("\\sqrt") || 
                        mathContent.contains("\\") ||
                        mathContent.contains("^") ||
                        mathContent.contains("_")) {
                        return@run true
                    }
                    i = endPos + endMarker.length
                } else {
                    i++
                }
            } else {
                i++
            }
        }
        false
    }
    
    if (!containsMath) {
        // No math expressions - use regular Text Composable for better performance
        Text(
            text = text.replace("\\n", "\n"), // Handle escaped newlines
            fontSize = fontSize.value.sp,
            color = color,
            fontWeight = fontWeight,
            modifier = modifier
        )
        return
    }
    
    // Has math expressions - use EquationElement with Canvas
    val elements = EquationParser.parseMixedContent(text, fontSize, color, fontWeight)
    
    if (elements.isEmpty()) {
        return
    }
    
    BoxWithConstraints(modifier = modifier) {
        val maxWidth = ConvertUtils.DpToPx(maxWidth)
        val fontSizePx = ConvertUtils.DpToPx(fontSize)
        
        // Split elements into lines based on available width
        val lines = mutableListOf<List<EquationElement>>()
        val normalSpacingWidth = fontSizePx * 1f
        val normalSpacingElement = EquationElement.Text(
            " ",
            fontSizePx,
            horizontalPadding = normalSpacingWidth,
            textColor = color,
            isBold = fontWeight >= FontWeight.Bold
        )
        
        // Extra spacing for text-to-math and math-to-operator transitions
        val extraSpacingWidth = fontSizePx * 0.5f // Increased for better readability
        val extraSpacingElement = EquationElement.Text(
            " ",
            fontSizePx,
            horizontalPadding = extraSpacingWidth,
            textColor = color,
            isBold = fontWeight >= FontWeight.Bold
        )
        
        val currentLine = mutableListOf<EquationElement>()
        var currentLineWidth = 0f
        
        // Helper function to check if element is a math element (Fraction, Radical, Superscript, etc.)
        fun isMathElement(element: EquationElement): Boolean {
            return element !is EquationElement.Text || element.text == "\n"
        }
        
        // Helper function to check if element is an operator or contains operator
        fun isOperatorElement(element: EquationElement): Boolean {
            if (element is EquationElement.Text) {
                val text = element.text.trim()
                return text == "+" || text == "-" || text == "=" || text == "*" || text == "/" ||
                       text == " + " || text == " - " || text == " = " || text == " * " || text == " / " ||
                       text.startsWith("+") || text.startsWith("-") || text.startsWith("=") ||
                       text.endsWith("+") || text.endsWith("-") || text.endsWith("=")
            }
            return false
        }
        
        // Helper function to check if text element contains operators
        fun textContainsOperator(element: EquationElement): Boolean {
            if (element is EquationElement.Text) {
                val text = element.text
                return text.contains("+") || text.contains("-") || text.contains("=") || 
                       text.contains("*") || text.contains("/")
            }
            return false
        }
        
        for (i in elements.indices) {
            val element = elements[i]
            
            // Check if this is a newline marker
            if (element is EquationElement.Text && element.text == "\n") {
                // Force a new line
                if (currentLine.isNotEmpty()) {
                    lines.add(currentLine.toList())
                    currentLine.clear()
                    currentLineWidth = 0f
                }
                // Add an empty line for spacing (or skip if you want no extra space)
                continue
            }
            
            val elementSize = element.measure()
            
            // Determine spacing based on element types
            val spacing: EquationElement

            if (currentLine.isNotEmpty()) {
                val previousElement = currentLine.last()
                val isPreviousText = previousElement is EquationElement.Text && previousElement.text != "\n"
                val isPreviousMath = isMathElement(previousElement)
                val isCurrentMath = isMathElement(element)
                val isCurrentOperator = isOperatorElement(element)
                
                when {
                    // Text to Math - extra spacing
                    isPreviousText && isCurrentMath -> {
                        spacing = extraSpacingElement
                    }
                    // Math to Text (that contains operators like " + ") - extra spacing
                    isPreviousMath && element is EquationElement.Text && textContainsOperator(element) -> {
                        spacing = extraSpacingElement
                    }
                    // Math to pure operator - extra spacing
                    isPreviousMath && isCurrentOperator -> {
                        spacing = extraSpacingElement
                    }
                    // Operator to Math - extra spacing
                    isOperatorElement(previousElement) && isCurrentMath -> {
                        spacing = extraSpacingElement
                    }
                    // Default spacing
                    else -> {
                        spacing = normalSpacingElement
                    }
                }
            } else {
                spacing = normalSpacingElement
            }
            
            // Calculate total width if we add this element to current line
            // Use actual measured width of spacing element instead of just spacingWidth
            val spacingWidthToAdd = if (currentLine.isNotEmpty()) {
                spacing.measure().width
            } else {
                0f
            }
            val totalWidthIfAdded = currentLineWidth + spacingWidthToAdd + elementSize.width
            
            // Check if element itself exceeds maxWidth - need to break it down
            if (element is EquationElement.Text && element.text != "\n" && elementSize.width > maxWidth) {
                // Break long text element into words
                val textContent = element.text
                val words = textContent.split(" ")
                
                for (wordIndex in words.indices) {
                    val word = words[wordIndex]
                    if (word.isEmpty()) continue
                    
                    // Create word element without extra padding for normal text
                    val wordElement = EquationElement.Text(
                        word,
                        element.fontSize,
                        verticalPadding = 0f, // No vertical padding for normal text words
                        horizontalPadding = 0f, // No horizontal padding - just natural character width
                        element.textColor,
                        element.isBold
                    )
                    val wordSize = wordElement.measure()
                    
                    // For words within the same text element, use minimal spacing (just a space character)
                    // Don't add extra spacing elements - the space character is enough
                    val spaceChar = if (wordIndex > 0 || currentLine.isNotEmpty()) {
                        // Create a simple space element without extra padding
                        EquationElement.Text(
                            " ",
                            element.fontSize,
                            verticalPadding = 0f, // No vertical padding for space
                            horizontalPadding = 0f, // No horizontal padding for space - just natural character width
                            element.textColor,
                            element.isBold
                        )
                    } else {
                        null
                    }
                    
                    val spaceWidth = spaceChar?.measure()?.width ?: 0f
                    val wordTotalWidth = currentLineWidth + spaceWidth + wordSize.width
                    
                    // Check if we need to wrap
                    if (wordTotalWidth > maxWidth && currentLine.isNotEmpty()) {
                        // Wrap to new line
                        lines.add(currentLine.toList())
                        currentLine.clear()
                        currentLineWidth = 0f
                    }
                    
                    // Add space before word if needed (within same text element or between words)
                    if (spaceChar != null && (wordIndex > 0 || currentLine.isNotEmpty())) {
                        currentLine.add(spaceChar)
                        currentLineWidth += spaceWidth
                    }
                    
                    // Add word
                    currentLine.add(wordElement)
                    currentLineWidth += wordSize.width
                }
            } else {
                // Normal case - check if adding this element would exceed the width
                if (totalWidthIfAdded > maxWidth && currentLine.isNotEmpty()) {
                    // Start a new line - save current line and start fresh
                    lines.add(currentLine.toList())
                    currentLine.clear()
                    // Add element to new line (no spacing before first element)
                    currentLine.add(element)
                    currentLineWidth = elementSize.width
                } else {
                    // Add to current line
                    if (currentLine.isNotEmpty()) {
                        // Use actual measured width of spacing element
                        currentLineWidth += spacing.measure().width
                    }
                    currentLine.add(element)
                    currentLineWidth += elementSize.width
                }
            }
        }
        
        // Add the last line if not empty
        if (currentLine.isNotEmpty()) {
            lines.add(currentLine)
        }
        
        // Combine elements in each line with proper spacing
        val combinedLines = lines.map { lineElements ->
            if (lineElements.size == 1) {
                lineElements[0]
            } else {
                var result = lineElements[0]
                for (i in 1 until lineElements.size) {
                    val prevElement = lineElements[i - 1]
                    val currElement = lineElements[i]
                    
                    val isPrevText = prevElement is EquationElement.Text && prevElement.text != "\n"
                    val isPrevMath = isMathElement(prevElement)
                    val isCurrMath = isMathElement(currElement)
                    val isCurrOperator = isOperatorElement(currElement)
                    
                    val spacing = when {
                        // Text to Math - extra spacing
                        isPrevText && isCurrMath -> {
                            extraSpacingElement
                        }
                        // Math to Text (that contains operators) - extra spacing
                        isPrevMath && currElement is EquationElement.Text && textContainsOperator(currElement) -> {
                            extraSpacingElement
                        }
                        // Math to pure operator - extra spacing
                        isPrevMath && isCurrOperator -> {
                            extraSpacingElement
                        }
                        // Operator to Math - extra spacing
                        isOperatorElement(prevElement) && isCurrMath -> {
                            extraSpacingElement
                        }
                        // Default spacing
                        else -> {
                            normalSpacingElement
                        }
                    }
                    
                    result = result + spacing + currElement
                }
                result
            }
        }
        
        // Calculate total height and baseline positions
        val lineHeights = combinedLines.map { it.measure().height }
        val maxLineHeight = lineHeights.maxOrNull() ?: 0f
        val lineSpacing = fontSizePx * 0.3f // Spacing between lines
        val totalHeight = lineHeights.sum() + (lineSpacing * (lines.size - 1).coerceAtLeast(0))
        
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(ConvertUtils.PxToDp(totalHeight + ConvertUtils.DpToPx(16.dp))) // Add padding
        ) {
            val topPadding = 8.dp.toPx()
            var currentY = topPadding
            
            // Draw each line - align all lines to a common baseline
            for (i in combinedLines.indices) {
                val line = combinedLines[i]
                val lineHeight = lineHeights[i]
                
                // All lines start from the same x position (0)
                // Each line's top-left is positioned to ensure consistent vertical alignment
                // The line's draw method will handle internal element alignment
                line.draw(this, Offset(0f, currentY))
                
                // Move to next line position - add line height plus spacing
                currentY += lineHeight + lineSpacing
            }
        }
    }
}
