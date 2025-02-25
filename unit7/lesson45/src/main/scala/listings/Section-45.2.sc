import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

def futureA = Future { Thread.sleep(42); 42 }
// futureA: scala.concurrent.Future[Int]

def futureB = Future(123/0)
// futureB: scala.concurrent.Future[Int]

Future.firstCompletedOf(Seq(futureA, futureB))
// val res0: scala.concurrent.Future[Int] = Future(Failure(java.lang.ArithmeticException: / by zero))


val seqAB = Future.find(Seq(futureA, futureB))(_ > 10)
// val seqAB: scala.concurrent.Future[Option[Int]] = Future(<not completed>)

seqAB
// val seqAB: scala.concurrent.Future[Option[Int]] = Future(Success(Some(42)))

Future.find(Seq(futureA, futureB))(_ > 100)
// val res0: scala.concurrent.Future[Option[Int]] = Future(Success(None))
// No Future instance in the sequence produces a value bigger than 100!

def futureC = Future(123)
// def futureC: scala.concurrent.Future[Int]

Future.sequence(Seq(futureA, futureB, futureC))
// val res0: scala.concurrent.Future[Seq[Int]] = Future(Failure(java.lang.ArithmeticException: / by zero))

val seqAC = Future.sequence(Seq(futureA, futureC))
// val seqAC: scala.concurrent.Future[Seq[Int]] = Future(<not completed>)

seqAC
// val seqAC: scala.concurrent.Future[Seq[Int]] = Future(Success(List(42, 123)))
