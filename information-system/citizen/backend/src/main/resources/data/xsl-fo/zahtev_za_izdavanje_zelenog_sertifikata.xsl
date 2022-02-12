<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format"
  xmlns:x="https://www.vakcinac-io.rs/zahtev"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
>

  <xsl:import href="./styles/zahtev_za_izdavanje_zelenog_sertifikata.xsl" />

  <xsl:variable name="fo:layout-master-set">
    <fo:layout-master-set>
      <fo:simple-page-master master-name="zahtev-za-izdavanje-zelenog-sertifikata-page">
        <fo:region-body margin="1in" />
      </fo:simple-page-master>
    </fo:layout-master-set>
  </xsl:variable>

  <xsl:template match="/x:zahtev-za-izdavanje-zelenog-sertifikata">
    <fo:root font-family="Times New Roman">
      <xsl:copy-of select="$fo:layout-master-set" />

      <fo:page-sequence master-reference="zahtev-za-izdavanje-zelenog-sertifikata-page">
        <fo:flow flow-name="xsl-region-body">
          <fo:block xsl:use-attribute-sets="h2">ЗАХТЕВ</fo:block>
          <fo:block xsl:use-attribute-sets="h3 margin-bottom-sm">за издавање дигиталног зеленог сертификата</fo:block>
          <fo:block xsl:use-attribute-sets="p intro-text">У складу са одредбом Републике Србије о издавању дигиталног зеленог
  сертификата као потврде о извршеној вакцинацији против COVID-19, резултатима
  тестирања на заразну болест SARS-CoV-2 или опоравку од болести COVID-19,
  подносим захтев за издавање дигиталног зеленог сертификата.</fo:block>

          <fo:block xsl:use-attribute-sets="requester-text">Подносилац захтева:</fo:block>

          <fo:table xsl:use-attribute-sets="margin-top">
            <fo:table-column column-width="18%" />
            <fo:table-column column-width="82%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                  Име и презиме:
                </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block xsl:use-attribute-sets="dots-underline">
                    <xsl:apply-templates select="x:podnosilac-zahteva" />
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>

          <fo:table xsl:use-attribute-sets="margin-top">
            <fo:table-column column-width="17%" />
            <fo:table-column column-width="83%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                  Датум рођења:
                </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block xsl:use-attribute-sets="dots-underline">
                    <xsl:value-of select="x:podnosilac-zahteva/x:datum-rodjenja" />
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>

          <fo:table xsl:use-attribute-sets="margin-top">
            <fo:table-column column-width="6%" />
            <fo:table-column column-width="94%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                  Пол:
                </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block xsl:use-attribute-sets="dots-underline">
                    <xsl:apply-templates select="x:podnosilac-zahteva/x:pol" />
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>

          <fo:table xsl:use-attribute-sets="margin-top">
            <fo:table-column column-width="43%" />
            <fo:table-column column-width="57%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                  Јединствени матични број грађанина:
                </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block xsl:use-attribute-sets="dots-underline">
                    <xsl:value-of select="x:podnosilac-zahteva/os:jmbg" />
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>

          <fo:table xsl:use-attribute-sets="margin-top margin-bottom-sm">
            <fo:table-column column-width="15%" />
            <fo:table-column column-width="85%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                  Број пасоша:
                </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block xsl:use-attribute-sets="dots-underline">
                    <xsl:value-of select="x:podnosilac-zahteva/x:broj-pasosa" />
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>

          <fo:block xsl:use-attribute-sets="p">Разлог за подношење захтева:</fo:block>
          <fo:table xsl:use-attribute-sets="div margin-top">
            <fo:table-column column-width="100%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block xsl:use-attribute-sets="dots-underline">
                    <xsl:value-of select="x:razlog" />
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>
          <fo:block xsl:use-attribute-sets="hint margin-bottom-lg">(навести што прецизнији разлог за подношење захтева за издавање дигиталног пасоша)</fo:block>

          <fo:table xsl:use-attribute-sets="margin-top">
            <fo:table-column column-width="30%" />
            <fo:table-column column-width="70%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                    <fo:table>
                      <fo:table-column column-width="8%" />
                      <fo:table-column column-width="81%" />
                      <fo:table-column column-width="2%" />
                      <fo:table-body>
                        <fo:table-row>
                          <fo:table-cell>
                            <fo:block>
                            У
                          </fo:block>
                          </fo:table-cell>
                          <fo:table-cell>
                            <fo:block xsl:use-attribute-sets="underline">
                              <xsl:value-of select="@mesto" />
                            </fo:block>
                          </fo:table-cell>
                          <fo:table-cell>
                            <fo:block>
                            ,
                          </fo:block>
                          </fo:table-cell>
                        </fo:table-row>
                      </fo:table-body>
                    </fo:table>
                  </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block></fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>

          <fo:table xsl:use-attribute-sets="margin-top">
            <fo:table-column column-width="30%" />
            <fo:table-column column-width="30%" />
            <fo:table-column column-width="40%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                    <fo:inline xsl:use-attribute-sets="p">
                      дана
                      <fo:inline xsl:use-attribute-sets="underline">
                        <xsl:value-of select="@dan" />
                      </fo:inline>
                      године
                    </fo:inline>
                  </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block></fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block></fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block></fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block></fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block xsl:use-attribute-sets="div signature">
                    <fo:inline xsl:use-attribute-sets="signature-label">Потпис</fo:inline>
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>

        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>

  <xsl:template match="x:podnosilac-zahteva/x:pol">
    <xsl:choose>
      <xsl:when test="current() = 0">
        <fo:inline>Мушко</fo:inline>
      </xsl:when>
      <xsl:otherwise>
        <fo:inline>Женско</fo:inline>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="x:podnosilac-zahteva">
    <xsl:value-of select="os:ime" />&#160;
    <xsl:value-of select="os:prezime" />
  </xsl:template>

</xsl:stylesheet>