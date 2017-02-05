package skac.miro.graphics.compounds

import skac.miro.segments._
import skac.miro._
import scala.math._
import skac.miro.Graphic._
import skac.miro.graphics._

/**
 * Wycinek pierscienia kolowego.
 * @author slawek
 */
case class Stripe(r1: Double, r2: Double, sa: Angle, ar: Angle, override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends GenericPath {
  /**
   * Linie w kolejnosci zgodnej z kierunkiem wskazowek zegara.
   */
  override lazy val subpaths = Seq(new GenericSegCurve {
    override lazy val segments = s1 :: ha :: s2 :: la :: Nil
    override lazy val closed = true})

  /**
   * Poczatek 1-go odcinka.
   */
  lazy val p1 = ori

  /**
   * Koniec 1-go odcinka i poczatek 1-go luku.
   */
  lazy val p2 = (r2 - r1) rot sa

  lazy val p3 = (r2 rot (sa + ar)) - r1 rot sa

  lazy val p4 = (r1 rot (sa + ar)) - r1 rot sa

  /**
   * Odcinek nr 1.
   */
  lazy val s1 = LineSeg(p2)

  /**
   * Luk nr 1 (wiekszy)
   */
  lazy val ha = Arc(r2, r2, .0, ar >= Pi, true, (p3 - p2))

  /**
   * Odcinek nr 2.
   */
  lazy val s2 = LineSeg(p4 - p3)

  /**
   * Luk nr 2 (mniejszy)
   */
  lazy val la = Arc(r1, r1, .0, ar >= Pi, false, (p1 - p4))

  /**
   * Srodek okregu (wzgledem punktu (0, 0) czyli poczatku 1-go odcinka).
   */
  lazy val c = r1 rot -sa
}
