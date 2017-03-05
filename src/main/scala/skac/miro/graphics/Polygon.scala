package skac.miro.graphics

import skac.miro.GenericPolygon
import skac.miro.Point
import skac.miro.Graphic._
import skac.miro._

/**
 * @author slawek
 */
case class Polygon(
  override val points: Seq[Point],
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericPolygon with Graphic {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
