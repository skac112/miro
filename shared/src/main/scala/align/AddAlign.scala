package com.github.skac112.miro.align

import com.github.skac112.miro.PosGraphic
import com.github.skac112.miro._

case class AddAlign(source: Graphic, target: Graphic) {
  def by(alignFun: AlignFun): Graphic = {
    val last = ensemble(source).last
    val new_last = Align(last._1, target) by alignFun
    source + new_last
  }
}
