package com.github.skac112.miro.graphics

import com.github.skac112.miro._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.segments._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class Polyline[+D](points: Points,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericSegCurve with Graphic[D] {
  override def segments = ((ori +: points.view).sliding(2) map {
    pts2 => LineSeg(pts2(1) - pts2(0))}).toSeq

  def toPolygon = Polygon(points, genericAttribs)
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)    
}
