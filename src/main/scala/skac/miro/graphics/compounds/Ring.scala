package skac.miro.graphics.compounds

import skac.miro._
import skac.miro.graphics._
import skac.miro.Graphic._
import skac.miro.segments._
import scala.math._

/**
 * Pierscien kolowy.
 * @author slawek
 */
case class Ring(rLow: Double, rHigh: Double, override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends GenericPath {
  private def pathCircle1(r: Double) = {
    val arc1 = Arc(r, r, .0, true, true, Point(-r, -r))
    val arc2 = Arc(r, r, .0, false, true, Point(r, r))
    SegCurve(arc1 :: arc2 :: Nil)
  }

  private def pathCircle2(r: Double) = {
    val arc1 = Arc(r, r, .0, true, false, Point(-r, r))
    val arc2 = Arc(r, r, .0, false, false, Point(r, -r))
    SegCurve(arc1 :: arc2 :: Nil)
  }

  override lazy val subpaths: Subpaths = Seq((pathCircle1(rHigh), ori), (pathCircle2(rLow), (rLow - rHigh, .0)))
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  /**
   * Punkt srodka okregow pierscienia.
   */
  lazy val c = Point(-rHigh, .0)
}
