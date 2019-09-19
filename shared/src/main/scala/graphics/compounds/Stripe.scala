package com.github.skac112.miro.graphics.compounds

import com.github.skac112.miro.segments._
import com.github.skac112.miro._

import scala.math._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.graphics._
import com.github.skac112.vgutils._
import com.github.skac112.miro.graphics.GenericPath

object Stripe {
  def scaleWidth(pStripe: PosGraphic[Stripe], scale: Double) = {
    val (st, pt) = pStripe
    val new_st = st scaleWidth scale
    val trans_v = new Point((st.width - new_st.width) * .5, st.sa)
    (new_st, pt + trans_v)
  }
}

/**
 * Wycinek pierscienia kolowego (wstega lukowa).
 * sa - start Angle
 * ar - angle range
 * @author slawek
 */
case class Stripe(rLow: Double,
 rHigh: Double,
 sa: Angle,
 ar: Angle,
 override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends GenericPath {
  lazy val curve = new GenericSegCurve {
    override lazy val segments = s1 :: ha :: s2 :: la :: Nil
    override lazy val closed = true }

  override lazy val subpaths: Subpaths = Seq((curve, ori))

  /**
   * Poczatek 1-go odcinka.
   */
  lazy val p1 = ori

  /**
   * Koniec 1-go odcinka i poczatek 1-go luku.
   */
  lazy val p2 = new Point(rHigh - rLow, sa)

  lazy val p3 = new Point(rHigh, sa + ar) - new Point(rLow, sa)

  lazy val p4 = new Point(rLow, sa + ar) - new Point(rLow, sa)

  /**
   * Odcinek nr 1.
   */
  lazy val s1 = LineSeg(p2)

  /**
   * Luk nr 1 (wiekszy)
   */
  lazy val ha = Arc(rHigh, rHigh, .0, ar >= Pi, true, (p3 - p2))

  /**
   * Odcinek nr 2.
   */
  lazy val s2 = LineSeg(p4 - p3)

  /**
   * Luk nr 2 (mniejszy)
   */
  lazy val la = Arc(rLow, rLow, .0, ar >= Pi, false, (p1 - p4))

  /**
   * Srodek okregu (wzgledem punktu (0, 0) czyli poczatku 1-go odcinka).
   */
  lazy val c = new Point(rLow, sa.opposite)

  /**
   * Stripe width.
   */
  lazy val width = rHigh - rLow

  /**
   * End angle
   */
  lazy val ea = sa + ar

  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  lazy val rMid = .5 * (rLow + rHigh)

  /**
   * Modifies width multiplying it by a given scale conserving radius of
   * "middle" arc
   */
  def scaleWidth(scale: Double) = {
    // new width
    val new_w = scale * width
    copy(rLow = rMid - .5 * new_w, rHigh = rMid + .5 * new_w)
  }
}
