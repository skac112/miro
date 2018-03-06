package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._
import skac.miro.segments._

/**
 * @author slawek
 */
case class Polyline(points: Points,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericSegCurve with Graphic {
  override def segments = ((ori +: points.view).sliding(2) map {
    pts2 => LineSeg(pts2(1) - pts2(0))}).toSeq

  def toPolygon = Polygon(points, genericAttribs)
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)    
}
