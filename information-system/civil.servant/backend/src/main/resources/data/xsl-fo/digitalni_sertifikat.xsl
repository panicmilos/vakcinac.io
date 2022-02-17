<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:fo="http://www.w3.org/1999/XSL/Format"
  xmlns:x="https://www.vakcinac-io.rs/digitalni-sertifikat"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
  version="2.0"
>

  <xsl:import href="./styles/digitalni_sertifikat.xsl" />

  <xsl:variable name="fo:layout-master-set">
    <fo:layout-master-set>
      <fo:simple-page-master master-name="digitalni-sertifikat">
        <fo:region-body margin-left="0.1in" margin-right="0.1in" margin-top="0in" margin-bottom="0.1in"/>
      </fo:simple-page-master>
    </fo:layout-master-set>
  </xsl:variable>

  <xsl:template match="x:digitalni-sertifikat">
    <fo:root font-family="Times New Roman">
        <xsl:copy-of select="$fo:layout-master-set" />

        <fo:page-sequence master-reference="digitalni-sertifikat">
            <fo:flow flow-name="xsl-region-body">

              <fo:block space-before="0mm" space-after="5mm">
                <fo:table>
                  <fo:table-column column-width="25.5%"/>
                  <fo:table-column column-width="49%"/>
                  <fo:table-column column-width="25.5%"/>
                  <fo:table-body>
                    <fo:table-row>
                      <fo:table-cell>
                        <fo:block text-align="center" space-after="1mm">
                          <fo:external-graphic
                                  content-height="115px"
                                  content-width="scale-down-to-fit"
                                  src="url(http://www.parlament.gov.rs/upload/images/content/amblems/mali-grb-kolorni.jpg)"
                                  scaling="uniform"/>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell number-rows-spanned="2" display-align="center">
                        <fo:block text-align="center" space-before="1mm" space-after="1mm">
                          <fo:block space-before="1mm" space-after="1mm">
                            <fo:inline xsl:use-attribute-sets="h3">
                              ДИГИТАЛНИ ЗЕЛЕНИ СЕРТИФИКАТ
                            </fo:inline>
                          </fo:block>
                          <fo:block>
                            Потврда о извршеној вакцинацији против COVID-19 и резултатима тестирања
                          </fo:block>
                        </fo:block>
                        <fo:block text-align="center" space-before="1mm" space-after="1mm">
                          <fo:block space-before="1mm" space-after="1mm">
                            <fo:inline xsl:use-attribute-sets="h3">
                              DIGITAL GREEN CERTIFICATE
                            </fo:inline>
                          </fo:block>
                          <fo:block>
                            Certificate of vaccination against COVID-19 and test results
                          </fo:block>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell number-rows-spanned="2" display-align="center">
                        <fo:block text-align="center" space-after="1mm">
                          <fo:external-graphic
                                  content-height="130px"
                                  content-width="scale-down-to-fit"
                                  scaling="uniform">
                            <xsl:attribute name="src">
                              https://api.qrserver.com/v1/create-qr-code/?size400=x150&amp;data=<xsl:value-of select="x:validacija"/>
                            </xsl:attribute>
                          </fo:external-graphic>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell font-size="9px" text-align="center" font-weight="bold">
                        <fo:block space-before="1mm" space-after="0.5mm">
                          РЕПУБЛИКА СРБИЈА
                        </fo:block>
                        <fo:block space-before="1mm" space-after="0.5mm">
                          REPUBLIC OF SERBIA
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                  </fo:table-body>
                </fo:table>
              </fo:block>

              <fo:block font-size="10px" space-after="3mm">
                <fo:table>
                  <fo:table-column column-width="20%"/>
                  <fo:table-column column-width="30%"/>
                  <fo:table-column column-width="35%"/>
                  <fo:table-column column-width="15%"/>
                  <fo:table-body>
                    <fo:table-row>
                      <fo:table-cell padding-left="5px">
                        <fo:block>
                          <fo:inline font-weight="bold">Број сертификата /&#xA; Certificate ID:</fo:inline>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell padding-left="5px">
                        <fo:block>
                          <xsl:value-of select="@broj-sertifikata"/>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell padding-left="5px">
                        <fo:block>
                          <fo:inline font-weight="bold">Датум и време издавања сертификата /&#xA; Certificate issuing date and time:</fo:inline>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell padding-left="5px">
                        <fo:block>
                          <xsl:value-of select="@datum-izdavanja"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                  </fo:table-body>
                </fo:table>
              </fo:block>
              <xsl:apply-templates select="x:nosilac-sertifikata"/>
              <xsl:apply-templates select="x:vakcinacije"/>
              <xsl:apply-templates select="x:testovi"/>

              <fo:block>
                <fo:table>
                  <fo:table-column column-width="15%"/>
                  <fo:table-column column-width="35%"/>
                  <fo:table-column column-width="25%"/>
                  <fo:table-column column-width="25%"/>
                  <fo:table-body>
                    <fo:table-row>
                      <fo:table-cell><fo:block></fo:block></fo:table-cell>
                      <fo:table-cell><fo:block></fo:block></fo:table-cell>
                      <fo:table-cell number-columns-spanned="2" text-align="center">
                        <fo:block>
                          <fo:inline font-weight="bold" font-size="10px">
                            Дигитални потпис / Digitaly signed by:
                          </fo:inline>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell display-align="center">
                        <fo:block>
                          <fo:external-graphic content-height="70px"
                                               content-width="scale-down-to-fit"
                                               src="url(https://euprava.gov.rs/media/logos/Batut.gif)"
                                               scaling="uniform"/>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-size="10px" display-align="center" text-align="left">
                        <fo:block>
                          <fo:inline font-weight="bold">Сертификат издаје:</fo:inline>
                        </fo:block>
                        <fo:block>
                          Институт за јавно здравље Србије
                        </fo:block>
                        <fo:block>
                          "Др Милан Јовановић Батут"
                        </fo:block>
                        <fo:block>
                          <fo:inline font-weight="bold">Certificate issued by:</fo:inline>
                        </fo:block>
                        <fo:block>
                          Institute of Public Health of Serbia
                        </fo:block>
                        <fo:block>
                          "Dr Milan Jovanović Batut"
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell display-align="center" text-align="right">
                        <fo:block>
                          <fo:external-graphic content-height="70px"
                                               content-width="scale-down-to-fit"
                                               src="url(http://www.parlament.gov.rs/upload/images/content/amblems/mali-grb-kolorni.jpg)"
                                               scaling="uniform"/>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-size="8px" display-align="center">
                        <fo:block>
                          РЕПУБЛИКА СРБИЈА
                        </fo:block>
                        <fo:block>
                          Влада Републике Србије
                        </fo:block>
                        <fo:block>
                          Канцеларија за информационе
                        </fo:block>
                        <fo:block>
                          технологије и електронску управу
                        </fo:block>
                        <fo:block>
                          Немањина 11, Београд
                        </fo:block>
                        <fo:block>
                          Датум: <xsl:value-of select="@datum-izdavanja"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                  </fo:table-body>
                </fo:table>
              </fo:block>

          </fo:flow>
        </fo:page-sequence>
    </fo:root>
  </xsl:template>

  <xsl:template match="x:nosilac-sertifikata">
    <fo:block font-size="10px" border-bottom-style="solid" border-bottom-width="0.5px">
      <fo:table border-collapse="separate" border-spacing="0mm 3mm">
        <fo:table-column column-width="40%"/>
        <fo:table-column column-width="60%"/>
        <fo:table-body>
          <fo:table-row>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <fo:inline font-weight="bold">Име и презиме / Name and surname:</fo:inline>
              </fo:block>
            </fo:table-cell>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <xsl:value-of select="x:ime"/>&#160;<xsl:value-of select="x:prezime"/>
              </fo:block>
            </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <fo:inline font-weight="bold">Пол / Gender:</fo:inline>
              </fo:block>
            </fo:table-cell>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <xsl:choose>
                  <xsl:when test="x:pol = 0">
                    Женско/Female
                  </xsl:when>
                  <xsl:otherwise>
                    Мушко/Male
                  </xsl:otherwise>
                </xsl:choose>
              </fo:block>
            </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <fo:inline font-weight="bold">Датум рођења / Date of birth:</fo:inline>
              </fo:block>
            </fo:table-cell>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <xsl:value-of select="x:datum-rodjenja"/>
              </fo:block>
            </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <fo:inline font-weight="bold">ЈМБГ / Personal No. / EBS:</fo:inline>
              </fo:block>
            </fo:table-cell>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <xsl:choose>
                  <xsl:when test="x:jmbg != ''">
                      <xsl:value-of select="x:jmbg"/>
                  </xsl:when>
                  <xsl:when test="x:ebs != ''">
                      <xsl:value-of select="x:ebs"/>
                  </xsl:when>
                </xsl:choose>
              </fo:block>
            </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <fo:inline font-weight="bold">Број пасоша / Passport No.</fo:inline>
              </fo:block>
            </fo:table-cell>
            <fo:table-cell padding-left="5px">
              <fo:block>
                <xsl:if test="x:br-pasosa != ''">
                  <xsl:value-of select="x:br-pasosa"/>
                </xsl:if>
              </fo:block>
            </fo:table-cell>
          </fo:table-row>
        </fo:table-body>
      </fo:table>
    </fo:block>
  </xsl:template>

  <xsl:template match="x:vakcinacije">
    <fo:block font-size="10px" border-bottom-style="solid" border-bottom-width="0.5px">
      <fo:block xsl:use-attribute-sets="center-content" padding-top="2px">
        <fo:inline font-weight="bold" font-size="14px">Вакцинација / Vaccination</fo:inline>
      </fo:block>
      <fo:block>
        <fo:table>
          <fo:table-body>
            <fo:table-row>
              <xsl:apply-templates select="x:vakcinacija"/>
            </fo:table-row>
          </fo:table-body>
        </fo:table>
      </fo:block>
    </fo:block>
  </xsl:template>

  <xsl:template match="x:vakcinacija[position() != last()]">
    <fo:table-cell border-end-style="solid" border-end-width="0.5px" padding-left="5px">
      <fo:block linefeed-treatment="preserve">
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Доза / Dose: <xsl:value-of select="@doza"/> / <xsl:value-of select="../@broj-doza"/></fo:inline>
        </fo:block>
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Тип / Type:</fo:inline>
        </fo:block>
        <fo:block space-after="3mm">
          <xsl:value-of select="x:tip"/>
        </fo:block>
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Произвођач и серија / Manufacturer and batch number:</fo:inline>
        </fo:block>
        <fo:block space-after="3mm">
          <xsl:value-of select="x:proizvodjac"/>, <xsl:value-of select="x:serija"/>
        </fo:block>
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Датум / Date:</fo:inline> <xsl:value-of select="x:datum"/>
        </fo:block>
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Здравствена установа / Health care institution:</fo:inline>
        </fo:block>
        <fo:block space-after="3mm" padding-after="3mm">
          <xsl:value-of select="x:zdravstvena-ustanova"/>
        </fo:block>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

  <xsl:template match="x:vakcinacija[last()]">
    <fo:table-cell padding-left="5px">
      <fo:block linefeed-treatment="preserve">
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Доза / Dose: <xsl:value-of select="@doza"/> / <xsl:value-of select="../@broj-doza"/></fo:inline>
        </fo:block>
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Тип / Type:</fo:inline>
        </fo:block>
        <fo:block space-after="3mm">
          <xsl:value-of select="x:tip"/>
        </fo:block>
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Произвођач и серија / Manufacturer and batch number:</fo:inline>
        </fo:block>
        <fo:block space-after="3mm">
          <xsl:value-of select="x:proizvodjac"/>, <xsl:value-of select="x:serija"/>
        </fo:block>
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Датум / Date:</fo:inline> <xsl:value-of select="x:datum"/>
        </fo:block>
        <fo:block space-after="3mm">
          <fo:inline font-weight="bold">Здравствена установа / Health care institution:</fo:inline>
        </fo:block>
        <fo:block space-after="3mm" padding-after="3mm">
          <xsl:value-of select="x:zdravstvena-ustanova"/>
        </fo:block>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

  <xsl:template match="x:testovi">
    <fo:block font-size="10px">
      <fo:table>
        <fo:table-body>
          <fo:table-row>
            <xsl:apply-templates select="x:test" mode="ime"/>
          </fo:table-row>
          <fo:table-row>
            <xsl:apply-templates select="x:test" mode="test-info"/>
          </fo:table-row>
          <fo:table-row>
            <xsl:apply-templates select="x:test" mode="test-result"/>
          </fo:table-row>
        </fo:table-body>
      </fo:table>
    </fo:block>
  </xsl:template>

  <xsl:template match="x:test[position() != last()]" mode="ime">
    <fo:table-cell border-end-style="solid"
                   border-end-width="0.5px"
                   border-bottom-style="solid"
                   border-bottom-width="0.5px"
                   display-align="center"
                   padding="5px">
      <fo:block xsl:use-attribute-sets="center-content">
        <fo:inline font-weight="bold" font-size="12px"><xsl:value-of select="x:ime"/></fo:inline>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

  <xsl:template match="x:test[last()]" mode="ime">
    <fo:table-cell display-align="center"
                   border-bottom-style="solid"
                   border-bottom-width="0.5px"
                   padding="5px">
      <fo:block xsl:use-attribute-sets="center-content">
        <fo:inline font-weight="bold" font-size="12px"><xsl:value-of select="x:ime"/></fo:inline>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

  <xsl:template match="x:test[position() != last()]" mode="test-info">
    <fo:table-cell border-end-style="solid"
                   border-end-width="0.5px"
                   border-bottom-style="solid"
                   border-bottom-width="0.5px"
                   display-align="center"
                   padding="5px">
      <fo:block>
        <fo:inline font-weight="bold">Врста узорка / Sample type:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:vrsta-uzorka/@xsi:nil='true')">
              <xsl:value-of select="x:vrsta-uzorka"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
        <fo:inline font-weight="bold">Произвођач теста / Test manufacturer:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:proizvodjac/@xsi:nil='true')">
              <xsl:value-of select="x:proizvodjac"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
        <fo:inline font-weight="bold">Датум и време узорковања / Date and time of sampling:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:datum-uzorka/@xsi:nil='true')">
              <xsl:value-of select="x:datum-uzorka"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
        <fo:inline font-weight="bold">Датум и време издавања резултата / Date and time of result:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:datum-izdavanja/@xsi:nil='true')">
              <xsl:value-of select="x:datum-izdavanja"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

  <xsl:template match="x:test[last()]" mode="test-info">
    <fo:table-cell display-align="center"
                   border-bottom-style="solid"
                   border-bottom-width="0.5px"
                   padding="5px">
      <fo:block>
        <fo:inline font-weight="bold">Врста узорка / Sample type:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:vrsta-uzorka/@xsi:nil='true')">
              <xsl:value-of select="x:vrsta-uzorka"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
        <fo:inline font-weight="bold">Произвођач теста / Test manufacturer:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:proizvodjac/@xsi:nil='true')">
              <xsl:value-of select="x:proizvodjac"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
        <fo:inline font-weight="bold">Датум и време узорковања / Date and time of sampling:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:datum-uzorka/@xsi:nil='true')">
              <xsl:value-of select="x:datum-uzorka"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
        <fo:inline font-weight="bold">Датум и време издавања резултата / Date and time of result:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:datum-izdavanja/@xsi:nil='true')">
              <xsl:value-of select="x:datum-izdavanja"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

  <xsl:template match="x:test" mode="test-result">
    <fo:table-cell padding-left="5px" background-color="#e6e6e6">
      <fo:block>
        <fo:inline font-weight="bold">Резултат / Result:</fo:inline>
        <fo:block>
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
        </fo:block>
        <fo:inline font-weight="bold">Лабораторија / Laboratory:</fo:inline>
        <fo:block>
          <xsl:choose>
            <xsl:when test="not(x:labaratorija/@xsi:nil='true')">
              <xsl:value-of select="x:labaratorija"/>
            </xsl:when>
            <xsl:otherwise>
              N/A
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

</xsl:stylesheet>