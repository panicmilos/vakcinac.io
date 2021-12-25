<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:p="https://www.vakcinac-io.rs/potvrda"
        xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
        version="2.0"
>
    <xsl:import href="./styles/potvrda_o_izvrsenoj_vakcinaciji.xsl"/>

    <xsl:variable name="fo:layout-master-set">
        <fo:layout-master-set>
            <fo:simple-page-master master-name="potvrda-o-izvrsenoj-vakcinaciji-page">
                <fo:region-body margin="0.5in" />
            </fo:simple-page-master>
        </fo:layout-master-set>
    </xsl:variable>

    <xsl:template match="p:potvrda-o-izvrsenoj-vakcinaciji">
        <fo:root font-family="Times New Roman">
            <xsl:copy-of select="$fo:layout-master-set" />

            <fo:page-sequence master-reference="potvrda-o-izvrsenoj-vakcinaciji-page">
                <fo:flow flow-name="xsl-region-body">

                    <fo:block>
                        <fo:table>
                            <fo:table-column column-width="40%"/>
                            <fo:table-column column-width="60%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block xsl:use-attribute-sets="institute-logo">
                                            <fo:external-graphic
                                                    src="url(institute-logo.jpg)"
                                                    content-width="100px"
                                                    content-height="100px"
                                                    scaling="uniform">
                                            </fo:external-graphic>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block xsl:use-attribute-sets="institute-intro text-center bold-text">ИНСТИТУТ ЗА ЈАВНО ЗДРАВЉЕ СРБИЈЕ</fo:block>
                                        <fo:block xsl:use-attribute-sets="institute-name text-center bold-text">„Др Милан Јовановић Батут”</fo:block>
                                        <fo:block xsl:use-attribute-sets="institute-intro text-center weak-text">INSTITUT ZA JAVNO ZDRAVLJE SRBIJE</fo:block>
                                        <fo:block xsl:use-attribute-sets="institute-name text-center weak-text">„Dr Milan Jovanović Batut”</fo:block>
                                        <fo:block xsl:use-attribute-sets="institute-intro text-center weak-text">INSTITUTE OF PUBLIC HEALTH OF SERBIA</fo:block>
                                        <fo:block xsl:use-attribute-sets="institute-name text-center weak-text">„Dr Milan Jovanović Batut”</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <xsl:apply-templates select="@sifra-potvrde" />

                    <fo:block xsl:use-attribute-sets="text-center">
                        <fo:block xsl:use-attribute-sets="vaccination-header bold-text">
                            ПОТВРДА О ИЗВРШЕНОЈ ВАКЦИНАЦИЈИ ПРОТИВ COVID-19
                        </fo:block>

                        <fo:block xsl:use-attribute-sets="vaccination-subheader weak-text mt-0.3">
                            POTVRDA O IZVRŠENOJ VAKCINACIJI <fo:inline xsl:use-attribute-sets="bold-text">PROTIV COVID-19</fo:inline>
                        </fo:block>

                        <fo:block xsl:use-attribute-sets="vaccination-subheader weak-text mt-0.3">
                            CONFIRMATION OF THE <fo:inline xsl:use-attribute-sets="bold-text">COVID-19</fo:inline> VACCINATION
                        </fo:block>
                    </fo:block>

                    <xsl:apply-templates select="p:podaci-o-vakcinisanom" />
                    <xsl:apply-templates select="p:podaci-o-vakcinaciji" />

                    <xsl:call-template name="vazenje-potvrde" />

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="@sifra-potvrde">
        <fo:block xsl:use-attribute-sets="confirmation-code">
            Шифра потврде вакцинације: <xsl:value-of select="." />
            <fo:block xsl:use-attribute-sets="weak-text mt-0.3">
                Šifra potvrde / Confirmation code
            </fo:block>
        </fo:block>
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
        <fo:table xsl:use-attribute-sets="mt-4.5">
            <fo:table-column column-width="80%"/>
            <fo:table-column column-width="20%"/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell xsl:use-attribute-sets="confirmation">
                            <fo:block xsl:use-attribute-sets="info-confirmation">
                                Ова потврда важи без потписа и печата
                            </fo:block>>

                            <fo:block xsl:use-attribute-sets="info-confirmation-weak mt-0.3 weak-text">
                                Ova potvrda važi bez potpisa i pečata / This certificate is valid without signatures and seals
                            </fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                        <fo:block xsl:use-attribute-sets="text-right">
                            <fo:external-graphic
                                    src="url(https://api.qrserver.com/v1/create-qr-code/?size200=x150&amp;data=proba)"
                                    content-width="80px"
                                    content-height="80px"
                                    scaling="uniform">
                            </fo:external-graphic>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
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

        <fo:block xsl:use-attribute-sets="info bold-text">
            Датум давања и број серије <xsl:call-template name="redni-broj-doze-pismo">
                <xsl:with-param name="redni-broj" select="$redni-broj" />
                <xsl:with-param name="pismo">
                    <xsl:text>cir</xsl:text>
                </xsl:with-param>
            </xsl:call-template> дозе вакцине:
            <xsl:choose>
                <xsl:when test="$datum != ''">
                    <xsl:value-of select="$datum"/> , серија: <xsl:value-of select="$serija"/>
                </xsl:when>
                <xsl:otherwise></xsl:otherwise>
            </xsl:choose>
        </fo:block>>

        <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
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
        </fo:block>>
    </xsl:template>

    <xsl:template name ="primljene-doze">
        <xsl:for-each select="p:podaci-o-dozama/p:primljena-doza">
            <xsl:call-template name="podaci-o-dozi">
                <xsl:with-param name="datum" select="p:datum" />
                <xsl:with-param name="serija" select="p:serija" />
                <xsl:with-param name="redni-broj" select="@redni-broj" />
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match ="p:podaci-o-vakcinisanom">
        <fo:block xsl:use-attribute-sets="info bold-text">
            Име и презиме: <xsl:value-of select="concat(os:ime,' ', os:prezime)" />
        </fo:block>>

        <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
            Ime i prezime / First and Last Name
        </fo:block>

        <fo:block xsl:use-attribute-sets="info bold-text">
            Датум рођења: <xsl:value-of select="p:datum-rodjenja" />
        </fo:block>

        <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
            Datum rođenja / Date of Birth
        </fo:block>

        <fo:block xsl:use-attribute-sets="info bold-text">
            Пол:  <xsl:call-template name="pol-vakcinisanog-srb" />
        </fo:block>

        <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
            Pol:  <xsl:call-template name="pol-vakcinisanog-srb" /> / Gender:  <xsl:call-template name="pol-vakcinisanog-eng" />
        </fo:block>

        <fo:block xsl:use-attribute-sets="info bold-text">
            JMБГ: <xsl:value-of select="os:jmbg" />
        </fo:block>

        <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
            JMBG / Personal. No.
        </fo:block>
    </xsl:template>

    <xsl:template match ="p:podaci-o-vakcinaciji">
        <xsl:call-template name="primljene-doze" />

        <fo:block xsl:use-attribute-sets="info bold-text">
            Здравствена установа која вакцинише: <xsl:value-of select="p:zdravstvena-ustanova" />
        </fo:block>

        <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
            Zdravstvena ustanova koja vakciniše / Health care institution of vaccination
        </fo:block>

        <fo:block xsl:use-attribute-sets="info bold-text">
            Назив вакцине: <xsl:value-of select="p:naziv-vakcine" />
        </fo:block>

        <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
            Naziv vakcine / Name of vaccine
        </fo:block>

        <fo:block xsl:use-attribute-sets="info bold-text">
            Датум издавања потврде: <xsl:value-of select="../@datum-izdavanja" />
        </fo:block>

        <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
            Datum izdavanja potvrde / Confirmation Release Date
        </fo:block>

        <fo:block xsl:use-attribute-sets="mt-4.5 text-right">
            <fo:block xsl:use-attribute-sets="info bold-text">
                Здравствена установа: <xsl:value-of select="p:zdravstvena-ustanova" />
            </fo:block>

            <fo:block xsl:use-attribute-sets="info-language mt-0.3 weak-text">
                Zdravstvena ustanova / Medical institution
            </fo:block>
        </fo:block>
    </xsl:template>

</xsl:stylesheet>