package skac.miro.graphics

import skac.miro.Graphic
import skac.miro.Graphic._
import skac.miro.GenericPolygon
import skac.miro.GenericRect

//object Rect {
//  def unapply(rect: Rect) = Some((rect.width, rect.height, rect.genericAttribs))
//}

/**
 * Rectangle. 
 * @author slawek
 */
case class Rect(
  override val width: Double = 1.0,
  override val height: Double = 1.0,
  override val rotation: Double = 0.0,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericRect with Graphic {

}
