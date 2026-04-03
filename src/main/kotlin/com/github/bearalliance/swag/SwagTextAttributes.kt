package com.github.bearalliance.swag

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey

object SwagTextAttributes {

    @JvmField
    val ANNOTATION = TextAttributesKey.createTextAttributesKey(
        "SWAG_ANNOTATION",
        DefaultLanguageHighlighterColors.METADATA,
    )

    @JvmField
    val PARAMETER_NAME = TextAttributesKey.createTextAttributesKey(
        "SWAG_PARAMETER_NAME",
        DefaultLanguageHighlighterColors.INSTANCE_FIELD,
    )

    @JvmField
    val HTTP_METHOD = TextAttributesKey.createTextAttributesKey(
        "SWAG_HTTP_METHOD",
        DefaultLanguageHighlighterColors.KEYWORD,
    )

    @JvmField
    val STATUS_CODE = TextAttributesKey.createTextAttributesKey(
        "SWAG_STATUS_CODE",
        DefaultLanguageHighlighterColors.NUMBER,
    )
}
