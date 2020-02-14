import io.circe.{Encoder, Json}
//import io.circe.generic.extras.Configuration
import io.circe.generic.semiauto._
import shapeless.{Coproduct, Generic}
//import io.circe.generic.extras.semiauto._
import io.circe.shapes._

sealed trait Life
case class Animal(sodium: Int) extends Life
case class Plant(nitrogen: Int) extends Life


object Life {
  implicit def encodeAdtNoDiscr[A, Repr <: Coproduct](implicit
    gen: Generic.Aux[A, Repr],
    encodeRepr: Encoder[Repr]
  ): Encoder[A] = encodeRepr.contramap(gen.to)

  /*
  implicit def decodeAdtNoDiscr[A, Repr <: Coproduct](implicit
    gen: Generic.Aux[A, Repr],
    decodeRepr: Decoder[Repr]
  ): Decoder[A] = decodeRepr.map(gen.from)
  */
  implicit val lifeEncoder: Encoder[Life] = deriveEncoder[Life]

  def example: Json = lifeEncoder(Animal(5))

}

object Main {

  def main(args: Array[String]): Unit = println(Life.example)
}
