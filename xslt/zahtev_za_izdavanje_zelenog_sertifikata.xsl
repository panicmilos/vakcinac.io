<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/zahtev"
  xmlns:os="https://www.vakcinac-io.rs/osnovna-sema"
>

<xsl:template match="/x:zahtev-za-izdavanje-zelenog-sertifikata">
  <html>
    <head>
      <style>
        body {
          margin: 8% 14% 15% 14%;
        }

        h2 {
          text-align: center;
          padding-top: 10%;
          margin-bottom: 0;
          letter-spacing: 6px;
          text-transform: uppercase;
        }

        h3 {
          text-align: center;
          margin-top: 0;
        }

        .inline {
          display: inline-block;
        }

        .intro-text {
          text-indent: 50px;
          font-weight: 510;
        }

        .content {
          margin-top: 50px;
        }

        .form-content {
          margin-top: 25px;
          display: flex;
          flex-wrap: wrap;
        }

        .form-content > div {
          flex: 100%;
          display: flex;
          margin-bottom: 5px;
        }

        .form-content > div > .dots-underline {
          flex: 50%;
          margin-left: 5px;
        }

        .form-content > div > .dots-reason {
          flex: 50%;
        }

        .dots-underline, .dots-reason {
          border-bottom: 2px dotted;
        }

        .underline {
          border-bottom: 1px solid;
        }

        .mtop {
          margin-top: 5%;
        }

        .hint {
          display: flex;
          justify-content: center;
          width: 100%;
          font-size: 13px;
        }

        .end-container {
          display: flex;
          flex-wrap: wrap;
          margin-top: 10%;
        }

        .end-container > div {
          flex: 50%;
        }

        .signature {
          display: flex;
          text-align: center;
          justify-content: center;
          padding-top: 12%;
        }

        .signature-label {
          width: 60%;
          border-top: 1px solid;
          font-size: 14px;
        }
      </style>
    </head>
    <body>
      <h2>ЗАХТЕВ</h2>
      <h3>за издавање дигиталног зеленог сертификата</h3>
      <p class="intro-text">У складу са одредбом Републике Србије о издавању дигиталног зеленог
сертификата као потврде о извршеној вакцинацији против COVID-19, резултатима
тестирања на заразну болест SARS-CoV-2 или опоравку од болести COVID-19,
подносим захтев за издавање дигиталног зеленог сертификата.</p>
      <div class="content">
        <p>Подносилац захтева:</p>

        <div class="form-content">
          <div>
            <span>Име и презиме:</span> <span class="dots-underline"><xsl:apply-templates select="x:podnosilac-zahteva" /></span>
          </div>
          <div>
            <span>Датум рођења:</span> <span class="dots-underline"><xsl:apply-templates select="x:podnosilac-zahteva/x:datum-rođenja" /></span>
          </div>
          <div>
            <span>Пол:</span> <span class="dots-underline"><xsl:apply-templates select="x:podnosilac-zahteva/x:pol" /></span>
          </div>
          <div>
            <span>Јединствени матични број грађанина:</span> <span class="dots-underline"><xsl:value-of select="x:podnosilac-zahteva/os:jmbg" /></span>
          </div>
          <div>
            <span>Број пасоша:</span> <span class="dots-underline"><xsl:value-of select="x:podnosilac-zahteva/x:broj-pasosa" /></span>
          </div>

          <p>Разлог за подношење захтева:</p>
          <div>
            <span class="dots-reason"><xsl:value-of select="x:razlog" /></span>
          </div>
          <span class="hint">(навести што прецизнији разлога за подношење захтева за издавање дигиталног пасоша)</span>
        </div>
      </div>

      <div class="end-container">
        <div>
          У <span class="underline"><xsl:value-of select="@mesto" /></span>,
          <p class="mtop">дана <span class="underline"><xsl:value-of select="@dan" />.</span> године</p>
        </div>
        <div class="signature">
          <p class="signature-label">Потпис</p>
        </div>
      </div>
    </body>
  </html>
</xsl:template>

<xsl:template match="x:podnosilac-zahteva/x:pol">
    <xsl:choose>
      <xsl:when test="current() = 0">
        <span>М</span>
      </xsl:when>
      <xsl:otherwise>
        <span>Ж</span>
      </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template match="x:podnosilac-zahteva">
  <xsl:value-of select="os:ime" />&#160;
  <xsl:value-of select="os:prezime" />
</xsl:template>

<xsl:template match="x:podnosilac-zahteva/x:datum-rođenja">
  <xsl:value-of select="os:dan" />.
  <xsl:value-of select="os:mesec" />.
  <xsl:value-of select="os:godina" />.
</xsl:template>

</xsl:stylesheet>
