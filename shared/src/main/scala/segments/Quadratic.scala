package com.github.skac112.miro.segments

import com.github.skac112.miro._
import cats.data.State
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class Quadratic(cp: Point, override val end: Point) extends GenericQuadratic(end) {
  // we use state monad to perform adding points to set
  def addPt(pt: Point) = State[PtSet, Unit] {
    case points => (points + pt, ())
  }

  override lazy val bounds = boundsCalc

  private def boundsCalc: Bounds = {
    // Creates a set of points for bounds calculation

    def addPtsForBounds: State[PtSet, Unit] = for {
      // always adds start point (0, 0)
      _ <- addPt(ori)
      // always adds end point
      _ <- addPt(end)
      // if x(t) extremum exists, adds it
      _ <- if (extrX.isDefined) addPt(extrX.get) else State.pure[PtSet, Unit](())
      // if y(t) extremum exists, adds it
      end <- if (extrY.isDefined) addPt(extrY.get) else State.pure[PtSet, Unit](())
    } yield ()

    val pts = addPtsForBounds.runS(Set.empty).value
    Bounds.forPts(pts)
  }

  /**
   * Point p = (x(t), y(t)) for a given t. t is a parameter of a Bezier curve
   * and this segment is described by (x(t), y(t)) for t in range <0; 1>.
   */
  def ptAtParam(t: Double) = cp*2*(1 - t)*t + end*t*t

  /**
   * Calculates extremum point for x if exists.
   */
  def extrX: Option[Point] = {
    // finding value of curve param ("t") where derivative of x equals 0
    val tx = cp.x / (2*cp.x - end.x)
    // we are only interested in extremum for t in range (0; 1)
    if (tx > 0 && tx < 1) Some(ptAtParam(tx)) else None
  }

  /**
   * Calculates extremum point for y if exists.
   */
  def extrY: Option[Point] = {
    // finding value of curve param ("t") where derivative of x equals 0
    val ty = cp.y / (2*cp.y - end.y)
    // we are only interested in extremum for t in range (0; 1)
    if (ty > 0 && ty < 1) Some(ptAtParam(ty)) else None
  }
}
