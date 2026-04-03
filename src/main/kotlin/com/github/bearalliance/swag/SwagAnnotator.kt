package com.github.bearalliance.swag

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement

class SwagAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is PsiComment) return

        val file = element.containingFile?.virtualFile ?: return
        if (!file.name.endsWith(".go")) return

        val text = element.text
        val trimmed = text.trimStart()
        if (!trimmed.startsWith("//")) return

        val slashEnd = text.indexOf("//") + 2
        val afterSlash = text.substring(slashEnd)
        val stripped = afterSlash.trimStart()
        if (!stripped.startsWith("@")) return

        val atOffset = text.length - afterSlash.length + (afterSlash.length - stripped.length)
        val afterAt = stripped.substring(1)

        val tagMatch = TAG_PATTERN.find(afterAt) ?: return
        val tag = tagMatch.groupValues[0]
        if (tag.isEmpty()) return

        val baseOffset = element.textRange.startOffset

        // Highlight @Tag
        annotateRange(holder, baseOffset + atOffset, tag.length + 1, SwagTextAttributes.ANNOTATION)

        // Highlight additional syntax based on the tag type
        val valueStart = atOffset + 1 + tag.length
        val valuePart = text.substring(valueStart)

        when (tag.lowercase()) {
            "router", "deprecatedrouter" -> highlightRouter(holder, baseOffset, valueStart, valuePart)
            "success", "failure", "response" -> highlightStatusCode(holder, baseOffset, valueStart, valuePart)
            "param" -> highlightParamName(holder, baseOffset, valueStart, valuePart)
        }
    }

    private fun highlightRouter(holder: AnnotationHolder, baseOffset: Int, valueStart: Int, value: String) {
        val methodMatch = ROUTER_METHOD_PATTERN.find(value) ?: return
        val methodGroup = methodMatch.groups[1] ?: return
        annotateRange(
            holder,
            baseOffset + valueStart + methodGroup.range.first,
            methodGroup.value.length,
            SwagTextAttributes.HTTP_METHOD,
        )
    }

    private fun highlightStatusCode(holder: AnnotationHolder, baseOffset: Int, valueStart: Int, value: String) {
        val codeMatch = STATUS_CODE_PATTERN.find(value) ?: return
        val codeGroup = codeMatch.groups[1] ?: return
        annotateRange(
            holder,
            baseOffset + valueStart + codeGroup.range.first,
            codeGroup.value.length,
            SwagTextAttributes.STATUS_CODE,
        )
    }

    private fun highlightParamName(holder: AnnotationHolder, baseOffset: Int, valueStart: Int, value: String) {
        val nameMatch = PARAM_NAME_PATTERN.find(value) ?: return
        val nameGroup = nameMatch.groups[1] ?: return
        annotateRange(
            holder,
            baseOffset + valueStart + nameGroup.range.first,
            nameGroup.value.length,
            SwagTextAttributes.PARAMETER_NAME,
        )
    }

    private fun annotateRange(
        holder: AnnotationHolder,
        offset: Int,
        length: Int,
        key: TextAttributesKey,
    ) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(TextRange(offset, offset + length))
            .textAttributes(key)
            .create()
    }

    companion object {
        private val TAG_PATTERN = Regex("""^(\w+(?:[.\-]\w+)*)""")
        private val ROUTER_METHOD_PATTERN = Regex("""\[(\w+)]""")
        private val STATUS_CODE_PATTERN = Regex("""\s+(\d{3}|default)\b""")
        private val PARAM_NAME_PATTERN = Regex("""\s+(\S+)""")
    }
}
