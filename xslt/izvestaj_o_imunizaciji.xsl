<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/izvestaj"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
>

<xsl:template match="/x:izvestaj-o-imunizaciji">
  <html>
    <head>
      <style>
        body {
          margin: 8% 12% 15% 12%;
        }

        h3 {
          text-align: center;
          padding-bottom: 5%;
        }

        div {
          padding-bottom: 3%;
        }

        table, td, th {
          border: 1px solid black;
          text-align: center;
        }

        table {
          width: 100%;
          border-collapse: collapse;
        }

        .datum-izdavanja {
          padding-top: 5%;
        }
      </style>
    </head>
    <body>
      <h3>Извештај о имунизацији</h3>

      <div>
        <p>
          Извештај се односи на период од
          <strong><xsl:value-of select="@od"/></strong>
          до 
          <strong><xsl:value-of select="@do"/></strong>
        </p>
      </div>

      <div>
        <xsl:apply-templates select="x:statistika-zahteva" />
      </div>
      <div>
        <xsl:apply-templates select="x:statistika-doza" />
      </div>
      <div>
        <xsl:apply-templates select="x:statistika-proizvodjaca" />
      </div>
    
      <div class="datum-izdavanja">
        Датум издавања: <u><xsl:value-of select="@izdato"/></u> године
      </div>
    </body>
  </html>
</xsl:template>

<xsl:template match="x:statistika-zahteva">
  <p>
    У напоменутом временском интервалу је: 
  </p>
  <ul>
    <li>поднето <strong><xsl:value-of select="x:podneto" /></strong> докумената о интересовању за имунизацију. </li>
    <li>примљено <strong><xsl:value-of select="x:primljeno" /></strong> захтева за дигитални зелени сертификат, од којих је <strong><xsl:value-of select="x:izdato" /></strong> издато. </li>
  </ul>
</xsl:template>

<xsl:template match="x:statistika-doza">
  <p>
    Дато је <strong><xsl:value-of select="x:ukupno-izdatih-doza" /></strong> доза вакцине против COVID-19 вируса у следећој количини:
  </p>
  <table>
    <tr>
      <th>Редни број дозе</th>
      <th>Број датих доза</th>
    </tr>
  <xsl:for-each select="x:izdate-doze/x:doza">
    <tr>
      <td><strong><xsl:value-of select="@redni-broj"/></strong></td>
      <td><xsl:value-of select="."/></td>
    </tr>
  </xsl:for-each>
  </table>
</xsl:template>

<xsl:template match="x:statistika-proizvodjaca">
  <p>
    Расподела по произвођачима је:
  </p>
  <ul>
  <xsl:for-each select="x:proizvodjac">
    <li>
      <strong><xsl:value-of select="x:naziv" /></strong> - <strong><xsl:value-of select="x:broj-doza" /></strong> доза; 
    </li>
  </xsl:for-each>
  </ul>
</xsl:template>

</xsl:stylesheet>
