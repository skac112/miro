package skac.miro.attribs.colors

import skac.miro.attribs._
import scala.math._

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
}
