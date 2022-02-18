<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/potvrda"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
>

<xsl:template match="/x:potvrda-o-izvrsenoj-vakcinaciji">
    <html>
        <head>
            <meta charset="UTF-8" />

            <style>
                body {
                    margin: 6% 3% 15% 5%;
                }

                .flex-container {
                    display: flex;
                }

                .institute-logo {
                    margin-left: 7%;
                    width: 140px;
                }

                .institute-intro {
                    text-align: center;
                    font-size: 20px;
                    font-weight: bold;
                }

                .institute-name {
                    text-align: center;
                    padding-top: 0.5%;
                    font-size: 18px;
                    font-weight: bold;
                }

                .info {
                    font-size: 17px;
                    margin-top: 3%;
                }

                .info-language {
                    font-size: 17px;
                    margin-top: 0.3%;
                }

                .weak {
                    color: #C0C0C0;
                }

            </style>

        </head>
        <body>

            <header>
                <div class="flex-container">
                    <div style="float: left; flex-grow: 1;">
                        <img class="institute-logo" src="https://i.ibb.co/KW0sDXY/institute-logo.jpg" />
                    </div>
                    <div style="margin-right: 7%;">
                        <div class="institute-intro">
                            ИНСТИТУТ ЗА ЈАВНО ЗДРАВЉЕ СРБИЈЕ
                        </div>
                        <div class="institute-name">
                            „Др Милан Јовановић Батут”
                        </div>
                        <div class="institute-intro weak">
                            INSTITUT ZA JAVNO ZDRAVLJE SRBIJE
                        </div>
                        <div class="institute-name weak">
                            „Dr Milan Jovanović Batut”
                        </div>
                        <div class="institute-intro weak">
                            INSTITUTE OF PUBLIC HEALTH OF SERBIA
                        </div>
                        <div class="institute-name weak">
                            „Dr Milan Jovanović Batut”
                        </div>
                    </div>
                </div>
                </header>

                <xsl:apply-templates select="@sifra-potvrde" />

                <div style="text-align: center">
                    <div style="font-size: 20px; margin-top:2.5%;"><strong>ПОТВРДА О ИЗВРШЕНОЈ ВАКЦИНАЦИЈИ ПРОТИВ COVID-19</strong></div>
                    <div class="weak" style="margin-top: 0.3%;">POTVRDA O IZVRŠENOJ VAKCINACIJI <strong>PROTIV COVID-19</strong></div>
                    <div class="weak" style="margin-top: 0.3%;">CONFIRMATION OF THE <strong>COVID-19</strong> VACCINATION</div>
                </div>

                <xsl:apply-templates select="x:podaci-o-vakcinisanom" />
                <xsl:apply-templates select="x:podaci-o-vakcinaciji" />

                <xsl:call-template name="vazenje-potvrde" />

        </body>
    </html>
</xsl:template>

<xsl:template match ="@sifra-potvrde">
    <div style="margin-top: 2%; font-size: 17px;">
        <strong>Шифра потврде вакцинације: </strong> <xsl:value-of select="." />
        <div class="weak" style="margin-top: 0.3%;">Šifra potvrde / Confirmation code</div>
    </div>
</xsl:template>


<xsl:template name ="pol-vakcinisanog-srb">
    <xsl:choose>
        <xsl:when test="os:pol = 0">
            Женско
        </xsl:when>
        <xsl:otherwise>
            Мушко
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template name ="pol-vakcinisanog-eng">
    <xsl:choose>
        <xsl:when test="os:pol = 0">
            Female
        </xsl:when>
        <xsl:otherwise>
            Male
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template name ="vazenje-potvrde">
    <div class="info">
        Ова потврда важи без потписа и печата 
    </div>
    <div class="info-language weak">
        Ova potvrda važi bez potpisa i pečata / This certificate is valid without signatures and seals
    </div>
</xsl:template>

<xsl:template name="redni-broj-doze-pismo" >
    <xsl:param name="redni-broj" />
    <xsl:param name="pismo" />

    <xsl:choose>
        <xsl:when test="$redni-broj = 1 and $pismo = 'cir'">
            прве
        </xsl:when>
        <xsl:when test="$redni-broj = 2 and $pismo = 'cir'">
            друге
        </xsl:when>
        <xsl:when test="$redni-broj = 2 and $pismo = 'lat'">
            druge
        </xsl:when>
        <xsl:when test="$redni-broj = 2 and $pismo = 'eng'">
            Second
        </xsl:when>
        <xsl:when test="$redni-broj = 3 and $pismo = 'cir'">
            треће
        </xsl:when>
        <xsl:when test="$redni-broj = 3 and $pismo = 'lat'">
            treće
        </xsl:when>
        <xsl:when test="$redni-broj = 3 and $pismo = 'eng'">
            Third
        </xsl:when>
    </xsl:choose>
</xsl:template>

<xsl:template name="podaci-o-dozi" >
    <xsl:param name="datum" />
    <xsl:param name="serija" />
    <xsl:param name="redni-broj" />

    <div class="info">
        <strong>Датум давања и број серије <xsl:call-template name="redni-broj-doze-pismo">
            <xsl:with-param name="redni-broj" select="$redni-broj" />
            <xsl:with-param name="pismo">
                <xsl:text>cir</xsl:text>
            </xsl:with-param>
        </xsl:call-template> дозе вакцине: </strong>
        <xsl:choose>
            <xsl:when test="$datum != ''">
                <strong><xsl:value-of select="$datum"/> , серија: <xsl:value-of select="$serija"/></strong>
            </xsl:when>
            <xsl:otherwise>
              ㅤ
            </xsl:otherwise>
        </xsl:choose>
    </div>
    <div class="info-language weak">
        Datum <xsl:call-template name="redni-broj-doze-pismo">
            <xsl:with-param name="redni-broj" select="$redni-broj" />
            <xsl:with-param name="pismo">
                <xsl:text>lat</xsl:text>
            </xsl:with-param>
        </xsl:call-template> vakcinacije / <xsl:call-template name="redni-broj-doze-pismo">
            <xsl:with-param name="redni-broj" select="$redni-broj" />
            <xsl:with-param name="pismo">
                <xsl:text>eng</xsl:text>
            </xsl:with-param>
        </xsl:call-template> Vaccination Date
    </div>
</xsl:template>

<xsl:template name ="primljene-doze">
    <xsl:for-each select="x:podaci-o-dozama/x:primljena-doza">
        <xsl:call-template name="podaci-o-dozi">
            <xsl:with-param name="datum" select="x:datum" />
            <xsl:with-param name="serija" select="x:serija" />
            <xsl:with-param name="redni-broj" select="@redni-broj" />
        </xsl:call-template>
      </xsl:for-each>
</xsl:template>

<xsl:template match ="x:podaci-o-vakcinisanom">
    <div class="info">
        <strong>Име и презиме: <xsl:value-of select="concat(os:ime,' ', os:prezime)" /></strong>
    </div>
    <div class="info-language weak">
        Ime i prezime / First and Last Name
    </div>

    <div class="info">
        <strong>Датум рођења: <xsl:value-of select="x:datum-rodjenja" /></strong>
    </div>
    <div class="info-language weak">
        Datum rođenja / Date of Birth
    </div>

    <div class="info">
        <strong>Пол:  <xsl:call-template name="pol-vakcinisanog-srb" /></strong>
    </div>
    <div class="info-language weak">
        Pol:  <xsl:call-template name="pol-vakcinisanog-srb" /> / Gender:  <xsl:call-template name="pol-vakcinisanog-eng" />
    </div>

    <div class="info">
        <strong>JMБГ: <xsl:value-of select="os:jmbg" /></strong>
    </div>
    <div class="info-language weak">
        JMBG / Personal. No.
    </div>
</xsl:template>

<xsl:template match ="x:podaci-o-vakcinaciji">
    <xsl:call-template name="primljene-doze" /> 

    <div class="info">
        <strong>Здравствена установа која вакцинише: <xsl:value-of select="x:zdravstvena-ustanova" /></strong>
    </div>
    <div class="info-language weak">
        Zdravstvena ustanova koja vakciniše / Health care institution of vaccination
    </div>

    <div class="info">
        <strong>Назив вакцине: <xsl:value-of select="x:naziv-vakcine" /></strong>
    </div>
    <div class="info-language weak">
        Naziv vakcine / Name of vaccine
    </div>

    <div class="info">
        <strong>Датум издавања потврде: <xsl:value-of select="../@datum-izdavanja" /></strong>
    </div>
    <div class="info-language weak">
        Datum izdavanja potvrde / Confirmation Release Date
    </div>

    <div style="float: right; display: block; width: 100%; text-align: right;">
        <div class="info">
            <strong>Здравствена установа: <xsl:value-of select="x:zdravstvena-ustanova" /></strong>
        </div>
        <div class="info-language weak">
            Zdravstvena ustanova / Medical institution
        </div>
        <img>
            <xsl:attribute name="src">
                https://api.qrserver.com/v1/create-qr-code/?size200=x150&amp;data=<xsl:value-of select="../x:qr-kod"/>
            </xsl:attribute>
        </img>
    </div>
</xsl:template>

</xsl:stylesheet>