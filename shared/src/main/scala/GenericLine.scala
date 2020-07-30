package com.github.skac112.miro

import com.github.skac112.vgutils.ori

/**
 * @author slawek
 * Abstrakcyjny odcinek (w matematycznym sensie) nie bedacy obiektem grafinczym.
 */
trait GenericLine extends Curve {
  // TODO: uzupelnic !!!
  override def apply(t: Double) = ori
}
