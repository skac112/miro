package com.github.skac112.miro.draw.scalajsreact

import com.github.skac112.miro
import com.github.skac112.miro.Graphic.GenericAttribs
import com.github.skac112.miro.attribs.{Fill, Stroke}
import com.github.skac112.miro.attribs.colors.MiroColor
import com.github.skac112.miro.{Graphic, PosGraphic}
import com.github.skac112.miro.graphics.{Circle, GenericPath, Quad, Rect, Square, Triangle}
import japgolly.scalajs.react.vdom.{VdomArray, svg_<^}
import japgolly.scalajs.react.vdom.all._
import scala.math._
import com.github.skac112.miro.SvgUtils._

class Draw {
  private def drawEl(g: PosGraphic[Graphic[_], _]): Option[VdomArray] = (g match {
    case (com.github.skac112.miro.graphics.Group(elements, _, _), pt) => {
      val elems = elements flatten drawEl _
      Some(svg_<^.<.g(svg_<^.^.transform := s"translate(${pt.x}, ${pt.y})")(elems: _*))
    }

    case (miro.graphics.Line(e, _, _), pt) => Some(svg_<^.<.line(
      svg_<^.^.x1 :=  pt.x.toString,
      svg_<^.^.y1 := pt.y.toString,
      svg_<^.^.x2 := (pt + e).x.toString,
      svg_<^.^.y2 := (pt + e).y.toString))

    case (Circle(r, _, _), pt) => Some(svg_<^.<.circle(
      svg_<^.^.r := r.toString,
      svg_<^.^.cx := pt.x.toString,
      svg_<^.^.cy := pt.y.toString))

    case (r @ Rect(w, h, rot, _), pt) => {
      val tl = r.tl + pt
      // conversion to degrees
      val rot_rad = rot * 180 / Pi
      val rot_str = s"rotate($rot_rad, ${tl.x}, ${tl.y})"
      Some(svg_<^.<.rect(
        svg_<^.^.x := tl.x.toString,
        svg_<^.^.y := tl.y.toString,
        svg_<^.^.width := w.toString,
        svg_<^.^.height := h.toString,
        svg_<^.^.transform := rot_str))
    }

    case (s @ Square(size, rot, _), pt) => {
      val tl = s.tl + pt
      // conversion to degrees
      val rot_rad = rot * 180 / Pi
      val rot_str = s"rotate($rot_rad, ${tl.x}, ${tl.y})"
      Some(svg_<^.<.rect(
        svg_<^.^.x := tl.x.toString,
        svg_<^.^.y := tl.y.toString,
        svg_<^.^.width := size.toString,
        svg_<^.^.height := size.toString,
        svg_<^.^.transform := rot_str))
    }

    case (p: GenericPath[_], pt) => Some(svg_<^.<.path(svg_<^.^.d := pathDAttr(p.subpaths, pt)))
    case (t @ Triangle(p2, p3, ga, _), pt) => Some(svg_<^.<.polygon(svg_<^.^.points := pointsAttr(t.points, pt)))
    case (q @ Quad(p2, p3, p4, ga), pt) => Some(svg_<^.<.polygon(svg_<^.^.points := pointsAttr(q.points, pt)))
    case _ => None
  }) map {elem: VdomTag => VdomArray.apply(checkAddGenAttrs(elem , g._1.genericAttribs))}

  def checkAddFillAttrs(elem: VdomTag, fillO: Option[Fill]) = fillO match {
    case Some(color: MiroColor) => elem(svg_<^.^.fill := color.toString)
    case _ => elem
  }

  def checkAddStrokeAttr(elem: VdomTag, strokeO: Option[Stroke]) = strokeO match {
//    case Some(Stroke(col, _)) => elem % new UnprefixedAttribute("stroke",
//      col.toString, Null)
    case _ => elem
  }

  def checkAddStrokeWidthAttr(elem: VdomTag, strokeO: Option[Stroke]) = strokeO match {
//    case Some(Stroke(_, width)) => elem % new UnprefixedAttribute("stroke-width",
//      width.toString, Null)
    case _ => elem
  }

  def checkAddFillOpacityAttr(elem: VdomTag, fillO: Option[Fill]) = fillO match {
    case Some(MiroColor(_, _, _, 1.0)) => elem
//    case Some(MiroColor(_, _, _, op)) => elem % new UnprefixedAttribute("fill-opacity", op.toString, Null)
    case _ => elem
  }

  def checkAddGenAttrs(elem: VdomTag, genAttrs: GenericAttribs): VdomTag = {
    val fun = {e: VdomTag => checkAddFillAttrs(e, genAttrs.fillO)} compose
      {e: VdomTag => checkAddStrokeAttr(e, genAttrs.strokeO)} compose
      {e: VdomTag => checkAddStrokeWidthAttr(e, genAttrs.strokeO)} compose
      {e: VdomTag => checkAddFillOpacityAttr(e, genAttrs.fillO)}

    fun(elem)
  }
}
