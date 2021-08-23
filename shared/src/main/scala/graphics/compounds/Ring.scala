package com.github.skac112.miro.graphics.compounds

import com.github.skac112.miro._
import com.github.skac112.miro.graphics._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.segments._

import scala.math._
import com.github.skac112.vgutils._
import com.github.skac112.miro.graphics.GenericPath

/**
 * Pierscien kolowy.
 * @author slawek
 */
case class Ring[+D](rLow: Double, rHigh: Double, override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends GenericPath[D] {
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
   * Width of this ring.
   */
  lazy val width = rHigh - rLow

  lazy val rMid = .5 * (rLow + rHigh)

  /**
   * Center point of circles of the stripe.
   */
  lazy val c = Point(-rHigh, .0)

  def scaleWidth(scale: Double) = {
    // new width
    val new_w = scale * width
    copy(rLow = rMid - .5 * new_w, rHigh = rMid + .5 * new_w)
  }
}
