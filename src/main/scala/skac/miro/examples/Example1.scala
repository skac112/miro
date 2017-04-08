package skac.miro.examples

import skac.miro.graphics._
import skac.miro.Graphic._
import skac.miro.draw.svg._
import skac.miro.attribs._
import skac.miro.attribs.colors._
import skac.miro._

object Example1 {
  def group = {
    val ga1 = GenericAttribs(None, Some(Stroke(Color.red(), 1.0)))
    val ga = GenericAttribs(None, Some(Stroke(Color.blue(), 1.0)))
    val c_pos1 = (Circle(100.0, ga1), Point(.0, .0))
    val c_pos2 = (Circle(50.0, ga1), Point(200.0, .0))
    val c = Circle(70.0, ga)
    val c_pos = c.tanTo2c(c_pos1, c_pos2, true, true)
    Group((c_pos1 :: c_pos2 :: Nil) ++ c_pos)
  }
}
