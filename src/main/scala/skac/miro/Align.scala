package skac.miro

import skac.miro.graphics._

object Align {
  /**
   * Type of function which aligns positions of two graphics to each other
   * due to some implicit rules. More precisely, it calculates point of
   * posgraphic of second argument such that it aligns toposgraphic given in
   * first argument.
   */
  type AlignFun = (PosGraphic, Graphic) => Point

  def align(fun: AlignFun, pg: PosGraphic, g: Graphic): PosGraphic = (g, fun(pg, g))

  def alignSelf(pg: PosGraphic, fun: AlignFun): PosGraphic = (pg._1, fun(pg, pg._1))

  /**
   * Calculates align function which makes a circle tangent to given.
   */
  def cTan(dir: Angle): AlignFun =
    (pg: PosGraphic, c: Graphic) => pg._2 + (Point.versor(dir) * (pg._1.asInstanceOf[Circle].r + c.asInstanceOf[Circle].r))

  def cTanInt(dir: Angle): AlignFun =
    (pg: PosGraphic, c: Graphic) => pg._2 + (Point.versor(dir) * (pg._1.asInstanceOf[Circle].r - c.asInstanceOf[Circle].r))

  def cTanAlign(pg: PosGraphic, g: Graphic, dir: Angle): PosGraphic = align(cTan(dir), pg, g)

  def cTanIntAlign(pg: PosGraphic, g: Graphic, dir: Angle): PosGraphic = align(cTanInt(dir), pg, g)
}
