package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._

/**
 * @author slawek
 */
case class Circle(r: Double = 1.0,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
  extends GenericEllipse with Graphic {
  override val rx = r
  override val ry = r
  override val rotation = 0.0

  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)  
}
