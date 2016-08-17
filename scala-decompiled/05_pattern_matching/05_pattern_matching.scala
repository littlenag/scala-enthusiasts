object Match {

  def foo(l:List[String]) = l match {

     case a :: b :: c :: Nil => println("has three!")

     case head :: tail => println("head is: " + head)

     case _ => println("huh?")
  }


}