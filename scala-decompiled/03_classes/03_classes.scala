class Foo(a:Int) {

  def this(s:String) = this(s.length)

}


object Foo {

  def apply(d:Double) = new Foo(2*d.toInt)

}
