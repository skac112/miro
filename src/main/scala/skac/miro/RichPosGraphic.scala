package skac.miro

import graphics._

object RichPosGraphic {
  type AlignFun = Graphic => Point
  def makeTrans(alFun: AlignFun, g: Graphic): Transform = (pg: PosGraphic) => pg.align(alFun, g)
}

import RichPosGraphic._

case class RichPosGraphic(pg: PosGraphic) {
  def align(fun: AlignFun, g: Graphic): PosGraphic = (g, fun(g))

  def alignSelf(fun: AlignFun): PosGraphic = (pg._1, fun(pg._1))

  /**
   * Calculates align function which makes a circle tangent to given.
   */
  def cTan(dir: Angle): AlignFun =
    (c: Graphic) => pg._2 + (Point.versor(dir) * (pg._1.asInstanceOf[Circle].r + c.asInstanceOf[Circle].r))

  def cTanInt(dir: Angle): AlignFun =
    (c: Graphic) => pg._2 + (Point.versor(dir) * (pg._1.asInstanceOf[Circle].r - c.asInstanceOf[Circle].r))

  def cTanAlign(g: Graphic, dir: Angle): PosGraphic = align(cTan(dir), g)

  def cTanIntAlign(g: Graphic, dir: Angle): PosGraphic = align(cTanInt(dir), g)

  def t(trans: Transform) = trans(pg)  
}
