package com.github.skac112.miro.graphics

import com.github.skac112.miro._
import com.github.skac112.miro.Graphic._
import com.github.skac112.vgutils._

object Circle {
}

/**
 * @author slawek
 */
case class Circle(r: Double = 1.0,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
  extends GenericEllipse with Graphic {
  // type T = this.type
  override val rx = r
  override val ry = r
  override val rotation = 0.0

  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  /**
   * Wyznacza okregi styczne do 2 innych okregow. Ze wzgledu na to, ze okrag w tym obiekcie
   * ma juz ustalony promien, wyznaczyc trzeba tylko jego srodek i podac go pozniej
   * jako pkt wynikowego PosGraphic.
   * Parametry loc1 i loc2 okreslaja pożądane polozenie szukanego okregu względem
   * odp. okregow zadanych - czy szukany okrag ma zawierac w sobie czy nie
   * dany okrag styczny.
   */
  def tanTo2c(c1: PosGraphic[Circle], c2: PosGraphic[Circle], loc1: Boolean, loc2: Boolean): Set[PosGraphic[Circle]] = {
    MathUtils.cTanTo2c(c1._2.x, c1._2.y,
     c1._1.r, loc1, c2._2.x, c2._2.y,
     c2._1.r, loc2, r) map {kv =>
      (Circle(r = this.r, genericAttribs = this.genericAttribs), Point(kv._1, kv._2))
    }
  }

  override lazy val bounds = Bounds((-r, -r), (r, r))

  override def cp(code: Symbol) = code match {
    case 'NQ => nq
    case 'SQ => sq
    case 'EQ => eq
    case 'WQ => wq
    case 'C => ori
    case _ => super.cp(code)
  }
}
