package skac.miro.draw.svg

import scala.xml._
import scala.xml.{Elem => XMLElem}
import skac.miro._
import skac.miro.Graphic._
import skac.miro.graphics._
import skac.miro.attribs._
import skac.miro.attribs.colors._
import skac.miro.transform._
import skac.miro.segments._
// import scalaz._

/**
 * Zapisuje grafiki do SVG.
 */
class Draw {
  private def drawEl(g: PosGraphic): Option[XMLElem] = (g match {
    case (skac.miro.Group(elements, ga), pt) => Some(<g>{ for (el <- elements;
                                                     xml_elem <- drawEl(el))
                                                     yield xml_elem }</g>)
    case (skac.miro.graphics.Line(e, _), pt) => Some(<line x1={ pt.x.toString } y1={ pt.y.toString } x2={ (pt + e).x.toString } y2={ (pt + e).y.toString }/>)
    case (Circle(r, _), pt) => Some(<circle r={ r.toString } cx={ pt.x.toString } cy={ pt.y.toString }/>)
    case (r @ Rect(w, h, rot, _), pt) => {
      val tl = r.tl + pt
      val rot_str = s"rotate($rot, ${tl.x}, ${tl.y})"
      Some(<rect x={ tl.x.toString } y={ tl.y.toString} width={ w.toString } height={ h.toString } transform={ rot_str }/>)
    }
    // case (Path(subpaths, _), pt) => Some(<path d="{ pathDAttr(subpaths, pt) }"/>)
    case (p: GenericPath, pt) => Some(<path d={ pathDAttr(p.subpaths, pt) }/>)
    case _ => None
  }) map {xml_elem: XMLElem => checkAddGenAttrs(xml_elem , g._1.genericAttribs)}

  def draw(g: PosGraphic) = <svg xmlns:svg="http://www.w3.org/2000/svg" xmlns="http://www.w3.org/2000/svg">{ drawEl(g).get }</svg>

  def saveToFile(g: PosGraphic, filename: String): Unit =
    scala.xml.XML.save(filename, draw(g), "UTF-8", true, null)

  private def genAttrsToStr(ga: GenericAttribs) =
    (strokeToStr(ga.strokeO) + fillToStr(ga.fillO) + transfToStr(ga.transformO)).trim

  private def strokeToStr(strokeO: Option[Stroke]) = strokeO match {
    case Some(stroke) => stroke.color.toString + " " + stroke.width.toString
    case _ => ""
  }

  private def fillToStr(fillO: Option[Fill]) = fillO match {
    case Some(color: Color) => color.toString
    case _ => ""
  }

  private def transfToStr(transformO: Option[Affine]) = transformO match {
    case Some(Rotation(ang, pivot)) => s"rotate($ang, ${pivot.x}, ${pivot.y})"
    case _ => ""
  }

  def checkAddFillAttrs(elem: XMLElem, fillO: Option[Fill]) = fillO match {
    case Some(color: Color) => elem % new UnprefixedAttribute("fill", color.toString, Null)
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

  def checkAddGenAttrs(elem: XMLElem, genAttrs: GenericAttribs): XMLElem = {
    val fun = {e: XMLElem => checkAddFillAttrs(e, genAttrs.fillO)} compose
     {e: XMLElem => checkAddStrokeAttr(e, genAttrs.strokeO)} compose
     {e: XMLElem => checkAddStrokeWidthAttr(e, genAttrs.strokeO)}

    fun(elem)
  }

  private def addChild(elem: XMLElem, childNode: scala.xml.Node): XMLElem = {
    XMLElem(elem.prefix, elem.label, elem.attributes, elem.scope, elem.child ++ childNode : _*)
  }

  /**
   * Zwraca wartosc atrybutu "g" sciezki
   */
  private def pathDAttr(subpaths: Seq[GenericSegCurve], pt: Point): String =
    (subpaths.foldLeft("") {(curr: String, segs: GenericSegCurve) => {
      curr + subpathToDStr(segs, pt) + " "
    }}).trim

  // TODO! uzupelnic
  private def subpathToDStr(subpath: GenericSegCurve, pt: Point): String =
    subpath.segments.foldLeft(s"M ${pt.x}, ${pt.y}") {(curr: String, seg: Segment) => {
      curr + " " + segToDStr(seg, pt)
    }} + " C"

  private def segToDStr(seg: Segment, pt: Point): String = seg match {
    case LineSeg(end) => s"l ${seg.end.x} ${seg.end.y}"
    case HSeg(len) => s"h $len"
    case VSeg(len) => s"v $len"
    case Arc(rx, ry, rotation, laf, sf, end) => s"a $rx $ry ${rotation.value} ${bool2Bit(laf)} ${bool2Bit(sf)} ${end.x} ${end.y}"
    case Quadratic(cp, end) => s"q ${cp.x} ${cp.y} ${end.x} ${end.y}"
    case SmoothQuadratic(end) => s"t ${end.x} ${end.y}"
    case Cubic(cp1, cp2, end) => s"c ${cp1.x} ${cp1.y} ${cp2.x} ${cp2.y} ${end.x} ${end.y}"
    case SmoothCubic(cp2, end) => s"s ${cp2.x} ${cp2.y} ${end.x} ${end.y}"
  }

  private def bool2Bit(b: Boolean): String = b match {
    case true => "1"
    case _ => "0"
  }
}
