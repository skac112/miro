package com.github.skac112.miro

import align._

case class RichPosGraphic[+G <: Graphic](pg: PosGraphic[G]) {
//  def align[GT <: Graphic](g: GT) = Align(this.pg, g)
//  def alignTo[GS <: Graphic](pg: PosGraphic[GS]) = Align(pg, this.pg._1)
//  def alignSelf = Align(pg, this.pg._1)
}
