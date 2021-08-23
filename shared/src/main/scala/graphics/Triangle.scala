package com.github.skac112.miro.graphics

import com.github.skac112.miro._
import com.github.skac112.miro.Graphic._
import scala.math._
import com.github.skac112.vgutils._

object Triangle {
  def equilateral(len: Double = 1.0, angle: Angle = Angle(0)) = Triangle(Point(len, 0) rot angle, Point(.5*len, .5*len*sqrt(3)) rot angle)
  def isosceles(base: Double = 1.0, arm: Double = .5*sqrt(5), angle: Angle = Angle(0)) = Triangle(Point(base, 0), Point(.5*base, sqrt(arm*arm - .25*base*base)))
  def right(c1: Double, c2: Double, c1a: Angle) = Triangle(Point(c1, 0) rot c1a, Point(0, c2) rot c1a)
}

/**
 * Triangle. Default argument values generate unitary equilateral triangle.
 */
case class Triangle[+D](pt2: Point = Point(1, .0),
                    pt3: Point = Point(.5, .5 * sqrt(3)),
                    override val genericAttribs: GenericAttribs = defaultGenericAttribs,
                       override val metadataO: Option[D] = None)
extends GenericPolygon with Graphic[D] {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
  override lazy val points = Seq(pt2, pt3)

  /**
   * Creates triangle with specified side lengths and angle of first side.
   */
  def apply(s1: Double, s2: Double, s3: Double) = ???

  /**
   * Middle point. This is a point where bisector lines interscect. It is always
   * located inside the triangle.
   */
  lazy val mid: Point = {
    // we need only two of three bisectors to find middle point
    // we use the fact that first vertex (p1) is ori
    val bi_ang_1 = (pt2 ang pt3) * .5
    val bi_ang_2 = ((pt3 - pt2) ang -pt2) * .5
    // finding points on bisector lines (other than corners of input triangle)
    val p21 = pt2 rot bi_ang_1
    val p31 = ((pt3 - pt2) rot bi_ang_2) + pt2
    // bisector lines
    val bis_1 = MathUtils.line2p(ori, p21)
    val bis_2 = MathUtils.line2p(pt2, p31)
    // intersection of bisector lines - middle point
    val (mid_x, mid_y) = MathUtils.linearSys2(bis_1._1, bis_1._2, bis_1._3,
     bis_2._1, bis_2._2, bis_2._3)
    Point(mid_x, mid_y)
  }
}
