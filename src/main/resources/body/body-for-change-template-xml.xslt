<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>
    <xsl:param name="phoneParam" select="'665 554 323'" />
    <xsl:template match="/">
        <xsl:copy>
            <xsl:apply-templates select="*"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="*">
        <xsl:copy>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="text()">
        <xsl:if test="normalize-space(.) != ''">
            <xsl:value-of select="normalize-space(.)"/>
        </xsl:if>
    </xsl:template>
    <xsl:template match="person">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
            <phone><xsl:value-of select="normalize-space($phoneParam)"/></phone>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>