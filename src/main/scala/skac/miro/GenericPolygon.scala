package skac.miro

import skac.miro.graphics.Path
import skac.miro.segments._
import com.github.skac112.vgutils._
/**
 * @author slawek
 */
trait GenericPolygon extends GenericSegCurve {
  // liczba punktow o 1 mniejsza niz liczba boków, bo przyjmuje sie domyslny punkt poczatkowy (0, 0)
  def points: Points

  /**
   * Kazdy segment ma wspolrzedne przesuniecia (end) obliczane jako roznica punktu koncowego i pocz.
   */
  override lazy val segments = ((ori +: points.view :+ ori).sliding(2) map {pts2 => LineSeg(pts2(1) - pts2(0))}).toSeq
  override def closed = true
}
