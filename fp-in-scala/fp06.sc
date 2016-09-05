import scalaz._, Scalaz._, effect._, IO._

val printLetters =
  List("a", "b", "c").
    map(_.toUpperCase).
    map(putStrLn _)

def evalEffects(effects:List[IO[_]] = printLetters) = effects.map(_.unsafePerformIO)

// fp02.printLetters.map(_.unsafePerformIO)

//Scala makes heavy use of:
//map, flatMap, filter, collect, fold
