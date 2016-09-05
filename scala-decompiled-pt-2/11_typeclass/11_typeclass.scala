trait Show[T] {
  def show(t:T) : String
}

object Main {

  implicit val ShowInt = new Show[Int] {
    def show(i:Int) = i.toString
  }

  implicit def ShowString = new Show[String] {
    def show(s:String) = s
  }

  implicit object ShowDouble extends Show[Double] {
    def show(d:Double) = d.toString
  }

  implicit def ShowTuple[A,B](implicit aev:Show[A], bev:Show[B]) : Show[(A,B)] = new Show[(A,B)] {
    def show(t:(A,B)):String = s"[[${aev.show(t._1)} ++ ${bev.show(t._2)}]]"
  }

  def show[T](t:T)(implicit ev:Show[T]) = ev.show(t)

  val show_1 = show(1)
  val show_buzzle = show("buzzle")
  val show_20d = show(2.0d)
  val show_tuple = show((1, ("foozle", 2.0d)))
}
