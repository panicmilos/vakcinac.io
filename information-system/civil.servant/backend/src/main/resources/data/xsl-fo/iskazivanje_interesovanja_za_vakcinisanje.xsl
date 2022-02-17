<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format"
  xmlns:x="https://www.vakcinac-io.rs/interesovanje"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
>

  <xsl:import href="./styles/iskazivanje_interesovanja_za_vakcinisanje.xsl" />

  <xsl:variable name="fo:layout-master-set">
    <fo:layout-master-set>
      <fo:simple-page-master master-name="izjava-interesovanja-za-vakcinisanje-page">
        <fo:region-body margin-left="1in" margin-right="1in" margin-top="0.4in" margin-bottom="0.2in" />
      </fo:simple-page-master>
    </fo:layout-master-set>
  </xsl:variable>

  <xsl:template match="/x:izjava-interesovanja-za-vakcinisanje">
    <fo:root font-family="Times New Roman">
      <xsl:copy-of select="$fo:layout-master-set" />

      <fo:page-sequence master-reference="izjava-interesovanja-za-vakcinisanje-page">
        <fo:flow flow-name="xsl-region-body">
          <fo:block xsl:use-attribute-sets="h3">Исказивање интересовања за вакцинисање против COVID-19</fo:block>
          <fo:block xsl:use-attribute-sets="div">
            <fo:inline>Одаберите опцију:</fo:inline>
            <fo:block xsl:use-attribute-sets="div options">
              <xsl:apply-templates select="x:podnosilac-izjave/x:podnosilac/x:drzavljanstvo" />
            </fo:block>
            <fo:block xsl:use-attribute-sets="div">

              <fo:table xsl:use-attribute-sets="margin-top">
                <fo:table-column column-width="100%" />
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>
                        ЈМБГ:
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block xsl:use-attribute-sets="dots-underline">
                        <xsl:value-of select="x:podnosilac-izjave/x:podnosilac/os:jmbg" />
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>

              <fo:table xsl:use-attribute-sets="margin-top">
                <fo:table-column column-width="100%" />
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>
                        Име:
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block xsl:use-attribute-sets="dots-underline">
                        <xsl:value-of select="x:podnosilac-izjave/x:podnosilac/os:ime" />
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>

              <fo:table xsl:use-attribute-sets="margin-top">
                <fo:table-column column-width="100%" />
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>
                        Презиме:
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block xsl:use-attribute-sets="dots-underline">
                        <xsl:value-of select="x:podnosilac-izjave/x:podnosilac/os:prezime" />
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>

              <fo:table xsl:use-attribute-sets="margin-top">
                <fo:table-column column-width="100%" />
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>
                        Адреса електронске поште:
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block xsl:use-attribute-sets="dots-underline">
                        <xsl:value-of select="x:podnosilac-izjave/x:kontakt/os:adresa-elektronske-poste" />
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>

              <fo:table xsl:use-attribute-sets="margin-top">
                <fo:table-column column-width="100%" />
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>
                        Број мобилног телефона (навести број у формату 06X..... без размака и цртица):
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block xsl:use-attribute-sets="dots-underline">
                        <xsl:value-of select="x:podnosilac-izjave/x:kontakt/os:broj-mobilnog-telefona" />
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>

              <fo:table xsl:use-attribute-sets="margin-top">
                <fo:table-column column-width="100%" />
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>
                        Број фиксног телефона (навести број у формату нпр. 011..... без размака и цртица):
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block xsl:use-attribute-sets="dots-underline">
                        <xsl:value-of select="x:podnosilac-izjave/x:kontakt/os:broj-fiksnog-telefona" />
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>

              <fo:table xsl:use-attribute-sets="margin-top">
                <fo:table-column column-width="100%" />
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block>
                        Одаберите локацију где желите да примите вакцину (унесите општину):
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell>
                      <fo:block xsl:use-attribute-sets="dots-underline">
                        <xsl:value-of select="x:informacije-o-primanju-vakcine/x:opstina" />
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:block>

            <fo:block xsl:use-attribute-sets="margin-top">
              Исказујем интересовање да примим искључиво вакцину следећих произвођача за који Агенција за лекове и медицинска средства потврди безбедност, ефикасност и квалитет и изда дозволу за употребу лека:
            </fo:block>

            <fo:block xsl:use-attribute-sets="div options">
              <xsl:apply-templates select="x:informacije-o-primanju-vakcine/x:proizvodjaci" />
            </fo:block>
            <fo:inline xsl:use-attribute-sets="blood">Да ли сте добровољни давалац крви?</fo:inline>
            <fo:block xsl:use-attribute-sets="div options">
              <xsl:apply-templates select="x:podnosilac-izjave/x:dobrovoljan-davalac-krvi" />
            </fo:block>
          </fo:block>

          <fo:table xsl:use-attribute-sets="margin-top">
            <fo:table-column column-width="30%" />
            <fo:table-column column-width="30%" />
            <fo:table-column column-width="40%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                    <fo:inline>
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

  <xsl:template match="x:podnosilac-izjave/x:podnosilac/x:drzavljanstvo">
    <fo:list-block xsl:use-attribute-sets="list-block">
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="current() = 0">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Држављанин Републике Србије</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Држављанин Републике Србије</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="current() = 1">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Страни држављанин са боравком у РС</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Страни држављанин са боравком у РС</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="current() = 2">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Страни држављанин без боравка у РС</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Страни држављанин без боравка у РС</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
    </fo:list-block>
  </xsl:template>

  <xsl:template match="x:informacije-o-primanju-vakcine/x:proizvodjaci">
    <fo:list-block xsl:use-attribute-sets="list-block">
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="boolean(x:proizvodjac = 0)">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Pfizer-BioNTech</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Pfizer-BioNTech</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="boolean(x:proizvodjac = 1)">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Sputnik V (Gamaleya истраживачки центар)</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Sputnik V (Gamaleya истраживачки центар)</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="boolean(x:proizvodjac = 2)">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Sinopharm</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Sinopharm</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="boolean(x:proizvodjac = 3)">
                <fo:inline xsl:use-attribute-sets="chosen-option option">AstraZeneca</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">AstraZeneca</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="boolean(x:proizvodjac = 4)">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Moderna</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Moderna</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="boolean(x:proizvodjac = 5)">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Било која</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Било која</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
    </fo:list-block>
  </xsl:template>

  <xsl:template match="x:podnosilac-izjave/x:dobrovoljan-davalac-krvi">
    <fo:list-block xsl:use-attribute-sets="list-block">
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="current() = 'true'">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Да</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Да</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item xsl:use-attribute-sets="list-item-block">
        <fo:list-item-label>
          <fo:block></fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            <xsl:choose>
              <xsl:when test="current() = 'false'">
                <fo:inline xsl:use-attribute-sets="chosen-option option">Не</fo:inline>
              </xsl:when>
              <xsl:otherwise>
                <fo:inline xsl:use-attribute-sets="option">Не</fo:inline>
              </xsl:otherwise>
            </xsl:choose>
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
    </fo:list-block>
  </xsl:template>

</xsl:stylesheet>