package skac.miro.transform

import skac.miro._

/**
 * @author slawek
 */
class Affine(val a: Double, val b: Double, val c: Double, val d: Double, val e: Double, val f: Double) extends Transform {
  def apply(PosGraphic: PosGraphic) = (PosGraphic._1.doTransform(this), transPt(PosGraphic._2))  
  def transPt(Pt: Point) = Point(a*Pt.x + b*Pt.y + e, c*Pt.x + d*Pt.y + f)
  
//  /**
//   * Zwraca wyznacznik liniowej czesci przeksztalcenia. Wartosc wyznacznika okresla, jak zmienia sie powierzchnia
//   * figury poddanej przeksztalceniu. Wartosc wieksza od 1 oznacza powiekszanie powierzchni, mniejsza niz 1 - zmniejszanie.
//   */
//  def linDet = a*c - b*d
  
  /**
   * Zwraca liniowa czesc przeksztalcenia
   */
  def lin = new Linear(a, b, c, d)
  
  /**
   * Zwraca przeksztalcenie translacji wystepujacej w tym przeksztalceniu
   */
  def transl = Transl(e, f)
  
  def translPt = Point(e, f)
    
  /**
   * Transformacja odwrotna.
   */
  lazy val inv: Affine = {
    val lin_inv = lin.inv
    lin_inv - (lin_inv transPt translPt) 
  }
}

//class Scale(ScaleX: Double, ScaleY: Double) extends Affine(ScaleX, 0.0, 0.0, ScaleY, 0.0, 0.0)
//case class UniScale(Scale: Double) extends Scale(Scale, Scale)
//case class Move(MoveX: Double, MoveY: Double) extends Affine(1.0, 0.0, 0.0, 1.0, MoveX, MoveY)
//case class RotTrans(Rot: Double) extends Affine(cos(Rot), -sin(Rot), sin(Rot), cos(Rot), 0.0, 0.0)
//class Linear(A: Double, B: Double, C: Double, D: Double) extends Affine(A, B, C, D, 0.0, 0.0)
