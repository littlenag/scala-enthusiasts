class Printer(val pages:Int) {
  def print:String = s"Printing $pages pages"
}

object ConstantPrinter extends Printer(-1) {
  override def print = "constant"
}
