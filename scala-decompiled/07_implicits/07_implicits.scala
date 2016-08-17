trait Print[T] {

  def print(t:T) : String

}

object Main {

  implicit val printInt = new Print[Int] { def print(i:Int) = i.toString }
  implicit def printString = new Print[String] { def print(s:String) = s }

  implicit object printDouble extends Print[Double] { 
    def print(d:Double) = d.toString
  }

  def foo[T](t:T)(implicit ev:Print[T]) = ev.print(t)

  def doStuff = {
    foo(1)
    foo("asdf")
    foo(2.0d)
    true
  }
}