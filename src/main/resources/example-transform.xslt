<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <!-- Zdefiniowanie parametru -->
    <xsl:param name="infoParam" select="'additional information'" />

    <!-- Reguła główna dla transformacji -->
    <xsl:template match="/">
        <xsl:copy>
            <xsl:apply-templates select="*"/>
        </xsl:copy>
    </xsl:template>

    <!-- Reguła dla kopiowania elementów -->
    <xsl:template match="*">
        <xsl:copy>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>

    <!-- Reguła dla tekstu -->
    <xsl:template match="text()">
        <xsl:if test="normalize-space(.) != ''">
            <xsl:value-of select="normalize-space(.)"/>
        </xsl:if>
    </xsl:template>

    <!-- Reguła dla elementu 'person' -->
    <xsl:template match="person">
        <xsl:copy>
            <!-- Skopiowanie istniejących elementów -->
            <xsl:apply-templates select="@*|node()"/>
            <!-- Dodanie nowego elementu 'info' -->
            <info><xsl:value-of select="normalize-space($infoParam)"/></info>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
