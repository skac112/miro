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
 * Example - leaf.
 */
object ExampleLeaf {
  def group = {
    val ga = GenericAttribs(Some(MiroColor.red()), None)
    val leaf = Leaf((100.0, .0), 70.0, -10.0, ga)
    Group(leaf :: Nil)
  }
}
