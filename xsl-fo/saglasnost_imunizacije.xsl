<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format"
  xmlns:s="https://www.vakcinac-io.rs/saglasnost"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
  version="2.0"
>
  <xsl:import href="./styles/saglasnost_imunizacije.xsl"/>

  <xsl:variable name="fo:layout-master-set">
    <fo:layout-master-set>
      <fo:simple-page-master master-name="saglasnost-imunizacije-page">
        <fo:region-body margin="0.5in" />
      </fo:simple-page-master>
    </fo:layout-master-set>
  </xsl:variable>

  <xsl:template match="s:saglasnost-za-sprovodjenje-preporucene-imunizacije">
    <fo:root font-family="Times New Roman">
      <xsl:copy-of select="$fo:layout-master-set" />

      <fo:page-sequence master-reference="saglasnost-imunizacije-page">
        <fo:flow flow-name="xsl-region-body">

          <fo:block space-after="10px">
            <fo:table>
              <fo:table-column column-width="70%"/>
              <fo:table-column column-width="30%"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block xsl:use-attribute-sets="h1 bold-text">САГЛАСНОСТ ЗА СПРОВОЂЕЊЕ</fo:block>
                    <fo:block xsl:use-attribute-sets="h1 bold-text">ПРЕПОРУЧЕНЕ ИМУНИЗАЦИЈЕ</fo:block>
                    <fo:block xsl:use-attribute-sets="h3">(попуњава пацијент)</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>
                      <fo:external-graphic
                        src="url(https://media.covid19.rs/2020/02/BATUT-logo-300x158.png)"
                        content-width="120px"
                        content-height="105px"
                        scaling="uniform"/>
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>

          <fo:block>
            <xsl:apply-templates select="s:pacijent" />
          </fo:block>

          <fo:block>
            <xsl:apply-templates select="s:izjava-saglasnosti" />
          </fo:block>

          <fo:block xsl:use-attribute-sets="bold-text mb-10">
            Лекар ми је објаснио предности и ризике од спровођења активне/пасивне имунизације наведеним имунолошким леком.
          </fo:block>

          <fo:block>
            <xsl:apply-templates select="@datum-izdavanja" />
          </fo:block>

          <fo:block xsl:use-attribute-sets="separator" />

          <fo:block xsl:use-attribute-sets="centered-text" space-after="15px">
            <fo:block xsl:use-attribute-sets="h1">ЕВИДЕНЦИЈА О ВАКЦИНАЦИЈИ ПРОТИВ COVID-19</fo:block>
            <fo:block xsl:use-attribute-sets="h3">(попуњава здравствени радник)</fo:block>
          </fo:block>

          <fo:block>
            <xsl:apply-templates select="s:evidencija-o-vakcinaciji" />
          </fo:block>

        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>

  <xsl:template match="s:pacijent">
    <xsl:apply-templates select="s:drzavljanstvo"/>
    <xsl:apply-templates select="s:licne-informacije"/>
  </xsl:template>

  <xsl:template match="s:drzavljanstvo">
    <fo:table xsl:use-attribute-sets="table" margin-bottom="3px">
      <fo:table-column column-width="17%"/>
      <fo:table-column column-width="83%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text">Држављанство</fo:block></fo:table-cell>
          <fo:table-cell>
            <fo:block>
              <fo:table xsl:use-attribute-sets="inline-table">
                <fo:table-column column-width="25%"/>
                <fo:table-column column-width="9%"/>
                <fo:table-column column-width="66%"/>
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell xsl:use-attribute-sets="right-dash"><fo:block>1) Република Србија</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block xsl:use-attribute-sets="ml-5">ЈМБГ</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="s:domace/s:jmbg"/></fo:block></fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
        <fo:table-row>
          <fo:table-cell><fo:block>&#160;</fo:block></fo:table-cell>
          <fo:table-cell>
            <fo:block>
              <fo:table xsl:use-attribute-sets="table">
                <fo:table-column column-width="3%"/>
                <fo:table-column column-width="47%"/>
                <fo:table-column column-width="50%"/>
                <fo:table-body>
                  <fo:table-row>
                    <fo:table-cell><fo:block>2)</fo:block></fo:table-cell>
                    <fo:table-cell xsl:use-attribute-sets="right-dash mr-5"><fo:block xsl:use-attribute-sets="underline-text">&#160;<xsl:value-of select="s:strano/s:naziv-drzavljanstva"/></fo:block></fo:table-cell>
                    <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text ml-5">&#160;<xsl:value-of select="s:strano/s:broj-pasosa-ebs"/></fo:block></fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
                    <fo:table-cell><fo:block>&#160;</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block xsl:use-attribute-sets="centered-text hint">(назив страног држављанства)</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block xsl:use-attribute-sets="centered-text hint">(бр. пасоша или ЕБС за стране држављане)</fo:block></fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>
  </xsl:template>

  <xsl:template match="s:licne-informacije">
    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="20%"/>
      <fo:table-column column-width="15%"/>
      <fo:table-column column-width="19%"/>
      <fo:table-column column-width="16%"/>
      <fo:table-column column-width="30%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell>
            <fo:block xsl:use-attribute-sets="right-dash mr-5">
              <fo:inline-container>
                <fo:block>
                  <fo:inline xsl:use-attribute-sets="bold-text">Пол</fo:inline>:
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:pol = 0" />
                    <xsl:with-param name="text">
                      <xsl:text>М,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:pol = 1" />
                    <xsl:with-param name="text">
                      <xsl:text>Ж</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                </fo:block>
              </fo:inline-container>
            </fo:block>
          </fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text">Датум рођења</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="right-dash mr-5"><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="../s:rodjenje/s:datum-rodjenja" /></fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text ml-5">Место рођења</fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="../s:rodjenje/s:mesto-rodjenja" /></fo:block></fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="23%"/>
      <fo:table-column column-width="37%"/>
      <fo:table-column column-width="17%"/>
      <fo:table-column column-width="23%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text">Адреса (улица и број)</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="right-dash mr-5"><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="../s:stanovanje/s:adresa" /></fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text ml-5">Место/Насеље</fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="../s:stanovanje/s:mesto" /></fo:block></fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="16%"/>
      <fo:table-column column-width="44%"/>
      <fo:table-column column-width="15%"/>
      <fo:table-column column-width="25%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text">Општина/Град</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="right-dash mr-5"><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="../s:stanovanje/s:opstina" /></fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text ml-5">Тел. фиксни</fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="../s:kontakt/os:broj-fiksnog-telefona" /></fo:block></fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="15%"/>
      <fo:table-column column-width="35%"/>
      <fo:table-column column-width="8%"/>
      <fo:table-column column-width="42%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text">Тел. мобилни</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="right-dash mr-5"><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="../s:kontakt/os:broj-mobilnog-telefona" /></fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text ml-5">имејл</fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="../s:kontakt/os:adresa-elektronske-poste" /></fo:block></fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

    <fo:table xsl:use-attribute-sets="table">
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell>
            <fo:block>
              <fo:inline-container>
                <fo:block>
                  <fo:inline xsl:use-attribute-sets="bold-text">Радни статус</fo:inline>:
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:radni-status = 0" />
                    <xsl:with-param name="text">
                      <xsl:text>запослен,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:radni-status = 1" />
                    <xsl:with-param name="text">
                      <xsl:text>незапослен,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:radni-status = 2" />
                    <xsl:with-param name="text">
                      <xsl:text>пензионер,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:radni-status = 3" />
                    <xsl:with-param name="text">
                      <xsl:text>ученик,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:radni-status = 4" />
                    <xsl:with-param name="text">
                      <xsl:text>студент,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:radni-status = 5" />
                    <xsl:with-param name="text">
                      <xsl:text>дете</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                </fo:block>
              </fo:inline-container>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

    <fo:table xsl:use-attribute-sets="table">
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell>
            <fo:block>
              <fo:inline-container>
                <fo:block>
                  <fo:inline xsl:use-attribute-sets="bold-text">Занимање</fo:inline>:
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:zanimanje = 0" />
                    <xsl:with-param name="text">
                      <xsl:text>здравствена заш,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:zanimanje = 1" />
                    <xsl:with-param name="text">
                      <xsl:text>социјална заш,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:zanimanje = 2" />
                    <xsl:with-param name="text">
                      <xsl:text>просвета,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:zanimanje = 3" />
                    <xsl:with-param name="text">
                      <xsl:text>МУП,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:zanimanje = 4" />
                    <xsl:with-param name="text">
                      <xsl:text>Војска РС,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:zanimanje = 5" />
                    <xsl:with-param name="text">
                      <xsl:text>друго</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                </fo:block>
              </fo:inline-container>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="46%"/>
      <fo:table-column column-width="29%"/>
      <fo:table-column column-width="25%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell xsl:use-attribute-sets="right-dash">
            <fo:block>
              <fo:inline-container>
                <fo:block>
                  <fo:inline xsl:use-attribute-sets="bold-text">Корисник установе соц. зашт.&#160;</fo:inline>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="../s:ustanova-socijalne-zastite/s:korisnik = 'true'" />
                    <xsl:with-param name="text">
                      <xsl:text>ДА,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="../s:ustanova-socijalne-zastite/s:korisnik = 'false'" />
                    <xsl:with-param name="text">
                      <xsl:text>НЕ</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                </fo:block>
              </fo:inline-container>
            </fo:block>
          </fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text ml-5">Назив и општина седишта</fo:block></fo:table-cell>
          <fo:table-cell>
            <fo:block xsl:use-attribute-sets="underline-text">
              <xsl:choose>
                <xsl:when test="../s:ustanova-socijalne-zastite/s:naziv-opstina-sedista != ''">
                  <xsl:value-of select="../s:ustanova-socijalne-zastite/s:naziv-opstina-sedista"/>
                </xsl:when>
                <xsl:otherwise>
                  &#160;
                </xsl:otherwise>
              </xsl:choose>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

  </xsl:template>

  <xsl:template match="s:izjava-saglasnosti">
    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="65%"/>
      <fo:table-column column-width="35%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell number-columns-spanned="2">
            <fo:block>
              <fo:inline-container>
                <fo:block>
                  <fo:inline xsl:use-attribute-sets="bold-text">Изјављујем да</fo:inline>:
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:izjava = 'true'" />
                    <xsl:with-param name="text">
                      <xsl:text>CAГЛACAH САМ,</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  <xsl:call-template name="checkbox">
                    <xsl:with-param name="test" select="s:izjava = 'false'" />
                    <xsl:with-param name="text">
                      <xsl:text>HИСАМ САГЛАСАН</xsl:text>
                    </xsl:with-param>
                  </xsl:call-template>
                  (означити) са спровођењем
                </fo:block>
              </fo:inline-container>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
        <fo:table-row>
          <fo:table-cell><fo:block>активне/пасивне имунизације (уписати назив имунолошког лека):</fo:block></fo:table-cell>
          <fo:table-cell>
            <fo:block xsl:use-attribute-sets="underline-text">
              <xsl:choose>
                <xsl:when test="s:naziv-imunoloskog-leka != ''">
                  <xsl:value-of select="s:naziv-imunoloskog-leka"/>
                </xsl:when>
                <xsl:otherwise>
                  &#160;
                </xsl:otherwise>
              </xsl:choose>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>
  </xsl:template>

  <xsl:template match ="@datum-izdavanja">
    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="62%"/>
      <fo:table-column column-width="18%"/>
      <fo:table-column column-width="20%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell><fo:block xsl:use-attribute-sets="bold-text">Потпис пацијента или законског заступника пацијента</fo:block></fo:table-cell>
          <fo:table-cell><fo:block /></fo:table-cell>
          <fo:table-cell><fo:block>Датум:</fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row>
          <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text">&#160;</fo:block></fo:table-cell>
          <fo:table-cell><fo:block /></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="centered-text underline-text"><xsl:value-of select="." /></fo:block></fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>
  </xsl:template>

  <xsl:template match ="s:evidencija-o-vakcinaciji">
    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="22%"/>
      <fo:table-column column-width="38%"/>
      <fo:table-column column-width="23%"/>
      <fo:table-column column-width="17%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell><fo:block>Здравствена установа</fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="s:zdravstvena-ustanova" /></fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="ml-5">Вакцинацијски пункт</fo:block></fo:table-cell>
          <fo:table-cell><fo:block xsl:use-attribute-sets="underline-text"><xsl:value-of select="s:vakcinacijski-punkt" /></fo:block></fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

    <fo:table xsl:use-attribute-sets="table">
      <fo:table-column column-width="47%"/>
      <fo:table-column column-width="53%"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell><fo:block>Име, презиме, факсимил и бр. телефона лекара:</fo:block></fo:table-cell>
          <fo:table-cell>
            <fo:block xsl:use-attribute-sets="underline-text">
              <xsl:value-of select="s:lekar/os:ime" />&#160;
              <xsl:value-of select="s:lekar/os:prezime" />,
              <xsl:value-of select="s:lekar/s:faksimil" />,
              <xsl:value-of select="s:lekar/s:broj-telefona" />
              </fo:block>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>

    <xsl:apply-templates select="s:obrazac" />
  </xsl:template>

  <xsl:template match="s:obrazac">
    <fo:block>
      Пре давања вакцине прегледати особу и упознати је са користима и о могућим нежељеним реакцијама после
      вакцинације. Обавезно уписати сваку дату вакцину и све тражене податке у овај образац и податке унети у лични
      картон о извршеним имунизацијама и здравствени картон.
    </fo:block>

    <fo:table xsl:use-attribute-sets="b-table">
      <fo:table-header>
        <fo:table-row>
          <fo:table-cell xsl:use-attribute-sets="b-th"><fo:block>Назив вакцине</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="b-th"><fo:block>Датум давања вакцине (V1 i V2)</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="b-th"><fo:block>Начин давања вакцине</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="b-th"><fo:block>Екстремитет</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="b-th"><fo:block>Серија вакцине (лот)</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="b-th"><fo:block>Произвођач</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="b-th"><fo:block>Нежељена реакција</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="b-th"><fo:block>Потпис лекара</fo:block></fo:table-cell>
        </fo:table-row>
      </fo:table-header>
      <fo:table-body>
        <xsl:for-each select="s:primljene-vakcine">
          <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="b-td">
              <fo:block>
                <xsl:choose>
                  <xsl:when test="s:naziv != ''">
                    <xsl:value-of select="s:naziv"/>
                  </xsl:when>
                  <xsl:otherwise>
                    &#160;
                  </xsl:otherwise>
                </xsl:choose>
              </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="b-td"><fo:block><xsl:value-of select="s:datum-davanja-vakcine"/></fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="b-td"><fo:block><xsl:value-of select="s:nacin-davanja-vakcine"/></fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="b-td">
              <fo:block>
                <xsl:if test="s:ekstremitet/@xsi:nil = 'false'">
                  <xsl:choose>
                    <xsl:when test="s:ekstremitet = 0">
                      ДР
                    </xsl:when>
                    <xsl:otherwise>
                      ЛР
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:if>
              </fo:block>
            </fo:table-cell>

            <fo:table-cell xsl:use-attribute-sets="b-td"><fo:block><xsl:value-of select="s:serija-vakcine"/></fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="b-td"><fo:block><xsl:value-of select="s:proizvodjac"/></fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="b-td"><fo:block><xsl:value-of select="s:nezeljena-reakcija"/></fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="b-td"><fo:block/></fo:table-cell>
          </fo:table-row>    
        </xsl:for-each>

        <fo:table-row>
          <fo:table-cell xsl:use-attribute-sets="b-td" number-columns-spanned="8" text-align="left">
            <fo:block start-indent="5px">  
              Привремене контраиндикације (датум утврђивања и дијагноза):
              <xsl:value-of select="s:privremene-kontraindikacije/s:datum-utvrdjivanja" />,
              <xsl:value-of select="s:privremene-kontraindikacije/s:dijagnoza" />
            </fo:block>
          </fo:table-cell>
        </fo:table-row>

        <fo:table-row>
          <fo:table-cell xsl:use-attribute-sets="b-td" number-columns-spanned="8" text-align="left">
            <fo:block start-indent="5px">  
              Одлука комисије за трајне контраиндикације (ако постоји, уписати Да):
              <xsl:choose>
                <xsl:when test="s:privremene-kontraindikacije/s:odluka-komisije = 'true'">
                  Да
                </xsl:when>
                <xsl:otherwise>
                  Не
                </xsl:otherwise>
              </xsl:choose>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>

      </fo:table-body>
    </fo:table>

    <fo:block>
      <fo:inline xsl:use-attribute-sets="bold-text">Напомена</fo:inline>: Образац се чува као део медицинске документације пацијента.
    </fo:block>
 
  </xsl:template>

  <xsl:template name="checkbox" >
    <xsl:param name="test" />
    <xsl:param name="text" />

    <xsl:choose>
      <xsl:when test="$test">
        <fo:inline xsl:use-attribute-sets="checkbox">&#160;x&#160;</fo:inline>
      </xsl:when>
      <xsl:otherwise>
        <fo:inline xsl:use-attribute-sets="checkbox">&#160;&#160;&#160;&#160;</fo:inline>
      </xsl:otherwise>
    </xsl:choose>
    <fo:inline xsl:use-attribute-sets="checkbox-text"><xsl:value-of select="$text" /></fo:inline>
  </xsl:template>

</xsl:stylesheet>