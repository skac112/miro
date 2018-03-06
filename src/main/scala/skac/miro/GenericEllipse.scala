package skac.miro

/**
 * Abstrakcyjna elipsa.
 * @author slawek
 */
trait GenericEllipse extends Curve {
  def rx: Double
  def ry: Double
  def rotation: Double
  override def closed = true

  /**
   * Polnocny kwadrant.
   */
  // lazy val nq = (rx, -ry) rot rotation

  /**
   * Wschodni kwadrant.
   */
  // lazy val eq = (rx, 0.0) rot rotation

  /**
   * Poludniowy kwadrant.
   */
  // lazy val sq = (0.0, ry) rot rotation

  /**
   * Zachodni kwadrant rowny jest ori.
   */
  // lazy val wq = ori
}
