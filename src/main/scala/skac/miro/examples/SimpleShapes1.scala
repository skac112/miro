package skac.miro.examples

import skac.miro.graphics._
import skac.miro.Graphic._
import skac.miro.draw.svg._
import skac.miro.attribs._
import skac.miro.attribs.colors._
import skac.miro._
import com.github.skac112.vgutils._

object SimpleShapes1 {
  def group = {
    val ga1 = GenericAttribs(Some(MiroColor.red()), Some(Stroke(MiroColor.red(), 3.0)))
    val line = graphics.Line((100.0, 100.0), ga1)
    val pos_line = (line, Point(10.0, 10.0))
    val pos_c = (Circle(30.0), Point(50.0, 50.0))
    Group(pos_line :: pos_c :: Nil)
  }
}
