<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fox="http://xmlgraphics.apache.org/fop/extensions">

  <xsl:attribute-set name="h3">
    <xsl:attribute name="text-align">center</xsl:attribute>
    <xsl:attribute name="font-size">15px</xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
    <xsl:attribute name="padding-bottom">2.5%</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="div">
    <xsl:attribute name="padding-bottom">1.5%</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="intro-text">
    <xsl:attribute name="start-indent">50px</xsl:attribute>
    <xsl:attribute name="font-weight">510</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="dots-underline">
    <xsl:attribute name="border-bottom">2px dotted</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="underline">
    <xsl:attribute name="border-bottom">1px solid</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="options">
    <xsl:attribute name="padding-left">10%</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="option">
    <xsl:attribute name="width">fit-content</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="chosen-option">
    <xsl:attribute name="padding">2 5 2 5</xsl:attribute>
    <xsl:attribute name="border">2px solid</xsl:attribute>
    <xsl:attribute name="fox:border-radius">20pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="end-container"></xsl:attribute-set>

  <xsl:attribute-set name="signature">
    <xsl:attribute name="text-align">center</xsl:attribute>
    <xsl:attribute name="padding-top">0.5%</xsl:attribute>
    <xsl:attribute name="border-top">1px solid</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="signature-label">
    <xsl:attribute name="font-size">14px</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="blood">
    <xsl:attribute name="margin-top">2%</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="margin-top">
    <xsl:attribute name="margin-top">3%</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="list-block">
    <xsl:attribute name="margin-top">1%</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="list-value">
    <xsl:attribute name="start-indent">8%</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="list-item-block">
    <xsl:attribute name="margin-bottom">1%</xsl:attribute>
  </xsl:attribute-set>

</xsl:stylesheet>