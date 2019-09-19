package com.github.skac112.miro.draw.svg

import com.github.skac112.miro

import scala.xml._
import scala.xml.{Elem => XMLElem}
import com.github.skac112.miro._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.graphics._
import com.github.skac112.miro.attribs._
import com.github.skac112.miro.attribs.colors._
import com.github.skac112.miro.segments._

import scala.math._
import com.github.skac112.vgutils._
import com.github.skac112.vgutils.transform._
import com.github.skac112.miro.Graphic
import com.github.skac112.miro.attribs.{Fill, Stroke}
import com.github.skac112.miro.attribs.colors.MiroColor
import com.github.skac112.miro.graphics.{Circle, GenericPath, Quad, Rect, Square, Triangle}
import com.github.skac112.miro.segments.{Arc, Cubic, HSeg, LineSeg, Quadratic, Segment, SmoothCubic, SmoothQuadratic, VSeg}
// import scalaz._

/**
 * Zapisuje grafiki do SVG.
 */
class Draw {
  private def drawEl(g: PosGraphic[Graphic]): Option[XMLElem] = (g match {
    case (com.github.skac112.miro.graphics.Group(elements, ga), pt) => Some(<g transform={ s"translate(${pt.x}, ${pt.y})" }>{ for (el <- elements;
                                                                                                                xml_elem <- drawEl(el))
                                                     yield xml_elem }</g>)
    case (miro.graphics.Line(e, _), pt) => Some(<line x1={ pt.x.toString } y1={ pt.y.toString } x2={ (pt + e).x.toString } y2={ (pt + e).y.toString }/>)
    case (Circle(r, _), pt) => Some(<circle r={ r.toString } cx={ pt.x.toString } cy={ pt.y.toString }/>)
    case (r @ Rect(w, h, rot, _), pt) => {
      val tl = r.tl + pt
      // conversion to degrees
      val rot_rad = rot * 180 / Pi
      val rot_str = s"rotate($rot_rad, ${tl.x}, ${tl.y})"
      Some(<rect x={ tl.x.toString } y={ tl.y.toString} width={ w.toString } height={ h.toString } transform={ rot_str }/>)
    }
    case (s @ Square(size, rot, _), pt) => {
      val tl = s.tl + pt
      // conversion to degrees
      val rot_rad = rot * 180 / Pi
      val rot_str = s"rotate($rot_rad, ${tl.x}, ${tl.y})"
      Some(<rect x={ tl.x.toString } y={ tl.y.toString} width={ size.toString } height={ size.toString } transform={ rot_str }/>)
    }
    // case (Path(subpaths, _), pt) => Some(<path d="{ pathDAttr(subpaths, pt) }"/>)
    case (p: GenericPath, pt) => Some(<path d={ pathDAttr(p.subpaths, pt) }/>)
    case (t @ Triangle(p2, p3, ga), pt) => Some(<polygon points={ pointsAttr(t.points, pt)}/>)
    case (q @ Quad(p2, p3, p4, ga), pt) => Some(<polygon points={ pointsAttr(q.points, pt)}/>)
    case _ => None
  }) map {xml_elem: XMLElem => checkAddGenAttrs(xml_elem , g._1.genericAttribs)}

  def draw(g: PosGraphic[Graphic]) = {
    val b = bounds(g)
    val b_minx = b.tl.x
    val b_miny = b.tl.y
    val b_w = b.w
    val b_h = b.h
    val view_box = s"$b_minx $b_miny $b_w $b_h"
    <svg xmlns:svg="http://www.w3.org/2000/svg" xmlns="http://www.w3.org/2000/svg" viewBox={ view_box } preserveAspectRatio="xMidYMid meet">{ drawEl(g).get }</svg>
  }

  def saveToFile(g: PosGraphic[Graphic], filename: String): Unit =
    scala.xml.XML.save(filename, draw(g), "UTF-8", true, null)

  def strDoc(g: PosGraphic[Graphic]): String = {
    val elem = draw(g)
    val writer = new java.io.StringWriter()
    scala.xml.XML.write(writer, elem, "UTF-8", true, null)
    writer.toString
  }

  def strEl(g: PosGraphic[Graphic]): Option[String] = drawEl(g) map {g_svg =>
    val writer = new java.io.StringWriter()
    scala.xml.XML.write(writer, g_svg, "UTF-8", true, null)
    writer.toString
  }

  private def genAttrsToStr(ga: GenericAttribs) =
    (strokeToStr(ga.strokeO) + fillToStr(ga.fillO) + transfToStr(ga.transformO)).trim

  private def strokeToStr(strokeO: Option[Stroke]) = strokeO match {
    case Some(stroke) => stroke.color.toString + " " + stroke.width.toString
    case _ => ""
  }

  private def fillToStr(fillO: Option[Fill]) = fillO match {
    case Some(color: MiroColor) => color.toString
    case _ => ""
  }

  private def transfToStr(transformO: Option[Affine]) = transformO match {
    case Some(Rotation(ang, pivot)) => s"rotate($ang, ${pivot.x}, ${pivot.y})"
    case _ => ""
  }

  def checkAddFillAttrs(elem: XMLElem, fillO: Option[Fill]) = fillO match {
    case Some(color: MiroColor) => elem % new UnprefixedAttribute("fill", color.toString, Null)
    case _ => elem
  }

  def checkAddStrokeAttr(elem: XMLElem, strokeO: Option[Stroke]) = strokeO match {
    case Some(Stroke(col, _)) => elem % new UnprefixedAttribute("stroke",
     col.toString, Null)
    case _ => elem
  }

  def checkAddStrokeWidthAttr(elem: XMLElem, strokeO: Option[Stroke]) = strokeO match {
    case Some(Stroke(_, width)) => elem % new UnprefixedAttribute("stroke-width",
     width.toString, Null)
    case _ => elem
  }

  def checkAddFillOpacityAttr(elem: XMLElem, fillO: Option[Fill]) = fillO match {
    case Some(MiroColor(_, _, _, 1.0)) => elem
    case Some(MiroColor(_, _, _, op)) => elem % new UnprefixedAttribute("fill-opacity", op.toString, Null)
    case _ => elem
  }

  def checkAddGenAttrs(elem: XMLElem, genAttrs: GenericAttribs): XMLElem = {
    val fun = {e: XMLElem => checkAddFillAttrs(e, genAttrs.fillO)} compose
     {e: XMLElem => checkAddStrokeAttr(e, genAttrs.strokeO)} compose
     {e: XMLElem => checkAddStrokeWidthAttr(e, genAttrs.strokeO)} compose
     {e: XMLElem => checkAddFillOpacityAttr(e, genAttrs.fillO)}

    fun(elem)
  }

  private def addChild(elem: XMLElem, childNode: scala.xml.Node): XMLElem = {
    XMLElem(elem.prefix, elem.label, elem.attributes, elem.scope, elem.child ++ childNode : _*)
  }

  /**
   * Zwraca wartosc atrybutu "d" sciezki
   */
  private def pathDAttr(subpaths: Subpaths, pt: Point): String =
    (subpaths.foldLeft("") {(curr: String, segs: Subpath) => {
      curr + subpathToDStr(segs, pt) + " "
    }}).trim

  private def pointsAttr(points: Points, pt: Point): String =
   (ori +: points) map {_ + pt} map {pt => s"${pt.x},${pt.y}"} reduceLeft {_ + " " + _}

  // TODO! uzupelnic
  private def subpathToDStr(subpath: Subpath, pt: Point): String = {
    val start_pt = subpath._2 + pt
    subpath._1.segments.foldLeft(s"M ${start_pt.x},${start_pt.y}") {(curr: String, seg: Segment) => {
      curr + " " + segToDStr(seg)
    }} + " z"
  }

  private def segToDStr(seg: Segment): String = seg match {
    case LineSeg(end) => s"l ${end.x} ${end.y}"
    case HSeg(len) => s"h $len"
    case VSeg(len) => s"v $len"
    case Arc(rx, ry, rotation, laf, sf, end) => s"a $rx $ry ${rotation.value} ${bool2Bit(laf)} ${bool2Bit(sf)} ${end.x},${end.y}"
    case Quadratic(cp, end) => s"q ${cp.x},${cp.y} ${end.x},${end.y}"
    case SmoothQuadratic(end) => s"t ${end.x},${end.y}"
    case Cubic(cp1, cp2, end) => s"c ${cp1.x},${cp1.y} ${cp2.x},${cp2.y} ${end.x},${end.y}"
    case SmoothCubic(cp2, end) => s"s ${cp2.x},${cp2.y} ${end.x},${end.y}"
  }

  private def bool2Bit(b: Boolean): String = b match {
    case true => "1"
    case _ => "0"
  }
}
