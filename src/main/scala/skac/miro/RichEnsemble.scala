package skac.miro

import skac.miro.align.Align

case class RichEnsemble(ensemble: Ensemble) {
  def align[GT <: Graphic](g: GT) = Align(this.ensemble.last._1, g)
//  def alignTo[GS <: Graphic](pg: PosGraphic[GS]) = Align(pg, this.pg._1)
  def alignSelf = Align(this.ensemble.last._1, this.ensemble.last._1)
}
