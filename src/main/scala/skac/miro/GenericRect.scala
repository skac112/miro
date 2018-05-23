package skac.miro

/**
 * Abstrakcyjny prostokat. Punkt (0, 0) odpowiada lewemu gornemu naroznikowi (lub jego odpowiednikowi po uwzglednieniu
 * kata obrotu).
 * @author slawek
 */
trait GenericRect extends GenericPolygon {
  def width: Double
  def height: Double
  def rotation: Double
  override lazy val points = Seq(Point(width, .0), Point(width, height),
   Point(.0, height)) map {p: Point => {p rot rotation}}

  /**
   * Lewy gorny naroznik (oznaczenie naroznikow bez uwzglednienia obrotu) - lewym gornym naroznikiem jest odp. naroznik przed ew. obrotem
   */
  val tl = ori

  /**
   * Prawy gorny naroznik (oznaczenie naroznikow bez uwzglednienia obrotu).
   */
  lazy val tr = points(0)

  /**
   * Prawy dolny naroznik (oznaczenie naroznikow bez uwzglednienia obrotu).
   */
  lazy val br = points(1)

  /**
   * Lewy dolny naroznik (oznaczenie naroznikow bez uwzglednienia obrotu).
   */
  lazy val bl = points(2)

  /**
   * Srodek gornego boku (oznaczenie bokow bez uwzgledniania obrotu).
   */
  lazy val tc = points(1) * .5

  /**
   * Srodek prawego boku (oznaczenie bokow bez uwzgledniania obrotu).
   */
  lazy val rc = (points(1) + points(2)) * .5

  /**
   * Srodek dolnego boku (oznaczenie bokow bez uwzgledniania obrotu).
   */
  lazy val bc = (points(2) + points(3)) * .5

  /**
   * Srodek lewego boku (oznaczenie bokow bez uwzgledniania obrotu).
   */
  lazy val lc = points(3) * .5

  /**
   * Punkt srodkowy
   */
  lazy val c = points(2) * .5

  override lazy val len = 2 * width + 2 * height
  override def bounds = Bounds.forPts(Set(tl, tr, tc, rc))
}
