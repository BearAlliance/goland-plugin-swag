package com.github.bearalliance.swag

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.PlainSyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class SwagColorSettingsPage : ColorSettingsPage {

    override fun getDisplayName(): String = "Swag"

    override fun getIcon(): Icon? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getHighlighter(): SyntaxHighlighter = PlainSyntaxHighlighter()

    override fun getDemoText(): String = DEMO_TEXT

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey> =
        TAG_DESCRIPTOR_MAP

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Annotation tag", SwagTextAttributes.ANNOTATION),
            AttributesDescriptor("Parameter name", SwagTextAttributes.PARAMETER_NAME),
            AttributesDescriptor("HTTP method", SwagTextAttributes.HTTP_METHOD),
            AttributesDescriptor("Status code", SwagTextAttributes.STATUS_CODE),
        )

        private val TAG_DESCRIPTOR_MAP = mapOf(
            "annotation" to SwagTextAttributes.ANNOTATION,
            "param" to SwagTextAttributes.PARAMETER_NAME,
            "method" to SwagTextAttributes.HTTP_METHOD,
            "status" to SwagTextAttributes.STATUS_CODE,
        )

        private const val DEMO_TEXT = """// <annotation>@Summary</annotation> Show an account
// <annotation>@Description</annotation> Get account by ID from the store
// <annotation>@Tags</annotation> accounts
// <annotation>@Accept</annotation> json
// <annotation>@Produce</annotation> json
// <annotation>@Param</annotation> <param>id</param> path int true "Account ID"
// <annotation>@Success</annotation> <status>200</status> {object} model.Account
// <annotation>@Failure</annotation> <status>400</status> {object} httputil.HTTPError
// <annotation>@Router</annotation> /accounts/{id} [<method>get</method>]
// <annotation>@Security</annotation> ApiKeyAuth"""
    }
}
