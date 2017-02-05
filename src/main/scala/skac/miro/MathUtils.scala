package skac.miro

import scala.math._
import skac.miro.transform.Rotation

/**
 * @author slawek
 */
object MathUtils {
  type RealFun = Double => Double
  def integrate(fun: RealFun, from: Double, to: Double) = simpsonIntegr(fun, from, to, 1000)

  def simpsonIntegr(fun: RealFun, from: Double, to: Double, n2: Int): Double = {
    val n = 2 * n2
    val h = (to - from) / n
    val s1 = fun(from) + fun(to)

    val sum = s1 +
    (0 to n2 - 1).foldLeft(0.0) {(acc: Double, i: Int) => {acc + 4 * fun(from + (2 * i  + 1) * h)}} +
    (1 to n2 - 2).foldLeft(0.0) {(acc: Double, i: Int) => {acc + 2 * fun(from + (2 * i  + 2) * h)}}

    sum * h / 3
  }

  object EllipseCalc {
    /**
     * Wyznacza srodek elipsy luku eliptycznego oraz wartosci katow tau1 i delta tau w rownaniu parametrycznym
     * dla danych punktow koncowych p1 i p2, dlugosci polosi oraz kata dlugiej polosi. Obliczenia na
     * podstawie: https://www.w3.org/TR/SVG/implnote.html#ArcImplementationNotes
     * Dane:
     * p1, p2 - punkty koncowe
     * a - dluga polos
     * b - krotka polos
     * fi - kat dlugiej polosi wzgledem x
     * laf - Large Arc Flag
     * sf - Sweep Flag
     * Wyznacza:
     * - c - srodek elipsy
     * - tau1 - kat promienia wodzacego punktu odp. punktowi poczatkowemu luku
     * na okregu jednostkowym bedacym obrazem elipsy luku w odwrotnej
     * transformacji elipsy (odwrocenie rozciagania i obrotu)
     * - delta_tau - roznica katow promieni wodzacych odp. punktowi koncowemu i
     * poczatkowemu luku na okregu jednostkowym bedacym obrazem elipsy luku w
     * odwrotnej transformacji elipsy (odwrocenie rozciagania i obrotu)
     */
    def ptToTauParam(p1: Point, p2: Point, a: Double, b: Double, fi: Angle, laf: Boolean, sf: Boolean) = {
      // transformacja punktow p1 i p2
      val p1p: Point = ((p1 - p2) * 0.5) rot -fi
      val sign = if (laf != sf) 1 else -1
      val a2 = a*a
      val b2 = b*b
      val p1p2 = Point(p1p.x * p1p.x, p1p.y * p1p.y)
      // srodek elipsy w transformacji ("primowany")
      val cp = (a * p1p.y / b, -b*p1p.x / a) * sign * sqrt((a2*b2 - a2*p1p2.y - b2*p1p2.x) / (a2*p1p2.y + b2*p1p2.x))
      // srodek elipsy po transformacji odwrotnej
      val c = (cp rot fi) + ((p1 + p2) * 0.5)
      val p_ang1 = ((p1p.x - cp.x) / a, (p1p.y - cp.y) / b)
      def tau1 = (1.0, .0) ang p_ang1
      val p_ang2 = ((-p1p.x - cp.x) / a, (-p1p.y - cp.y) / b)
      def delta_tau1 = p_ang1 ang p_ang2
      val delta_tau = if (!sf && (delta_tau1 > .0)) delta_tau1 - 2 * Pi else if (sf && (delta_tau1 < .0)) delta_tau1 + 2 * Pi else delta_tau1
      (tau1, delta_tau, c)
    }

    /**
     * Konwertuje "centralna" parametryzacje elipsy na "koncowopunktowa"
     * parametryzacje elipsy, tzn. na podstawie parametrow tau, deltaTau, c,
     * a, b, fi wyznacza parametry p1, p2, laf i sf, gdzie:
     * - tau - kat promienia wodzacego odp. punktowi poczatkowemu
     * przetransformowanemu na okreg jednostkowym
     * - deltaTau - roznica katow promieni wodzacych odp. punktowi koncowemu i
     * poczatkowemu luku na okregu jednostkowym bedacym obrazem elipsy luku w
     * odwrotnej transformacji elipsy (odwrocenie rozciagania i obrotu)
     * - c - srodek elipsy
     * - a - dluga polos
     * - b - ktorka polos
     * - fi - kat dlugiej polosi wzgledem x
     * laf - Large Arc Flag
     * sf - Sweep Flag
     * deltaTau Double a nie Angle bo reprezentuje przyrost luku, ktory moze
     *  byc ujemny (znak okresla kierunek)
     */
    def tauToPtParam(tau: Angle, deltaTau: Double, c: Point, a: Double, b: Double, fi: Double) = {
      def calcPt(tau: Angle) = {
        val p1 = Point(a, b) * Point.versor(tau)
        (p1 rot fi) + c
      }

      val p1 = calcPt(tau)
      val p2 = calcPt(tau + deltaTau)
      val laf = abs(deltaTau) > Pi
      val sf = deltaTau > .0
      (p1, p2, laf, sf)
    }
  }

  /**
   *
   */
  def tauToArcLen(tau: Angle, a: Double, b: Double, sf: Boolean) = {

  }
}
