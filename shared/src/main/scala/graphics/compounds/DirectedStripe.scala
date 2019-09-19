package com.github.skac112.miro.graphics.compounds

import com.github.skac112.miro.segments._
import com.github.skac112.miro._
import scala.math._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.graphics._
import com.github.skac112.vgutils._

object DirectedStripe {
  def scaleWidth(pStripe: PosGraphic[Stripe], scale: Double) = {
    val (st, pt) = pStripe
    val new_st = st scaleWidth scale
    val trans_v = new Point((st.width - new_st.width) * .5, st.sa)
    (new_st, pt + trans_v)
  }
}

/**
  * Section of an sircle ring (arc stripe). Can have positive or negative curvature.
  */
case class DirectedStripe(width: Double,
                          length: Double,
                          curvature: Double,
                          sa: Angle,
                          override val genericAttribs: GenericAttribs = defaultGenericAttribs)
  extends GenericPath {

  lazy val curve = new GenericSegCurve {
    override lazy val segments = s1 :: a1 :: s2 :: a2 :: Nil
    override lazy val closed = true }

  override lazy val subpaths: Subpaths = Seq((curve, ori))

  /**
    * Circle center (relative to  (0, 0) - i.e. start of first line section) - if a stripe is not a rectangle
    * (curvature != 0.0)
    */
  lazy val cO = curvature match {
    case c if c > 0.0 => Some(new Point(r2O.get, sa))
    case c if c < 0.0 => Some(new Point(r2O.get, sa.opposite))
    case _ => None
  }

  /**
    * Poczatek 1-go odcinka.
    */
  lazy val p1 = ori

  /**
    * Koniec 1-go odcinka i poczatek 1-go luku.
    */
  lazy val p2 = new Point(width, sa)

  /**
    * Radius of first arc (if applicable, i.e curvature != 0)
    */
  lazy val r1O: Option[Double] = curvature match {
    case 0.0 => None
    case c => Some(abs(1/c) + signum(c)*0.5*width)
  }

  /**
    * Radius of second arc (if applicable, i.e curvature != 0)
    */
  lazy val r2O: Option[Double] = curvature match {
    case 0.0 => None
    case c => Some(abs(1/c) - signum(c)*0.5*width)
  }

  lazy val p3 = curvature match {
    case 0.0 => p2 + new Point(length,  sa + 0.5*Pi)
    case c if c > 0.0 => new Point(r1O.get, ea) - new Point(r2O.get, sa)
    case c => new Point(r2O.get, sa) - new Point(r1O.get, ea)
  }

  lazy val p4 = p3 + new Point(width, ea.opposite)

  /**
    * Odcinek nr 1.
    */
  lazy val s1 = LineSeg(p2)

  /**
    * First arc
    */
  lazy val a1 = Arc(r1O.get, r1O.get, .0, length*abs(curvature) >= Pi, curvature > 0.0, (p3 - p2))

  /**
    * Odcinek nr 2.
    */
  lazy val s2 = LineSeg(p4 - p3)

  /**
    * Luk nr 2 (mniejszy)
    */
  lazy val a2 = Arc(r2O.get, r2O.get, .0, length*abs(curvature) >= Pi, curvature < 0.0, (p1 - p4))

  /**
    * Srodek okregu (wzgledem punktu (0, 0) czyli poczatku 1-go odcinka).
    */
//  lazy val c = new Point(rLow, sa.opposite)

  /**
    * End angle
    */
  lazy val ea: Angle = sa + length*curvature

  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  lazy val rMidO: Option[Double] = curvature match {
    case 0.0 => None
    case c => Some(abs(1.0 / c))
  }

  /**
    * Modifies width multiplying it by a given scale conserving radius of
    * "middle" arc
    */
  def scaleWidth(scale: Double) = copy(width = scale * width)
}
