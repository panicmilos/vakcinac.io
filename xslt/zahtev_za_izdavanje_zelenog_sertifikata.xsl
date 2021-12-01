<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:x="https://www.vakcinac-io.rs/xsd/zahtev"
  xmlns:os="https://www.vakcinac-io.rs/xsd/osnovna-sema"
>

<xsl:template match="/x:zahtev-za-izdavanje-zelenog-sertifikata">
  <html>
    <head>

    </head>
    <body>
      <xsl:value-of select="x:podnosilac-zahteva/os:ime" />
    </body>
  </html>
</xsl:template>

</xsl:stylesheet>
