object Main {
   def strLengths(l1:List[String]) = {
     for (a1 <- l1) yield a1.length
   }

   def filterNonEmptyByLengths(l1:List[String], l2:List[Int]) = {
     for {
       a1 <- l1
       if a1.nonEmpty
       a2 <- l2
       if a1.length >= a2} yield a1
    }
}
