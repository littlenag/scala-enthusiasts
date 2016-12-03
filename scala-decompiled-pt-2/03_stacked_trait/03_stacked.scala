// From http://stackoverflow.com/questions/32511722/stackable-traits-pattern-methods-implementation-needs-abstract-override-mo

trait T1 {
  def foobar() = 1
}

trait T2 {
  def foobar() = 2
}

class B extends T2 {
  override def foobar() = 42
}

class A extends B {
  override def foobar() = super.foobar()
}
