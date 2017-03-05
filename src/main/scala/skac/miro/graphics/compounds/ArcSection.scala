package skac.miro.graphics.compounds

import skac.miro.segments._
import skac.miro._
import scala.math._
import skac.miro.Graphic._
import skac.miro.graphics._

/**
 * Wycinek kola.
 * sa - start Angle
 * ar - angle range
 * @author slawek
 */
case class ArcSection(r: Double, sa: Angle, ar: Angle, override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends GenericPath {
  lazy val curve = new GenericSegCurve {
    override lazy val segments = s1 :: ha :: s2 :: Nil
    override lazy val closed = true }

    override lazy val subpaths: Subpaths = Seq((curve, ori))

    /**
     * Poczatek 1-go odcinka.
     */
    lazy val p1 = ori

    /**
     * Koniec 1-go odcinka i poczatek 1-go luku.
     */
    lazy val p2 = r rot sa

    lazy val p3 = r rot (sa + ar)

    /**
     * Odcinek nr 1.
     */
    lazy val s1 = LineSeg(p2)

    /**
     * Luk
     */
    lazy val ha = Arc(r, r, .0, ar >= Pi, true, (p3 - p2))

    /**
     * Odcinek nr 2.
     */
    lazy val s2 = LineSeg(-p3)

    lazy val c = ori

    override def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
