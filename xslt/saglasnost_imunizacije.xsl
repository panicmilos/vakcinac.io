<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/saglasnost"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
>

<xsl:template match="/x:saglasnost-za-sprovodjenje-preporucene-imunizacije">
  <html>
    <head>
      <meta charset="UTF-8" />

      <style>
        body {
          margin: 8% 5% 15% 5%;
        }

        header {
          margin-bottom: 5%;
        }

        h1, h3 { 
          font-weight:normal;
        }

        h1:not(:first-child) { 
          margin-top: -20px;
        }

        h3:not(:first-child) { 
          margin-top: -20px;
        }

        .flex-container {
          display: flex;
        }
        
        table {
          width: 100%;
          padding-bottom: 1em;
        }

        tr {
          width: 100%;
        }

        td:not(:last-child) {
          padding-right: 5px;
        }

        td:not(:first-child) {
          padding-left: 5px;
        }

        strong.d-tc, span.d-tc  {
          padding-right: 5px;
        }

        .right-dash {
          border-right: 1px solid black;
        }

        .underlined-text {
          display: table-cell;
          border-bottom: 1px solid black;
          width: 100%;
        }

        .new-line-underlined-text {
          display: block;
          border-bottom: 1px solid black;
          width: 100%;
        }

        .centered-text {
          text-align: center;
        }

        .separator {
          border-bottom: 1px dotted black;
        }

        table.obrazac {
          border-collapse: collapse;
        }

        table.obrazac, table.obrazac th, table.obrazac td {
          border: 1px solid black;
          text-align: center;
        }

        .second-row {
          flex-grow: 1;
        }

        .d-table {
          display: table;
          white-space: nowrap;
        }

        .d-tc {
          display: table-cell;
        }

        input[type="checkbox"]:disabled {
          background: white;
          -ms-transform: scale(1.5); /* IE */
          -moz-transform: scale(1.5); /* FF */
          -webkit-transform: scale(1.5); /* Safari and Chrome */
          -o-transform: scale(1.5); /* Opera */
          transform: scale(1.5);
          padding: 10px;        
        }

        .checkboxtext
        {
          /* Checkbox text */
          font-size: 110%;
          display: inline;
          margin-left: 5px;
          margin-right: 5px;
        }

      </style>
    </head>
    <body>

      <header>
        <div class="flex-container">
          <div>
            <h1>САГЛАСНОСТ ЗА СПРОВОЂЕЊЕ</h1>
            <h1>ПРЕПОРУЧЕНЕ ИМУНИЗАЦИЈЕ</h1>
            <h3>(попуњава пацијент)</h3>
          </div>
          <div style="flex-grow: 1;">
            <img style="float: right;" src="https://media.covid19.rs/2020/02/BATUT-logo-300x158.png" />
          </div>
        </div>
      </header>


      <xsl:apply-templates select="x:pacijent" />
      <xsl:apply-templates select="x:izjava-saglasnosti" />

      <p><strong>&#160;Лекар ми је објаснио предности и ризике од спровођења активне/пасивне имунизације наведеним имунолошким леком.</strong></p>

      <xsl:apply-templates select="@datum-izdavanja" />

      <div class="separator"></div>

      <div class="centered-text">
        <h1>ЕВИДЕНЦИЈА О ВАКЦИНАЦИЈИ ПРОТИВ COVID-19</h1>
        <h3>(попуњава здравствени радник)</h3>
      </div>

      <xsl:apply-templates select="x:evidencija-o-vakcinaciji" />
    </body>
  </html>
</xsl:template>

<xsl:template match="x:pacijent">
  <xsl:apply-templates select="x:drzavljanstvo" />
  <xsl:apply-templates select="x:licne-informacije" />
</xsl:template>

<xsl:template match="x:drzavljanstvo">
  <div class="flex-container">
    <div><strong>&#160;Држављанство</strong></div>
    <div class="second-row">
      <table>
        <tr class="d-table">
          <td width="20%" class="right-dash">1) Република Србија </td>
          <td width="80%"><span class="d-tc">ЈМБГ</span><span class="underlined-text"><xsl:value-of select="x:domace/x:jmbg" /></span></td>
        </tr>
      </table>
      <table>
        <tr class="d-table">
          <td width="50%" class="right-dash"><span class="d-tc">2)</span><span class="underlined-text"><xsl:value-of select="x:strano/x:naziv-drzavljanstva" /></span></td>
          <td width="50%"><span class="d-tc">&#160;</span><span class="underlined-text"><xsl:value-of select="x:strano/x:broj-pasosa-ebs" /></span></td>
        </tr>
        <tr class="d-table">
          <td width="50%"><p class="centered-text">(назив страног држављанства)</p></td>
          <td width="50%"><p class="centered-text">(бр. пасоша или ЕБС за стране држављане)</p></td>
        </tr>
      </table>
    </div>
  </div>
</xsl:template>

<xsl:template match="x:licne-informacije">
  <div>
    <table>
      <tr class="d-table">
        <td width="30%" class="right-dash"><strong class="d-tc">Презиме</strong> <span class="underlined-text"><xsl:value-of select="os:prezime" /></span></td>
        <td width="30%" class="right-dash"><strong class="d-tc">Име</strong> <span class="underlined-text"><xsl:value-of select="os:ime" /></span></td>
        <td width="30%"><strong class="d-tc">Име родитеља</strong> <span class="underlined-text"><xsl:value-of select="x:ime-roditelja" /></span></td>
      </tr>
    </table>
    <table>
      <tr class="d-table">
        <td width="13%" class="right-dash">
          <strong>Пол</strong>:
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:pol = 0" />
            <xsl:with-param name="text">
              <xsl:text>М,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:pol = 1" />
            <xsl:with-param name="text">
              <xsl:text>Ж</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
        </td>
        <td width="37%" class="right-dash">
          <strong class="d-tc">Датум рођења</strong>
          <span class="underlined-text"><xsl:value-of select="../x:rodjenje/x:datum-rodjenja" /></span>
        </td>
        <td width="50%"><strong class="d-tc">Место рођења</strong><span class="underlined-text"><xsl:value-of select="../x:rodjenje/x:mesto-rodjenja" /></span></td>
      </tr>
    </table>
    <table>
      <tr class="d-table">
        <td width="60%" class="right-dash"><strong class="d-tc">Адреса (улица и број)</strong><span class="underlined-text"><xsl:value-of select="../x:stanovanje/x:adresa" /></span></td>
        <td width="40%"><strong class="d-tc">Место/Насеље</strong><span class="underlined-text"><xsl:value-of select="../x:stanovanje/x:mesto" /></span></td>
      </tr>
    </table>
    <table>
      <tr class="d-table">
        <td width="60%" class="right-dash"><strong class="d-tc">Општина/Град</strong><span class="underlined-text"><xsl:value-of select="../x:stanovanje/x:opstina" /></span></td>
        <td width="40%"><strong class="d-tc">Тел. фиксни</strong><span class="underlined-text"><xsl:value-of select="../x:kontakt/os:broj-fiksnog-telefona" /></span></td>
      </tr>
    </table>
    <table>
      <tr class="d-table">
        <td width="50%" class="right-dash"><strong class="d-tc">Тел. мобилни</strong><span class="underlined-text"><xsl:value-of select="../x:kontakt/os:broj-mobilnog-telefona" /></span></td>
        <td width="50%"><strong class="d-tc">имејл</strong><span class="underlined-text"><xsl:value-of select="../x:kontakt/os:adresa-elektronske-poste" /></span></td>
      </tr>
    </table>
    <table>
      <tr>
        <td>
          <strong>Радни статус</strong>:
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:radni-status = 0" />
            <xsl:with-param name="text">
              <xsl:text>запослен,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:radni-status = 1" />
            <xsl:with-param name="text">
              <xsl:text>незапослен,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:radni-status = 2" />
            <xsl:with-param name="text">
              <xsl:text>пензионер,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:radni-status = 3" />
            <xsl:with-param name="text">
              <xsl:text>ученик,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:radni-status = 4" />
            <xsl:with-param name="text">
              <xsl:text>студент,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:radni-status = 5" />
            <xsl:with-param name="text">
              <xsl:text>дете</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
        </td>
      </tr>
    </table>
    <table>
      <tr>
        <td>
          <strong>Занимање запосленог</strong>:
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:zanimanje = 0" />
            <xsl:with-param name="text">
              <xsl:text>здравствена заштита,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:zanimanje = 1" />
            <xsl:with-param name="text">
              <xsl:text>социјална заштита,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:zanimanje = 2" />
            <xsl:with-param name="text">
              <xsl:text>просвета,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:zanimanje = 3" />
            <xsl:with-param name="text">
              <xsl:text>МУП,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:zanimanje = 4" />
            <xsl:with-param name="text">
              <xsl:text>Војска РС,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:zanimanje = 5" />
            <xsl:with-param name="text">
              <xsl:text>друго</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
        </td>
      </tr>
    </table>
    <table>
      <tr class="d-table">
        <td width="30%" class="right-dash">
          <strong>Корисник установе соц. зашт.</strong>:
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="../x:ustanova-socijalne-zastite/x:korisnik = 'true'" />
            <xsl:with-param name="text">
              <xsl:text>ДА,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="../x:ustanova-socijalne-zastite/x:korisnik = 'false'" />
            <xsl:with-param name="text">
              <xsl:text>НЕ</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
        </td>
        <td width="60%"><strong class="d-tc">Назив и општина седишта</strong><span class="underlined-text"><xsl:value-of select="../x:ustanova-socijalne-zastite/x:naziv-opstina-sedista" /></span></td>
      </tr>
    </table>
  </div>
</xsl:template>

<xsl:template match="x:izjava-saglasnosti">
  <div>
    <table>
      <tr>
        <td>
          <strong>Изјављујем да</strong>:
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:izjava = 'true'" />
            <xsl:with-param name="text">
              <xsl:text>CAГЛACAH САМ,</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="checkbox">
            <xsl:with-param name="test" select="x:izjava = 'false'" />
            <xsl:with-param name="text">
              <xsl:text>HИСАМ САГЛАСАН</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          (означити) са спровођењем активне/пасивне
        </td>
      </tr>
      <tr class="d-table">
        <td><span class="d-tc">имунизације (уписати назив имунолошког лека):</span><span class="underlined-text"><xsl:value-of select="x:naziv-imunoloskog-leka" /></span></td>
      </tr>
    </table>
  </div>
</xsl:template>

<xsl:template match ="@datum-izdavanja">
  <div>
    <table>
      <tr class="d-table">
        <td width="40%"><strong class="d-tc">Потпис пацијента или законског заступника пацијента:</strong><span class="new-line-underlined-text">&#160;</span></td>
        <td width="40%"/>
        <td width="20%" class="d-tc centered-text">Датум:<span class="new-line-underlined-text"><xsl:value-of select="." /></span></td>
      </tr>
    </table>
  </div>
</xsl:template>

<xsl:template match="x:evidencija-o-vakcinaciji">
  <div>
    <table>
      <tr class="d-table">
        <td width="60%"><span class="d-tc">Здравствена установа</span><span class="underlined-text"><xsl:value-of select="x:zdravstvena-ustanova" /></span></td>
        <td width="40%"><span class="d-tc">Вакцинацијски пункт</span><span class="underlined-text"><xsl:value-of select="x:vakcinacijski-punkt" /></span></td>
      </tr>
    </table>
    <table>
      <tr class="d-table">
        <td>
          <span class="d-tc">Име, презиме, факсимил и бр. телефона лекара:</span>
          <span class="underlined-text">
            <xsl:value-of select="x:lekar/os:ime" />&#160;
            <xsl:value-of select="x:lekar/os:prezime" />,
            <xsl:value-of select="x:lekar/x:faksimil" />,
            <xsl:value-of select="x:lekar/x:broj-telefona" />
          </span>
        </td>
      </tr>
    </table>

    <xsl:apply-templates select="x:obrazac" />
  </div>

</xsl:template>

<xsl:template match="x:obrazac">
  <div>
    <p>Пре давања вакцине прегледати особу и упознати је са користима и о могућим нежељеним реакцијама после
      вакцинације. Обавезно уписати сваку дату вакцину и све тражене податке у овај образац и податке унети у лични
      картон о извршеним имунизацијама и здравствени картон.
    </p>

    <table class="obrazac"> 
      <tr>
        <th>Назив вакцине</th>
        <th>Датум давања вакцине (V1 i V2)</th>
        <th>Начин давања вакцине</th>
        <th>Екстремитет</th>
        <th>Серија вакцине (лот)</th>
        <th>Произвођач</th>
        <th>Нежељена реакција</th>
        <th>Потпис лекара</th>
      </tr>

      <xsl:for-each select="x:primljene-vakcine">
        <tr>
          <td>
            <xsl:choose>
              <xsl:when test="x:naziv != ''">
                <xsl:value-of select="x:naziv"/>
              </xsl:when>
              <xsl:otherwise>
                ㅤ
              </xsl:otherwise>
            </xsl:choose>
          </td>
          <td><xsl:value-of select="x:datum-davanja-vakcine"/></td>
          <td><xsl:value-of select="x:nacin-davanja-vakcine"/></td>
          <td>
            <xsl:if test="x:ekstremitet/@xsi:nil = 'false'">
              <xsl:choose>
                <xsl:when test="x:ekstremitet = 0">
                  <span>ДР</span>
                </xsl:when>
                <xsl:otherwise>
                  <span>ЛР</span>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:if>
          </td>
          <td><xsl:value-of select="x:serija-vakcine"/></td>
          <td><xsl:value-of select="x:proizvodjac"/></td>
          <td><xsl:value-of select="x:nezeljena-reakcija"/></td>
          <td></td>
        </tr>
      </xsl:for-each>

      <tr>
        <td colspan="8" style="text-align: left;">
        Привремене контраиндикације (датум утврђивања и дијагноза):
        <xsl:value-of select="x:privremene-kontraindikacije/x:datum-utvrdjivanja" />,
        <xsl:value-of select="x:privremene-kontraindikacije/x:dijagnoza" />
        </td>
      </tr>

      <tr>
        <td colspan="8" style="text-align: left;">
          Одлука комисије за трајне контраиндикације (ако постоји, уписати Да):
          <xsl:choose>
            <xsl:when test="x:privremene-kontraindikacije/x:odluka-komisije = 'true'">
              <span>Да</span>
            </xsl:when>
            <xsl:otherwise>
              <span>Не</span>
            </xsl:otherwise>
          </xsl:choose>
        </td>
      </tr>

    </table>

    <p><strong>Напомена</strong>: Образац се чува као део медицинске документације пацијента.</p>
  </div>
</xsl:template>

<xsl:template name="checkbox" >
  <xsl:param name="test" />
  <xsl:param name="text" />

  <xsl:choose>
    <xsl:when test="$test">
      <input type="checkbox" disabled="disabled" checked="checked" />
    </xsl:when>
    <xsl:otherwise>
      <input type="checkbox" disabled="disabled" />
    </xsl:otherwise>
  </xsl:choose>
  <span class="checkboxtext"><xsl:value-of select="$text" /></span>
</xsl:template>

</xsl:stylesheet>
