<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format"
  xmlns:i="https://www.vakcinac-io.rs/izvestaj"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
  version="2.0"
>
  <xsl:import href="./styles/izvestaj_o_imunizaciji.xsl"/>

  <xsl:variable name="fo:layout-master-set">
    <fo:layout-master-set>
      <fo:simple-page-master master-name="izvestaj-o-imunizaciji-page">
        <fo:region-body margin="1in" />
      </fo:simple-page-master>
    </fo:layout-master-set>
  </xsl:variable>

  <xsl:template match="i:izvestaj-o-imunizaciji">
    <fo:root font-family="Times New Roman">
      <xsl:copy-of select="$fo:layout-master-set" />

      <fo:page-sequence master-reference="izvestaj-o-imunizaciji-page">
        <fo:flow flow-name="xsl-region-body">
          <fo:block xsl:use-attribute-sets="h3 div">
            Извештај о имунизацији
          </fo:block>

          <fo:block xsl:use-attribute-sets="div">
            Извештај се односи на период од
            <fo:inline xsl:use-attribute-sets="bold-text"><xsl:value-of select="@od"/></fo:inline>
            до
            <fo:inline xsl:use-attribute-sets="bold-text"><xsl:value-of select="@do"/></fo:inline>
          </fo:block>

          <fo:block xsl:use-attribute-sets="div">
            <xsl:apply-templates select="i:statistika-zahteva"/>
          </fo:block>

          <fo:block xsl:use-attribute-sets="div">
            <xsl:apply-templates select="i:statistika-doza"/>
          </fo:block>

          <fo:block xsl:use-attribute-sets="div">
            <xsl:apply-templates select="i:statistika-proizvodjaca"/>
          </fo:block>


          <fo:table xsl:use-attribute-sets="issuing-date">
            <fo:table-column column-width="30%" />
            <fo:table-column column-width="30%" />
            <fo:table-column column-width="40%" />
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                    Датум издавања: <fo:inline xsl:use-attribute-sets="underline-text"><xsl:value-of select="@izdato"/></fo:inline> године
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

  <xsl:template match="i:statistika-zahteva">
    <fo:block>
      У напоменутом временском интервалу је: 
    </fo:block>
    <fo:list-block>
      <fo:list-item>
        <fo:list-item-label xsl:use-attribute-sets="list-item">
          <fo:block>&#x2022;</fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            поднето <fo:inline xsl:use-attribute-sets="bold-text"><xsl:value-of select="i:podneto" /></fo:inline> докумената о интересовању за имунизацију.
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
      <fo:list-item>
        <fo:list-item-label xsl:use-attribute-sets="list-item">
          <fo:block>&#x2022;</fo:block>
        </fo:list-item-label>
        <fo:list-item-body xsl:use-attribute-sets="list-value">
          <fo:block>
            примљено <fo:inline xsl:use-attribute-sets="bold-text"><xsl:value-of select="i:primljeno" /></fo:inline> захтева за дигитални зелени сертификат, од којих је <fo:inline xsl:use-attribute-sets="bold-text"><xsl:value-of select="i:izdato" /></fo:inline> издато.
          </fo:block>
        </fo:list-item-body>
      </fo:list-item>
    </fo:list-block>
  </xsl:template>

  <xsl:template match="i:statistika-doza">
    <fo:block xsl:use-attribute-sets="before-table-text">
      Дато је <fo:inline xsl:use-attribute-sets="bold-text"><xsl:value-of select="i:ukupno-izdatih-doza" /></fo:inline> доза вакцине против COVID-19 вируса у следећој количини:
    </fo:block>

    <fo:table xsl:use-attribute-sets="table">
      <fo:table-header>
        <fo:table-row>
          <fo:table-cell xsl:use-attribute-sets="th"><fo:block>Редни број дозе</fo:block></fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="th"><fo:block>Број датих доза</fo:block></fo:table-cell>
        </fo:table-row>
      </fo:table-header>
      <fo:table-body>
        <xsl:for-each select="i:izdate-doze/i:doza">
          <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="td"><fo:block xsl:use-attribute-sets="bold-text"><xsl:value-of select="@redni-broj"/></fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="td"><fo:block><xsl:value-of select="."/></fo:block></fo:table-cell>
          </fo:table-row>
        </xsl:for-each>
      </fo:table-body>
    </fo:table>
  </xsl:template>

  <xsl:template match="i:statistika-proizvodjaca">
    <fo:block>
      Расподела по произвођачима је:
    </fo:block>

    <fo:list-block>
      <xsl:for-each select="i:proizvodjac">
        <fo:list-item>
          <fo:list-item-label xsl:use-attribute-sets="list-item">
            <fo:block>&#x2022;</fo:block>
          </fo:list-item-label>
          <fo:list-item-body xsl:use-attribute-sets="list-value">
            <fo:block>
              <fo:inline xsl:use-attribute-sets="bold-text"><xsl:value-of select="i:naziv" /></fo:inline> - <fo:inline xsl:use-attribute-sets="bold-text"><xsl:value-of select="i:broj-doza" /></fo:inline> доза; 
            </fo:block>
          </fo:list-item-body>
        </fo:list-item>
      </xsl:for-each>
    </fo:list-block>


  </xsl:template>
</xsl:stylesheet>