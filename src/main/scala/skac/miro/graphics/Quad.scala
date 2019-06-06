package skac.miro.graphics
import skac.miro._
import skac.miro.Graphic._
import scala.math._
import com.github.skac112.vgutils._
import com.github.skac112.vgutils.transform._

object Quad {
  /**
   * Creates deltoid parameterized by location of two vertices where equal sides
   * join. Fourth vertex is symmetric to ori (first vertex) with respect to
   * deltoid symmetry line (joining v2 and v3).
   */
  def deltoid(v2: Point, v3: Point) = {
    // deltoid's line of symmetry
    val s = MathUtils.line2p(v2, v3)
    // transformation of mirror with respect to line of symmetry
    val mirr_trans = Affine.mirror(s._1, s._2, s._3)
    val v4 = mirr_trans transPt ori
    Quad(v2, v3, v4)
  }

  /**
   * Creates parallelogram paremeterized by two vertices which are neighbours
   * to ori (first vertex).
   */
  def parallelogram(v2: Point, v3: Point) = Quad(v2, v3, v2 + v3)

  /**
   * Creates a diamond parameterized by one point and an angle. The given point,
   * together with ori, determines one side of a diamond. The
   * second side is given by rotating first side by a given angle.
   */
  def diamond(v2: Point, angle: Angle) = parallelogram(v2, v2 rot angle)

  /**
   * Creates quad with given side lengths inscribed in circle.
   */
  def fromSides = ???
}

case class Quad(v2: Point = Point(1, 0),
                v3: Point = Point(1, 1),
                v4: Point = Point(0, 1),
                override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericPolygon with Graphic {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
  override lazy val points = Seq(v2, v3, v4)
}
