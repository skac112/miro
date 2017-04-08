# miro
Library for handling vector graphics with SVG support and some DSL goodies.

Miro is a library for handling vector graphics in an effective and expressive way, thanks to using Scala. It is designed for using primarily with SVG. As of today (2017-04-08) it is still on early stage and has only limited support for SVG, but this will change soonly.

It is partly inspired by Haskell's diagrams - for example, shapes are treated as equal if thay differ only by location, and also most of classes are immutable.

Scala allows for some nice DSL-like features, for example when handling points or transformations:

import skac.miro._

import skac.miro.Point

val p1 = (100, 100)

val p2 = (200, 200)

val p3 = (p1 + p2) * 2

p3: skac.miro.Point = Point(600.0,600.0)

-p3

res0: skac.miro.Point = Point(-600.0,-600.0)

val rotation = 45.deg

p rot rotation

res0: skac.miro.Point = Point(-600.0,-600.0)








