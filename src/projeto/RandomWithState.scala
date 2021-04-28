package projeto

trait RandomWithState {

  def nextInt(n: Int): (Int, RandomWithState)
}
