<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/digitalni-sertifikat"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
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
                    border-spacing: 10px;
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

                .info {
                    padding-left: 5px;
                }

                .republic {
                    font-size: 18px;
                    text-align: center;
                }

                .title {
                    font-size: 20px;
                    text-align: center;
                }

            </style>
        </head>
        <body>
            <div class="header">
                <div class="wrapper">
                    <div style="grid-column: 1; text-align: center;">
                        <img style="margin: -10px;" width="200px" src="https://kampovi.mis.org.rs/wp-content/uploads/2015/06/grb.png"/>
                        <p class="republic">
                            <strong>РЕПУБЛИКА СРБИЈА</strong><br/>
                            <strong>REPUBLIC OF SERBIA</strong>
                        </p>
                    </div>
                    <div style="grid-column: 2;">
                        <p class="title">
                            <strong>ДИГИТАЛНИ ЗЕЛЕНИ СЕРТИФИКАТ</strong><br/>
                            Потврда о извршеној вакцинацији против COVID-19 и резултатима тестирања
                        </p>
                        <p class="title">
                            <strong>DIGITAL GREEN CERTIFICATE</strong><br/>
                            Certificate of vaccination against COVID-19 and test results
                        </p>
                    </div>
                    <div style="grid-column: 3;">
                        <img>
                            <xsl:attribute name="src">
                                https://api.qrserver.com/v1/create-qr-code/?size400=x150&amp;data=<xsl:value-of select="x:validacija"/>
                            </xsl:attribute>
                        </img>
                    </div>
                </div>
            </div>
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
            <div class="footer wrapper">
                <div style="grid-row: 2; grid-column: 1;">
                    <img style="margin: -10px; float: left;" width="200px" src="https://www.energetskiportal.rs/wp-content/uploads/2018/02/batutlogo.png"/>
                    <p style="float: left;">
                        <strong>Сертификат издаје:</strong><br/>
                        Институт за јавно здравље Србије<br/>
                        "Др Милан Јовановић Батут"<br/>
                        <strong>Certificate issued by:</strong><br/>
                        Institute of Public Health of Serbia<br/>
                        "Dr Milan Jovanović Batut"
                    </p>
                </div>
                <div style="grid-row: 1; grid-column: 2;">
                    <p style="float: right; padding-right: 20%">
                        <strong>Дигитални потпис / Digitaly signed by:</strong>
                    </p>
                </div>
                <div style="grid-row: 2; grid-column: 2;">
                    <p style="float: right;">
                        РЕПУБЛИКА СРБИЈА<br/>
                        Влада Републике Србије<br/>
                        Канцеларија за информационе<br/>
                        технологије и електронску управу<br/>
                        Немањина 11, Београд<br/>
                        Датум: <xsl:value-of select="@datum-izdavanja"/>
                    </p>
                    <img style="margin: -20px; float: right;" width="200px" src="https://kampovi.mis.org.rs/wp-content/uploads/2015/06/grb.png"/>
                </div>
            </div>
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
                    <xsl:value-of select="x:datum-rodjenja"/>
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
                    <strong>Број пасоша / Passport No.</strong>
                </td>
                <td>
                    <xsl:value-of select="x:br-pasosa"/>
                </td>
            </tr>
        </table>
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
        <p class="info">
            <xsl:value-of select="x:tip"/>
        </p>
        <b>Произвођач и серија / Manufacturer and batch number:</b>
        <p class="info">
            <xsl:value-of select="x:proizvodjac"/>, <xsl:value-of select="x:serija"/>
        </p>
        <p>
            <b>Датум / Date:</b>     
            <span class="info">
                <xsl:value-of select="x:datum"/>
            </span>
        </p>
        <b>Здравствена установа / Health care institution:</b>
        <p>
            <xsl:value-of select="x:zdravstvena-ustanova"/>
        </p>
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
            <p class="info">
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
            <p class="info">
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
            <p class="info">
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
            <p class="info">
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
        <div class="grid-cell" style="grid-row: 3; background-color: #e6e6e6;">
            <b>Резултат / Result:</b>
            <p class="info">
                <xsl:choose>
                    <xsl:when test="not(x:rezultat/@xsi:nil='true')">
                        <xsl:choose>
                            <xsl:when test="x:rezultat = 1">
                                <xsl:text>Позитиван</xsl:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text>Негативан</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        N/A
                    </xsl:otherwise>
                </xsl:choose>
            </p>
            <b>Лабораторија / Laboratory:</b>
            <p class="info">
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
