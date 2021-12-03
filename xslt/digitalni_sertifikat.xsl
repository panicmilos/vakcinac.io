<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/xsd/digitalni-sertifikat"
  xmlns:os="https://www.vakcinac-io.rs/xsd/osnovna-sema"
>

<xsl:template match="/x:digitalni-sertifikat">
    <html>
        <head>
            <style>
                .nosilac-info {
                    width: 32vh;
                }
            </style>
        </head>
        <body>
            <table>
                <tr>
                    <td>
                        <strong>Број сертификата /<br/> Certificate ID:</strong>
                    </td>
                    <td class="nosilac-info">
                        <xsl:value-of select="@broj-sertifikata"/>
                    </td>
                    <td>
                        <strong>Датум и време издавања сертификата /<br/> Certificate issuing date and time:</strong>
                    </td>
                    <td>
                        <xsl:value-of select="@datum-izdavanja"/>
                    </td>
                </tr>
            </table>
            <xsl:apply-templates match="x:nosilac-sertifikata"/>
        </body>
    </html>
</xsl:template>

<xsl:template match="x:nosilac-sertifikata">
    <div>
        <table>
            <tr>
                <td class="nosilac-info">
                    <strong>Име и презиме / Name and surname:</strong>
                </td>
                <td>
                    <xsl:value-of select="os:ime"/>&#160;<xsl:value-of select="os:prezime"/>
                </td>
            </tr>
            <tr>
                <td class="nosilac-info">
                    <strong>Пол / Gender:</strong>
                </td>
                <td>
                    <xsl:choose>
                        <xsl:when test="os:pol = 0">
                            Женско/Female
                        </xsl:when>
                        <xsl:otherwise>
                            Мушко/Male
                        </xsl:otherwise>
                    </xsl:choose>
                </td>
            </tr>
            <tr>
                <td class="nosilac-info">
                    <strong>Датум рођења / Date of birth:</strong>
                </td>
                <td>
                    <xsl:value-of select="x:datum-rodjenja/os:dan"/>.
                    <xsl:value-of select="x:datum-rodjenja/os:mesec"/>.
                    <xsl:value-of select="x:datum-rodjenja/os:godina"/>
                </td>
            </tr>
            <tr>
                <td class="nosilac-info">
                    <strong>ЈМБГ / Personal No. / EBS:</strong>
                </td>
                <td>
                    <xsl:value-of select="os:jmbg"/>
                </td>
            </tr>
            <tr>
                <td class="nosilac-info">
                    <strong>Број пасоша / Passport No.:</strong>
                </td>
                <td>
                    <xsl:value-of select="x:br-pasosa"/>
                </td>
            </tr>
        </table>
    </div> 
</xsl:template>

<xsl:template match="x:validacija">
    <div>
        
    </div> 
</xsl:template>

<xsl:template match="x:vakcinacije">
    <div>
        
    </div> 
</xsl:template>

<xsl:template match="x:testovi">
    <div>
        
    </div> 
</xsl:template>


</xsl:stylesheet>
