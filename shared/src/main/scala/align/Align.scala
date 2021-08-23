package com.github.skac112.miro.align

import com.github.skac112.miro._

case class Align[+G <: Graphic[D], +D](source: Graphic[_], target: G) {
  def by[G2 >: G <: Graphic[D2], D2 >: D](alignFun: AlignFun[G2]): PosGraphic[G2, D2] = (target, alignFun(source, target))
}
