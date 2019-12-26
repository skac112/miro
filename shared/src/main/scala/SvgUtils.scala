package com.github.skac112.miro

import com.github.skac112.miro.{Subpath, Subpaths}
import com.github.skac112.miro.segments._
import com.github.skac112.vgutils.{Point, Points}
import com.github.skac112.vgutils._

object SvgUtils {
  /**
   * Zwraca wartosc atrybutu "d" sciezki
   */
  def pathDAttr(subpaths: Subpaths, pt: Point): String =
    (subpaths.foldLeft("") {(curr: String, segs: Subpath) => {
      curr + subpathToDStr(segs, pt) + " "
    }}).trim

  def pointsAttr(points: Points, pt: Point): String =
    (ori +: points) map {_ + pt} map {pt => s"${pt.x},${pt.y}"} reduceLeft {_ + " " + _}

  // TODO! uzupelnic
  def subpathToDStr(subpath: Subpath, pt: Point): String = {
    val start_pt = subpath._2 + pt
    subpath._1.segments.foldLeft(s"M ${start_pt.x},${start_pt.y}") {(curr: String, seg: Segment) => {
      curr + " " + segToDStr(seg)
    }} + " z"
  }

  def segToDStr(seg: Segment): String = seg match {
    case LineSeg(end) => s"l ${end.x} ${end.y}"
    case HSeg(len) => s"h $len"
    case VSeg(len) => s"v $len"
    case Arc(rx, ry, rotation, laf, sf, end) => s"a $rx $ry ${rotation.value} ${bool2Bit(laf)} ${bool2Bit(sf)} ${end.x},${end.y}"
    case Quadratic(cp, end) => s"q ${cp.x},${cp.y} ${end.x},${end.y}"
    case SmoothQuadratic(end) => s"t ${end.x},${end.y}"
    case Cubic(cp1, cp2, end) => s"c ${cp1.x},${cp1.y} ${cp2.x},${cp2.y} ${end.x},${end.y}"
    case SmoothCubic(cp2, end) => s"s ${cp2.x},${cp2.y} ${end.x},${end.y}"
  }

  def bool2Bit(b: Boolean): String = b match {
    case true => "1"
    case _ => "0"
  }
}
