package com.github.skac112.miro.graphics.compounds

import com.github.skac112.miro.segments._
import com.github.skac112.miro._
import scala.math._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.graphics._
import com.github.skac112.vgutils._

/**
 * Wycinek kola.
 * @param sa start angle
 * @param ar angle range
 * @author slawek
 */
case class ArcSection[+D](r: Double, sa: Angle, ar: Angle, override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends GenericPath[D] {
  lazy val curve = new GenericSegCurve {
    override lazy val segments = s1 :: ha :: s2 :: Nil
    override lazy val closed = true
  }

  override lazy val subpaths: Subpaths = Seq((curve, ori))

  /**
   * Poczatek 1-go odcinka.
   */
  lazy val p1 = ori

  /**
   * Koniec 1-go odcinka i poczatek 1-go luku.
   */
  lazy val p2: Point = new Point(r, sa)

  lazy val p3: Point = new Point(r, sa + ar)

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
