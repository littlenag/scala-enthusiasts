object Main extends App {

  def greeting = "hello"
  def place = "world"

  def greetPlace(g:String)(p:String) = s"$g $p!"

  def makeGreeting(a:String = greeting, b:String = place) = greetPlace(a)(b)

  println(greetPlace(greeting)(place))
  println(makeGreeting())
}
