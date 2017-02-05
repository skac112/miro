package skac.miro.transform

/**
 * Transformacja liniowa.
 * @author slawek
 */
case class Linear(override val a: Double, 
  override val b: Double, 
  override val c: Double, 
  override val d: Double) extends GenericLinear(a, b, c, d) {
}