<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <!-- Zdefiniowanie parametru -->
    <xsl:param name="paramName" select="'wartośćParametru'" />

    <!-- Reguła główna dla transformacji -->
    <xsl:template match="/">
        <xsl:copy>
            <xsl:apply-templates select="*"/>
        </xsl:copy>
    </xsl:template>

    <!-- Reguła dla elementu 'person' -->
    <xsl:template match="person">
        <xsl:copy>
            <!-- Skopiowanie istniejących elementów -->
            <xsl:apply-templates select="@*|node()"/>
            <!-- Dodanie nowego elementu 'info' -->
            <info><xsl:value-of select="$paramName"/></info>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
