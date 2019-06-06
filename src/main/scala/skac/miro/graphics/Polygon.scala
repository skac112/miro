package skac.miro.graphics

import skac.miro.GenericPolygon
import skac.miro.Graphic._
import skac.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class Polygon(
                    override val points: Seq[Point],
                    override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericPolygon with Graphic {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
