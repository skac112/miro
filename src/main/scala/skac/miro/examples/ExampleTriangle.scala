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
 * Example - triangle.
 */
object ExampleTriangle {
  def group = {
    val ga = GenericAttribs(Some(Color.red()), None)
    val triangle = (Triangle(Point(100, 0), Point(50, 50), ga), Point(100, 100))
    Group(triangle :: Nil)
  }
}
