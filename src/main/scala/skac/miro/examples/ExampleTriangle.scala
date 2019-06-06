package skac.miro.examples

import skac.miro.graphics._
import skac.miro.Graphic._
import skac.miro.draw.svg._
import skac.miro.attribs._
import skac.miro.attribs.colors._
import skac.miro._
import skac.miro.graphics.compounds._
import scala.math._
import com.github.skac112.vgutils._

/**
 * Example - triangle.
 */
object ExampleTriangle {
  def group = {
    val ga = GenericAttribs(Some(MiroColor.red()), None)
    val triangle = (Triangle((100.0, .0), (50.0, 50.0), ga), Point(100.0, 100.0))
    Group(triangle :: Nil)
  }
}
