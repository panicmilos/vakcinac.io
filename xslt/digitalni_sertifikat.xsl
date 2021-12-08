<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/xsd/digitalni-sertifikat"
  xmlns:os="https://www.vakcinac-io.rs/xsd/osnovna-sema"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>

<xsl:template match="/x:digitalni-sertifikat">
    <html>
        <head>
            <style>
                body {
                    margin: 10%;
                }

                .wrapper {
                    display: grid;
                }

                .nosilac-info {
                    width: 30%;
                }

                table {
                    width: 100%;
                }

                .grid-cell {
                    padding: 5px;
                }

                .nosilac-cell {
                    border-bottom: 1px solid black;
                }

                .vaccination-header {
                    text-align: center;
                    margin: 1px;
                }

                .border-cell {
                    border-bottom: 1px solid black;
                    border-right: 1px solid black;
                }

                #vakcinacije div:last-child {
                    border-right: none;
                    border-bottom: 1px solid black;
                }

                #testovi > div:nth-last-child(-n+3) {
                    border-right: none;
                    border-bottom: 1px solid black;
                }

                #testovi > div:last-child {
                    border-right: none;
                    border-bottom: none;
                }

            </style>
        </head>
        <body>
            <div class="wrapper">
                <div style="grid-row: 1">
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
                </div>
                <xsl:apply-templates select="x:nosilac-sertifikata"/>
            </div>
            <xsl:apply-templates select="x:vakcinacije"/>
            <xsl:apply-templates select="x:testovi"/>
        </body>
    </html>
</xsl:template>

<xsl:template match="x:nosilac-sertifikata">
    <div class="nosilac-cell" style="grid-row: 2;">
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
    <div id="vakcinacije" class="wrapper">
        <div class="grid-cell" style="grid-row: 1; grid-column: 1 / 3; ">
            <h3 class="vaccination-header">Вакцинација / Vaccination</h3>
        </div>
            <xsl:apply-templates select="x:vakcinacija"/>
    </div>
</xsl:template>

<xsl:template match="x:vakcinacija">
    <div class="border-cell grid-cell" style="grid-row: 2;">
        <div>
            <b>Доза / Dose: <xsl:value-of select="@doza"/> / <xsl:value-of select="../@broj-doza"/></b><br/>
            <b>Тип / Type:</b>
        </div>
        <p>
            <xsl:value-of select="x:tip"/>
        </p>
        <b>Произвођач и серија / Manufacturer and batch number:</b>
        <p>
            <xsl:value-of select="x:proizvodjac"/>, <xsl:value-of select="x:serija"/>
        </p>
        <b>Датум / Date:</b> <xsl:value-of select="x:datum"/> <br/>
        <b>Здравствена установа / Health care institurion:</b> <br/>
        <xsl:value-of select="x:zdravstvena-ustanova"/>
    </div>
</xsl:template>

<xsl:template match="x:testovi">
    <div id="testovi" class="wrapper">
        <xsl:apply-templates select="x:test"/>
    </div>
</xsl:template>

<xsl:template match="x:test">
        <div class="border-cell grid-cell" style="grid-row: 1;">
            <h3 style="text-align: center;"><xsl:value-of select="x:ime"/></h3>
        </div>
        <div class="border-cell grid-cell" style="grid-row: 2;">
            <b>Врста узорка / Sample type:</b>
            <p>
                <xsl:choose>
                    <xsl:when test="not(x:vrsta-uzorka/@xsi:nil='true')">
                        <xsl:value-of select="x:vrsta-uzorka"/>
                    </xsl:when>
                    <xsl:otherwise>
                        N/A
                    </xsl:otherwise>
                </xsl:choose>
            </p>
            <b>Произвођач теста / Test manufacturer:</b>
            <p>
                <xsl:choose>
                    <xsl:when test="not(x:proizvodjac/@xsi:nil='true')">
                        <xsl:value-of select="x:proizvodjac"/>
                    </xsl:when>
                    <xsl:otherwise>
                        N/A
                    </xsl:otherwise>
                </xsl:choose>
            </p>
            <b>Датум и време узорковања / Date and time of sampling:</b>
            <p>
                <xsl:choose>
                    <xsl:when test="not(x:datum-uzorka/@xsi:nil='true')">
                        <xsl:value-of select="x:datum-uzorka"/>
                    </xsl:when>
                    <xsl:otherwise>
                        N/A
                    </xsl:otherwise>
                </xsl:choose>
            </p>
            <b>Датум и време издавања резултата / Date and time of result:</b>
            <p>
                <xsl:choose>
                    <xsl:when test="not(x:datum-izdavanja/@xsi:nil='true')">
                        <xsl:value-of select="x:datum-izdavanja"/>
                    </xsl:when>
                    <xsl:otherwise>
                        N/A
                    </xsl:otherwise>
                </xsl:choose>
            </p>
            
        </div>
        <div class="grid-cell" style="grid-row: 3;">
            <b>Резултат / Result:</b>
            <p>
                <xsl:choose>
                    <xsl:when test="not(x:rezultat/@xsi:nil='true')">
                        <xsl:value-of select="x:rezultat"/>
                    </xsl:when>
                    <xsl:otherwise>
                        N/A
                    </xsl:otherwise>
                </xsl:choose>
            </p>
            <b>Лабораторија / Labratory:</b>
            <p>
                <xsl:choose>
                    <xsl:when test="not(x:labaratorija/@xsi:nil='true')">
                        <xsl:value-of select="x:labaratorija"/>
                    </xsl:when>
                    <xsl:otherwise>
                        N/A
                    </xsl:otherwise>
                </xsl:choose>
            </p>
        </div>
</xsl:template>


</xsl:stylesheet>
