package skac.miro.transform

import skac.miro.Point

object Transl {
  implicit def PointToTransl(pt: Point) = Transl(pt)  
}

/**
 * Translacja
 * @author slawek
 */
case class Transl(vec: Point) extends Affine(1.0, .0, .0, 1.0, vec.x, vec.y){
  
}