package com.github.skac112.miro.align

import com.github.skac112.miro._

case class Align[+G1 <: Graphic, +G2 <: Graphic](source: G1, target: G2) {
  def by(alignFun: AlignFun): PosGraphic[G2] = (target, alignFun(source, target))
}
