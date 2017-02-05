package skac.miro

import graphics._
import Graphic._
import segments._

object Dsl {
  /**
   * Prostokat.
   */
  def R(w: Double = 1.0, h: Double = 1.0, r: Double = 0.0, ga: GenericAttribs = defaultGenericAttribs) = Rect(w, h, r, ga)

  /**
   * Kwadrat.
   */
  def S(s: Double = 1.0, r: Double = 0.0, ga: GenericAttribs = defaultGenericAttribs) = Square(s, r, ga)

  /**
   * Elipsa.
   */
  def E(rx: Double = 1.0, ry: Double = 1.0, r: Double = 0.0, ga: GenericAttribs = defaultGenericAttribs) = Ellipse(rx, ry, r, ga)

  /**
   * Kolo.
   */
  def C(radius: Double = 1.0, ga: GenericAttribs = defaultGenericAttribs) = Circle(radius, ga)

  /**
   * Segment sciezki liniowy (odcinek)
   */
  def ls(len: Double) = LineSeg(len)

  /**
   * Segment sciezki - odcinek poziomy
   */
  def hs(len: Double) = HSeg(len)

  /**
   * Segment sciezki - odcinek pionowy
   */
  def vs(len: Double) = VSeg(len)

  /**
   * Segment sciezki - luk eliptyczny
   */
  def as(rx: Double = 1.0, ry: Double = 1.0, rot: Angle, laf: Boolean,
   sf: Boolean, end: Point) = Arc(rx, ry, rot, laf, sf, end)

  /**
   * Segment sciezki - kwadratowa krzywa Beziera
   */
  def qs(cp: Point, end: Point) = Quadratic(cp, end)

  /**
   * Segment sciezki - gladka kwadratowa krzywa Beziera
   */
  def sqs(end: Point) = SmoothQuadratic(end)

  /**
   * Segment sciezki - kubiczna krzywa Beziera
   */
  def cs(cp1: Point, cp2: Point, end: Point) = Cubic(cp1, cp2, end)

  /**
   * Segment sciezki - gladka kubiczna krzywa Beziera
   */
  def scs(cp2: Point, end: Point) = SmoothCubic(cp2, end)
}
