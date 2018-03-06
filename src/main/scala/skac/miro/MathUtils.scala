package skac.miro

import skac.miro.transform.Rotation
import scala.math._

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

  /**
   * Wyznacza funkcje odwrotna metoda bisekcji.
   * Zaklada, ze fun jest roznowartosciowa i ciagla, a wiec i monotoniczna.
   * Zaklada tez, ze x miesci sie w przedziale wyznaczonym przez fun(minArg) i
   * fun(maxArg).
   */
  def invFun(fun: RealFun,
   minArg: Double = Double.MinValue,
   maxArg: Double = Double.MaxValue,
   eps: Double = 1E-9): RealFun = (x: Double) => {
    val f_minarg = fun(minArg)
    val f_maxarg = fun(maxArg)
    val init_abs_diff = abs(f_minarg - f_maxarg)
    val x_norm = if (x > scala.math.max(f_minarg, f_maxarg) || x < scala.math.min(f_minarg, f_maxarg)) {
      x - floor(x / init_abs_diff) * init_abs_diff
    }
    else {
      x
    }
    def yBetween(f1: Double, f2: Double) = x_norm >= scala.math.min(f1, f2) && x_norm <= scala.math.max(f1, f2)
    // interpolacja szukanego argumentu fun metoda bijekcji - zawezanie widelek
    var abs_diff = init_abs_diff
    var min = minArg
    var max = maxArg

    if (!yBetween(fun(min), fun(max))) {
      throw new Exception(s"Invalid minArg ($minArg) or maxArg ($maxArg) - searched value ($x) outside range.")
    }

    while (abs_diff > eps) {
      val mid = .5 * (min + max)
      val f1 = fun(min)
      val f2 = fun(mid)
      if (yBetween(f1, f2)) {
        max = mid
      }
      else {
        min = mid
      }
      abs_diff = abs(f1 - f2)
    }
    .5 * (min + max)
  }

  // def precalcInvFun(fun: RealFun): RealFun = {
  //
  // }

  // def precalcFun(fun: RealFun, x0: Double, x1: Double, n: Double = 10000): RealFun = {
  //
  // }

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
      val delta_tau: Double = if (!sf && (delta_tau1 > .0)) delta_tau1.normVal - 2 * Pi else if (sf && (delta_tau1 < .0)) delta_tau1.normVal + 2 * Pi else delta_tau1.normVal
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

    /**
     * Wyznacza biezaca dlugosc luku liczac od kata 0 dla punktu wskazanego
     * parametrem tau w elipsie o polosiach a i b i przy liczeniu kata w kierunku
     * wskazanym przez sf (sweeptFlag, znaczenie takie jak w SVG)
     * Tau jest katem promienia wodzacego okregu jednostkowego odp. elipsie
     */
    def tauToArcLen(tau: Angle, a: Double, b: Double, sf: Boolean) = {
      val e = sqrt(1 - b*b/a/a)
      a * incEllInt(tau, e)
    }

    def arcLenToTauF(a: Double, b: Double, sf: Boolean) = invFun(tauToArcLen(_, a, b, sf), 0, 2*Pi)

    /**
     * Calculates incomplete elliptic integral.
     * Na podstawie: http://mathworld.wolfram.com/EllipticIntegraloftheSecondKind.html
     */
    def incEllInt(tau: Double, k: Double) = {
      val k2 = k*k
      def fun(t: Double): Double = {
        val s = sin(t)
        sqrt(1 - k2*s*s)
      }

      simpsonIntegr(fun, 0, tau, 1000)
    }
  }

  /**
   * Wyznacza okregi o zadanym promieniu styczne do 2 innych okregow. Moze
   * istniec 0, 1 lub 2 rozwiazania. Parametry loc1 i loc2 okreslaja, czy
   * szukany okrag ma byc na zewnatrz (true), czy wewnatrz (false) odp. okregu
   * stycznego. Zwraca pary wyznaczajace srodki okregow stycznych.
   */
  def cTanTo2c(x1: Double, y1: Double, r1: Double, loc1: Boolean, x2: Double,
   y2: Double, r2: Double, loc2: Boolean, r: Double): Set[(Double, Double)] = {
     val calc_case = x1 != x2
     val (v11, v12, v21, v22) = calc_case match {
       case true => (x1, x2, y1, y2)
       case false => (y1, y2, x1, x2)
     }
     val s1 = if (loc1) -1.0 else 1.0
     val s2 = if (loc2) -1.0 else 1.0
     // wspolczynniki rownania x = ay + b
     val a = (v21 - v22)/(v12 - v11)
     val b = .5*(2.0*r*(s2*r2 - s1*r1) + r1*r1 - r2*r2 - v11*v11 - v21*v21 +
      v12*v12 + v22*v22)/(v12 - v11)
     // wspolczynniki rownania kwadratowego alfa*xs^2 + beta*xs + gamma = 0
     val alfa = a*a + 1.0
     val beta = 2.0*(a*b - a*v11 - v21)
     val gamma = -(r - s1*r1)*(r - s1*r1) + b*b - 2.0*b*v11 + v11*v11 + v21*v21
     quadEqReal(alfa, beta, gamma) map {v => calc_case match {
       case true => (a*v + b, v)
       case false => (v, a*v + b)
     }}
  }

  /**
   * Wyznacza okregi styczne do 3 innych okregow.
   * Zwraca trojki (xs, ys, rs).
   */
  def cTanTo3c(x1: Double, y1: Double, r1: Double, loc1: Boolean, x2: Double,
   y2: Double, r2: Double, loc2: Boolean, x3: Double, y3: Double, r3: Double, loc3: Boolean):
   Set[(Double, Double, Double)] = {
    val s1 = if (loc1) -1.0 else 1.0
    val s2 = if (loc2) -1.0 else 1.0
    val s3 = if (loc3) -1.0 else 1.0
    val den_inv1 = 1.0 / (s2*r2 - s1*r1)
    val a1 = (x2 - x1) * den_inv1
    val b1 = (y2 - y1) * den_inv1
    val c1 = .5 * (x1*x1 + y1*y1 - x2*x2 - y2*y2 - r1*r1 + r2*r2) * den_inv1
    val den_inv2 = 1.0 / (s3*r3 - s2*r2)
    val a2 = (x3 - x2) * den_inv2
    val b2 = (y3 - y2) * den_inv2
    val c2 = .5 * (x2*x2 + y2*y2 - x3*x3 - y3*y3 - r2*r2 + r3*r3) * den_inv2
    val alfa = (b2 - b1)/(a1 - a2)
    val beta = (c2 - c1)/(a1 - a2)
    // wspolczynniki ukladu rownan:
    // xs = A*rs + B
    // ys = C*rs + D
    val C = 1.0 / (a1*alfa + b1)
    val D = -(a1*beta + c1)/(a1*alfa + b1)
    val A = alfa*C
    val B = alfa*D + beta
    // wspolczynniki rownania kwadratowego na rs:
    // a*rs**2 + b*rs + c = 0
    val a = A*A + C*C - 1
    val b = 2.0*(A*B - A*x1 + C*D - C*y1 + s1*r1)
    val c = B*B - 2*B*x1 + x1*x1 + D*D - 2*D*y1 + y1*y1 - r1*r1
    quadEqReal(a, b, c) map {r =>
      val x = A*r + B
      val y = C*r + D
      (x, y, r)
    }
  }
  /**
   * Znajduje rozwiazania rzeczywiste rownania kwadratowego postaci
   * ax^2 + bx + c = 0.
   */
  def quadEqReal(a: Double, b: Double, c: Double): Set[Double] = {
    if (a != 0) {
      val a2_inv = .5/a
      val delta = b*b - 4*a*c
      delta match {
        case _ if delta > 0 => {
          val delta_sq = sqrt(delta)
          Set((-b - delta_sq)*a2_inv, (-b + delta_sq)*a2_inv)
        }
        case _ if delta == .0 => Set(-b*a2_inv)
        case _ => Set()
      }
    }
    else {
      // zakladamy, ze b <> 0
      Set(-c/b)
    }
  }

  /**
   * Finds solution of linear equation system of 2 variables:
   * a1*x + b1*y + c1 = 0
   * a2*x + b2*y + c2 = 0
   */
  def linearSys2(a1: Double, b1: Double, c1: Double, a2: Double, b2: Double,
   c2: Double): (Double, Double) = {
    val det = a1*b2 - a2*b1
    ((c2*b1 - c1*b2) / det, (a2*c1 - a1*c2) / det)
  }

  /**
   * Determine parameters of a circle passing through 3 points.
   */
  def c3p(p1: Point, p2: Point, p3: Point): (Point, Double) = {
    // po odjeciu podstawowych rownan (1) - (3) w sposob: (1) - (2) oraz
    // (2) - (3) otrzymujemy uklad rownan liniowych (redukuje sie r oraz
    // wyrazenia kwadratowe wzgledem szukanych x i y)
    val a1 = 2*(p2.x - p1.x)
    val b1 = 2*(p2.y - p1.y)
    val c1 = p2.x*p2.x + p2.y*p2.y - p1.x*p1.x - p1.y*p1.y
    val a2 = 2*(p3.x - p2.x)
    val b2 = 2*(p3.y - p2.y)
    val c2 = p3.x*p3.x + p3.y*p3.y - p2.x*p2.x - p2.y*p2.y
    val det = a1*b2 - a2*b1
    val detx = c1*b2 - c2*b1
    val dety = a1*c2 - a2*c1
    val x = detx / det
    val y = dety / det
    // aby obliczyc r, podstawiamy obliczone x i y do ktoregokolwiek z
    // podstawowych rownan, np. do (1):
    val d1 = p1.x - x
    val d2 = p1.y - y
    val r = sqrt(d1*d1 + d2*d2)
    (Point(x, y), r)
  }

  /**
   * Finds a straight line passing through two points.
   */
  def line2p(p1: Point, p2: Point): (Double, Double, Double) = {
    val y_dif = p1.y != p2.y
    val a = if (y_dif) 1.0 else 0.0
    val b = if (y_dif) (p2.x - p1.x)/(p1.y - p2.y) else 1.0
    val c = -p1.x - b*p1.y
    (a, b, c)
  }
}
