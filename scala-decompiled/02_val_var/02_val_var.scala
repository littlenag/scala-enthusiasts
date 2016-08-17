case class Foo(x:Int)

case class Baz(y:Int)

object Baz {
  def twice(baz:Baz) = 2*baz.y
}

class Bar(var a:Int, val b:String, z:Double) {

  var c = 5.0d

  lazy val d = 2*a*c

}

object Main {
  new Bar(0, "foo", 0.0d)
}
