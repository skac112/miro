package skac.miro.attribs.colors

import skac.miro.attribs._
import scala.math._
import skac.miro._

// import javafx.scene.paint.{Color => FXColor}

object Color {
  def red(Val: Double) = new Color(Val, 0.0, 0.0)
  def green(Val: Double) = new Color(0.0, Val, 0.0)
  def blue(Val: Double) = new Color(0.0, 0.0, Val)
  // def alpha(Val: Double) = new Alpha(Val)

  // def hsb2red(Hue: Double, Sat: Double, Bright: Double, Alpha: Double = 1.0) = {
  //   val fxcolor = FXColor.hsb(Hue, Sat, Bright)
  //   fxcolor.getRed
  // }
  //
  // def hsb2green(Hue: Double, Sat: Double, Bright: Double, Alpha: Double = 1.0) = {
  //   val fxcolor = FXColor.hsb(Hue, Sat, Bright)
  //   fxcolor.getGreen
  // }
  //
  // def hsb2blue(Hue: Double, Sat: Double, Bright: Double, Alpha: Double = 1.0) = {
  //   val fxcolor = FXColor.hsb(Hue, Sat, Bright)
  //   fxcolor.getBlue
  // }

  def normalize(Val: Double) = Val match {
    case v if v > 1.0 => 1.0
    case v if v < 0.0 => 0.0
    case v => v
  }

  def normalizeHue(Val: Double) = Val - (Val / 360.0).floor * 360

  def red = Color(1.0, .0, .0)
  def green = Color(.0, 1.0, .0)
  def blue = Color(.0, .0, 1.0)

  lazy val pi_3_inv = 3.0 / Pi

  def hsl(hue: Angle, sat: Double, l: Double) = {
    // chroma
    val C = (1 - abs(2*l - 1.0))*sat
    val hue_p = hue.value * pi_3_inv
    val X = C*(1 - abs(hue_p % 2.0 - 1.0))
    val (r1, g1, b1) = hue_p match {
      case _ if hue_p >= 0 && hue_p < 1 => (C, X, .0)
      case _ if hue_p >= 1 && hue_p < 2 => (X, C, .0)
      case _ if hue_p >= 2 && hue_p < 3 => (.0, C, X)
      case _ if hue_p >= 3 && hue_p < 4 => (.0, X, C)
      case _ if hue_p >= 4 && hue_p < 5 => (X, .0, C)
      case _ if hue_p >= 5 => (C, .0, X)
    }
    val m = l - 0.5 * C
    Color(r1 + m, g1 + m, b1 + m)
  }
}

import Color._

case class Color(r: Double, g: Double, b: Double, a: Double = 1.0) extends Fill {

  // val FXColor = new FXColor(Red, Green, Blue, Alpha)
  // def this(FXColor: FXColor) = this(FXColor.getRed, FXColor.getGreen, FXColor.getBlue, FXColor.getOpacity())
  // def Hue = FXColor.getHue
  // def Sat = FXColor.getSaturation
  // def Bright = FXColor.getBrightness
  def +:(other: Color) = Color(normalize(r + other.r), normalize(g + other.g), normalize(b + other.b), a)
  def -:(other: Color) = Color(normalize(r - other.r), normalize(g - other.g), normalize(b - other.b), a)

  override lazy val toString = {
    // konwersja wartosci Double w zakresie <0; 1> na dwucyfrowy lancuch heksadecymalny
    val to_hex = (value: Double) => f"${round(value*255)}%02x"
    "#" + (Seq(r, g, b) map to_hex reduceLeft {_ + _})
  }

  private lazy val M =  List(r, g, b).max

  private lazy val m = List(r, g, b).min

  /**
   * Chroma
   */
  private lazy val C = M - m

  /**
   * Wartosc hue w modelu HSL. Zrodlo:
   * https://en.wikipedia.org/wiki/HSL_and_HSV
   */
  lazy val hue: Angle = {
    val hue_60 = (r, g, b, C) match {
      case (_, _, _, .0) => .0
      case _ if M == r => (g - b) / C % 6
      case _ if M == g => (b - r) / C + 2
      case _ => (r - g) / C + 4
    }
    Angle(hue_60 * Pi / 3.0)
  }

  /**
   * Wartosc lightness (L) w modelu HSL. Zakres: <0; 1>. Zrodlo:
   * https://en.wikipedia.org/wiki/HSL_and_HSV
   */
  lazy val l = 0.5 * (M + m)

  /**
   * Nasycenie (S) w modelu HSL. Zakres: <0; 1>. Zrodlo:
   * https://en.wikipedia.org/wiki/HSL_and_HSV
   */
  lazy val sat: Double = l match {
    case 1.0 => .0
    case _ => C / (1.0 - abs(2*l - 1.0))
  }
}
