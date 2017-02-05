package skac.miro.segments

import skac.miro._
import skac.miro.Angle
import skac.miro.Point

/**
 * Abstrakcyjny luk eliptyczny. Skladnik podsciezek Path.
 * @author slawek
 */
case class Arc(
  rx: Double = 1.0,
  ry: Double = 1.0,
  /* kat osi x */
  rotation: Angle,
  largeArcFlag: Boolean,
  sweepFlag: Boolean,
  override val end: Point)
  extends Segment {

// UWAGA! odkomentowac po stworzeniu funkcji tauTo.. i arcLen..
  // override def ptAt(arcLen: Double) = {
  //   val (init_tau, init_delta_tau, cen) = MathUtils.EllipseCalc.ptToTauParam(ori, end, rx, ry, rotation, largeArcFlag, sweepFlag)
  //   val init_arclen = MathUtils.EllipseCalc.tauToArcLen(init_tau, rx, ry, sweepFlag)
  //   val tau = MathUtils.EllipseCalc.arcLenToTau(init_arclen + arcLen, rx, ry, sweepFlag)
  //   val p1 = Point(rx, ry) * Point.versor(tau)
  //   (p1 rot rotation) + cen
  //   // TODO: tymczasowo!
  //   // ori
  // }
}
