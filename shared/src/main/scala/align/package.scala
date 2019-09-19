package com.github.skac112.miro

import com.github.skac112.vgutils._

package object align {
  /**
    * Type of function which aligns positions of two graphics to each other
    * due to some implicit rules. More precisely, it calculates a point such that
    * a posgraphic made of a second graphic and the calculated point aligns to graphic given in first
    * argument.
    */
  type AlignFun = (Graphic, Graphic) => Point

  def joining(cp1: Symbol, cp2: Symbol): AlignFun = (g1: Graphic, g2: Graphic) => g1.cp(cp1) - g2.cp(cp2)
}
