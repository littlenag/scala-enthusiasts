implicit val y = 5

def f(x:Int)(implicit ev:Int) = x + ev

// implicits can have scope
// scala compiler find the "best match" by ranking implicits

// implicit values can be generated on demand

implicit def z = 5

// implicit rules can depend on implicit parameters themselves!

implicit def s(implicit ev:String) : Int = 5 + ev.length

// And of course all of this can be generic!

trait Show[T] {
  def show(t:T):String
}

implicit def showInt : Show[Int] = new Show[Int] {
  def show(t:Int):String = t.toString
}

implicit def showString : Show[String] = new Show[String] {
  def show(t:String):String = t
}

implicit def showTuple[A,B](implicit aev:Show[A], bev:Show[B]) : Show[(A,B)] = new Show[(A,B)] {
  def show(t:(A,B)):String = s"[[${aev.show(t._1)} ++ ${bev.show(t._2)}]]"
}

def show[T](t:T)(implicit ev:Show[T]) = ev.show(t)

show(1)
show("abc")
show((1,"abc"))
show(1,"abc")
show(2,(3,"def"))
