package skac.miro.examples

import skac.miro.graphics._
import skac.miro.Graphic._
import skac.miro.draw.svg._
import skac.miro.attribs._
import skac.miro.attribs.colors._
import skac.miro._
import skac.miro.graphics.compounds._
import scala.math._

/**
 * Przyklad - pierscien.
 */
object SimpleShapes4 {
  def group = {
    val ga = GenericAttribs(Some(Color.red), None)
    val ring = ArcSection(50, 0, 0.25 * Pi, ga)
    Group(ring :: Nil)
  }
}
