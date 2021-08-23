package com.github.skac112.miro

import com.github.skac112.vgutils._

package object align {
  /**
    * Type of function which aligns positions of two graphics to each other
    * due to some implicit rules. More precisely, it calculates a point such that
    * a posgraphic made of a second graphic and the calculated point aligns to graphic given in first
    * argument.
    */
  type AlignFun[G <: Graphic[_]] = (Graphic[_], G) => Point

  def joining[G <: Graphic[_]](cp1: Symbol, cp2: Symbol): AlignFun[G] = (g1: Graphic[_], g2: G) =>
    g1.characteristicPt(cp1) - g2.characteristicPt(cp2)
}
