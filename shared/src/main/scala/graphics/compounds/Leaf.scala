package com.github.skac112.miro.graphics.compounds

import com.github.skac112.miro._
import com.github.skac112.miro.graphics._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.segments._

import scala.math._
import com.github.skac112.vgutils._
import com.github.skac112.vgutils.MathUtils._
import com.github.skac112.miro.segments.Arc

object Leaf {
  def uniform(pivot: Point, d: Double) = Leaf(pivot, d, d)
}

/**
 * Path comprised of two circular arcs. Arcs join at points ori and pivot.
 * Values d1 and d2 determine, for each arc, a side and a distance of
 * point on arc with maximum distance from middle point of line connecting ori
 * and pivot. These points, for each arc, divide an arc in two parts of equal
 * length. Positive values of d1 and d2 result in leaf being "concave" on both
 * sides.
 * @param pivot Point determining "pivot" line ((0, 0), pivot)
 * @param r1 radius of first arc (positive value for "thickening" direction)
 * @param r2 radius of second arc (positive value for "thickening" direction)
 * @author slawek
 */
case class Leaf(pivot: Point,
                d1: Double,
                d2: Double,
                override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends GenericPath {
  lazy val curve = new GenericSegCurve {
    override lazy val segments = List(a1, a2)
    override lazy val closed = true
  }

  override lazy val subpaths: Subpaths = Seq((curve, ori))

  lazy val mid = pivot * .5

  lazy val r1 = {
    // luk o promieniu r1 przechodzi przez ori, pivot i 3-ci punkt (bedacy
    // srodkiem luku), ktory lezy w odleglosci d1 od srodka odcinka
    // wyznaczajacego "os" listka (ori, pivot). Znak d1 okresla, po ktorej
    // stronie wzgledem osi lezy szukany punkt
    // wektor od srodka osi listka do szukanego punktu srodka luku
    val d_vec = (mid.versor rot (-.5*Pi)) * d1
    val arc_mid = mid + d_vec
    val (_, r) = c3p(ori, pivot, arc_mid)
    r
  }

  lazy val r2 = {
    // luk o promieniu r1 przechodzi przez ori, pivot i 3-ci punkt (bedacy
    // srodkiem luku), ktory lezy w odleglosci d1 od srodka odcinka
    // wyznaczajacego "os" listka (ori, pivot). Znak d1 okresla, po ktorej
    // stronie wzgledem osi lezy szukany punkt
    val d_vec = (mid.versor rot (.5*Pi)) * d2
    val arc_mid = mid + d_vec
    val (_, r) = c3p(ori, pivot, arc_mid)
    r
  }

  lazy val a1 = Arc.circ(r1, r1 < abs(d1), d1 > 0, pivot)
  lazy val a2 = Arc.circ(r2, r2 < abs(d2), d2 > 0, -pivot)
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
