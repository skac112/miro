package skac.miro.graphics

import skac.miro.GenericRect
import skac.miro.Graphic._
import skac.miro._

/**
 * @author slawek
 */
case class Square(size: Double = 1.0,
  override val rotation: Double = 0.0,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericRect with Graphic {
  override val width = size
  override val height = size
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
