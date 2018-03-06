package skac.miro
import scala.math._

object Bounds {
  def empty: Bounds = Bounds(ori, ori, 'EMPTY_BOUNDS)
  def point(pt: Point): Bounds = Bounds(pt, pt, 'POINT_BOUNDS)
  def inf = Bounds(ori, ori, 'INF_BOUNDS)

  /**
   * Calculates bounds containing sequence of points. Sequence can be of any
   * length including 0 and 1.
   */
  def forPts(pts: Set[Point]) = pts.foldLeft(empty) {(acc: Bounds, pt: Point) =>
    acc + point(pt)
  }
}

/**
 * Class representing rectangular area of drawing together with following special
 * cases:
 * - empty bounds
 * - point
 * - infinite bounds
 */
case class Bounds(tl: Point, br: Point, boundsType: Symbol = 'NORMAL_BOUNDS) {
  def +(other: Bounds) = (this.boundsType, other.boundsType) match {
    case (_, 'EMPTY_BOUNDS) => this
    case (_, 'INF_BOUNDS) => other
    case ('INF_BOUNDS, _) => this
    case ('EMPTY_BOUNDS, _) => other
    case (_, _) => {
      val tl = minTopLeft(this.tl, other.tl)
      val br = maxBottomRight(this.br, other.br)
      Bounds(tl, br)
    }
  }

  private def minTopLeft(tl1: Point, tl2: Point) = Point(min(tl1.x, tl2.x), min(tl1.y, tl2.y))

  private def maxBottomRight(br1: Point, br2: Point) = Point(max(br1.x, br2.x), max(br1.y, br2.y))

  /**
   * Width of these bounds.
   */
  def w = br.x - tl.x

  /**
   * Height of these bounds.
   */
  def h = br.y - tl.y

  def move(pt: Point) = Bounds(tl + pt, br + pt)
}
