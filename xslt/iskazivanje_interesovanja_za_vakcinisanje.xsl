<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/xsd/interesovanje"
  xmlns:os="https://www.vakcinac-io.rs/xsd/osnovna-sema"
>

<xsl:template match="/x:izjava-interesovanja-za-vakcinisanje">
  <html>
    <head>
      <style>
        body {
          margin: 8% 14% 15% 14%;
        }

        h3 {
          text-align: center;
          margin-top: 0;
        }

        .intro-text {
          text-indent: 50px;
          font-weight: 510;
        }

        .content {
          margin-top: 20px;
        }

        .form-content {
          margin-top: 25px;
          display: flex;
          flex-wrap: wrap;
        }

        .form-content > div {
          flex: 100%;
          display: flex;
          flex-wrap: wrap;
          margin-bottom: 5px;
        }

        .form-content > div > p {
          flex: 100%;
          margin-bottom: 5px;
        }

        .form-content > div > .dots-underline {
          flex: 100%;
          margin-bottom: 15px;
        }

        .dots-underline {
          border-bottom: 2px dotted;
        }

        .underline {
          border-bottom: 1px solid;
        }

        .options {
          padding-left: 10%;
        }

        .option {
          width: fit-content;
        }

        .chosen-option {
		  padding: 2 5 2 5;
          border: 2px solid;
          border-radius: 15px;
        }

        .end-container {
          display: flex;
          flex-wrap: wrap;
          margin-top: 25%;
        }

        .end-container > div {
          flex: 50%;
        }

        .signature {
          display: flex;
          text-align: center;
          justify-content: flex-end;
          padding-top: 2%;
        }

        .signature-label {
          width: 80%;
          border-top: 1px solid;
          font-size: 14px;
        }

        .blood {
          margin-top: 5%;
        }
      </style>
    </head>
    <body>
      <h3>Исказивање интересовања за вакцинисање против COVID-19</h3>
      <div class="content">
        <p>Одаберите опцију:</p>
        <div class="options">
          <xsl:apply-templates select="x:podnosilac-izjave/x:podnosilac/x:drzavljanstvo" />
        </div>
        <div class="form-content">
          <div>
            <p>ЈМБГ:</p> <span class="dots-underline"><xsl:value-of select="x:podnosilac-izjave/x:podnosilac/os:jmbg" /></span>
          </div>
          <div>
            <p>Име:</p> <span class="dots-underline"><xsl:value-of select="x:podnosilac-izjave/x:podnosilac/os:ime" /></span>
          </div>
          <div>
            <p>Презиме:</p> <span class="dots-underline"><xsl:value-of select="x:podnosilac-izjave/x:podnosilac/os:prezime" /></span>
          </div>
          <div>
            <p>Адреса електронске поште:</p> <span class="dots-underline"><xsl:value-of select="x:podnosilac-izjave/x:kontakt/os:adresa-elektronske-poste" /></span>
          </div>
          <div>
            <p>Број мобилног телефона (навести број у формату 06X..... без размака и цртица):</p> <span class="dots-underline"><xsl:value-of select="x:podnosilac-izjave/x:kontakt/os:broj-mobilnog-telefona" /></span>
          </div>
          <div>
            <p>Број фиксног телефона (навести број у формату нпр. 011..... без размака и цртица):</p> <span class="dots-underline"><xsl:value-of select="x:podnosilac-izjave/x:kontakt/os:broj-fiksnog-telefona" /></span>
          </div>
          <div>
            <p>Одаберите локацију где желите да примите вакцину (унесите општину):</p> <span class="dots-underline"><xsl:value-of select="x:informacije-o-primanju-vakcine/x:opstina" /></span>
          </div>
        </div>

        <p>Исказујем интересовање да примим искључиво вакцину следећих произвођача за
који Агенција за лекове и медицинска средства потврди безбедност, ефикасност и
квалитет и изда дозволу за употребу лека:</p>
        <div class="options">
          <xsl:apply-templates select="x:informacije-o-primanju-vakcine/x:proizvodjaci" />
        </div>
        <p class="blood">Да ли сте добровољни давалац крви?</p>
        <div class="options">
          <xsl:apply-templates select="x:podnosilac-izjave/x:dobrovoljan-davalac-krvi" />
        </div>
      </div>

      <div class="end-container">
        <div>
          <p>дана <span class="underline"><xsl:value-of select="@dan" /></span> године</p>
        </div>
        <div class="signature">
          <p class="signature-label">Потпис</p>
        </div>
      </div>
    </body>
  </html>
</xsl:template>

<xsl:template match="x:podnosilac-izjave/x:podnosilac/x:drzavljanstvo">
    <xsl:choose>
      <xsl:when test="current() = 0">
        <p class="chosen-option option">Држављанин Републике Србије</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Држављанин Републике Србије</p>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="current() = 1">
        <p class="chosen-option option">Страни држављанин са боравком у РС</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Страни држављанин са боравком у РС</p>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="current() = 2">
        <p class="chosen-option option">Страни држављанин без боравка у РС</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Страни држављанин без боравка у РС</p>
      </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template match="x:informacije-o-primanju-vakcine/x:proizvodjaci">
    <xsl:choose>
      <xsl:when test="boolean(x:proizvodjac = 0)">
        <p class="chosen-option option">Pfizer-BioNTech</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Pfizer-BioNTech</p>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="boolean(x:proizvodjac = 1)">
        <p class="chosen-option option">Sputnik V (Gamaleya истраживачки центар)</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Sputnik V (Gamaleya истраживачки центар)</p>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="boolean(x:proizvodjac = 2)">
        <p class="chosen-option option">Sinopharm</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Sinopharm</p>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="boolean(x:proizvodjac = 3)">
        <p class="chosen-option option">AstraZeneca</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">AstraZeneca</p>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="boolean(x:proizvodjac = 4)">
        <p class="chosen-option option">Moderna</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Moderna</p>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="boolean(x:proizvodjac = 5)">
        <p class="chosen-option option">Било која</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Било која</p>
      </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template match="x:podnosilac-izjave/x:dobrovoljan-davalac-krvi">
    <xsl:choose>
      <xsl:when test="current() = 'true'">
        <p class="chosen-option option">Да</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Да</p>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="current() = 'false'">
        <p class="chosen-option option">Не</p>
      </xsl:when>
      <xsl:otherwise>
        <p class="option">Не</p>
      </xsl:otherwise>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet>
