object Match {

  case class Bar(i:Int, d:Double)

  def doMatch(any:Any) = any match {
     case a :: b :: c :: Nil => 3
     case head :: tail => 2
     case Bar(i,d) => 1
     case _ => 0
  }
}
