# miro
Library for vector graphics with SVG support and some DSL goodies.

Miro is a library for handling vector graphics in an effective and expressive way, thanks to using Scala. It is designed for using primarily with SVG. As of today (2017-04-08) it is still in early stage and has only limited support for SVG, but this will change soonly.

It is partly inspired by Haskell's <a href="http://projects.haskell.org/diagrams/">diagrams</a> - for example, shapes are treated as equal if they differ only by location, and also most classes are immutable.

Scala allows for some nice DSL-like features, for example when handling points or transformations:
~~~ scala
import skac.miro._
import skac.miro.Point
val p1 = Point(100, 100)
val p2 = Point(200, 200)
val p3 = (p1 + p2) * 2
~~~

~~~ scala
p3: skac.miro.Point = Point(600.0,600.0)
~~~

~~~ scala
-p3
~~~

~~~ scala
res0: skac.miro.Point = Point(-600.0,-600.0)
~~~

~~~ scala
val rotation = 45.deg
~~~

~~~ scala
p rot rotation
~~~

~~~ scala
res1: skac.miro.Point = Point(5.6843418860808015E-14,848.5281374238571)
~~~

It is used by <a href="https://github.com/skac112/fractalizer">fractalizer</a>.









