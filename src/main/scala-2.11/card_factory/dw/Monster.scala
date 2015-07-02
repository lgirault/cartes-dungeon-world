package card_factory.dw

import java.io.File

import scala.xml.{NodeSeq, Node, XML}

case class Monster
( name : String,
  setting : String,
  tags : Seq[String],
  attack : Option[String],
  damage : Option[String],
  hp : Option[String],
  armor : Option[String],
  attackTags : Seq[String],
  qualities : Seq[String],
  description : String,
  instinct : Option[String],
  moves : Seq[String])

object Monster {

  implicit def nodeSeqToOption( ns : NodeSeq) : Option[String] =
    ns map (_.text) match {
      case Seq(i) => Some(i)
      case Nil => None
      case _ => sys.error("Should have only one or zero value :" + ns.mkString(","))
    }

  def fromXml(monster : Node) : Monster =
      Monster((monster \ "name").text ,
        (monster \ "setting").text ,
        monster \ "tagList" \ "tag" map (_.text),
        monster \ "attack",
        monster \ "damage",
        monster \ "hp",
        monster \ "armor",
        monster \ "attackTagList" \ "tag" map (_.text),
        monster \ "qualityList" \ "quality" map (_.text),
        (monster \ "description").text,
        monster \ "instinct" ,
        monster \ "moveList" \ "move" map (_.text)
      )

  def fromFile(file : File) : Seq[Monster] = {
    val root = XML.loadFile(file)
    if(root.label == "monsterList")
      root \ "monster" map fromXml
    else if(root.label == "monster")
      Seq(fromXml(root))
    else Seq()
  }

  val settingIcon = Map(
    "Habitants des Cavernes" -> "dung.png",
    "Tréfonds" -> "lower.png" ,
    "Expériences Contre Nature"-> "twisted.png",
    "Habitants du Royaume"-> "folks.png",
    "Hordes Voraces"-> "hordes.png",
    "Forces Planaires"-> "planar.png",
    "Habitants du Marais"-> "swamp.png",
    "Légion des Morts-Vivants"-> "dead.png",
    "Bois Obscurs"-> "woods.png"
  )

  def getIcon(setting : String ) =
    "img/" + settingIcon(setting)

  def mkDesc(m : Monster, elemNum : Int) : String = {
    val img = getIcon(m.setting)

    s"""<div class="descript" id="descript$elemNum">
        |<div class="leftpic"></div>
        |<div class="rightpic">
        |   <img class="suit" src="$img">
        |</div>
        |<div class="name">${m.name}</div>
        | ${m.description}
        |</div>""".stripMargin

  }

  def mkMech(m : Monster, elemNum : Int) : String = {
    val img = getIcon(m.setting)
    val leftText =
      m.instinct.map(str => s"Instinct: $str<br/>").getOrElse("") +
        (if(m.tags.nonEmpty) m.tags.mkString("<em>", ", ", "</em></br>")
        else "") +
        (if(m.qualities.nonEmpty) "Capacités spéciales : " + m.qualities.mkString(", ")
         else "")
    
    val rightText = 
      m.hp.map(str => s"PV : $str").getOrElse("") +
      m.armor.map(str => s"Armure : $str ").getOrElse("") +
        (if(m.hp.nonEmpty || m.armor.nonEmpty) "<br/>"
        else "") +
      m.attack.map(str => s"($str) ").getOrElse("") +
      m.damage.getOrElse("") +
        (if(m.attack.nonEmpty || m.damage.nonEmpty) "<br/>"
        else "") +
        (if(m.attackTags.nonEmpty) m.attackTags.mkString("<em>", ", ", "</em></br>")
          else "")

    val footerText =
      if(m.moves.nonEmpty) m.moves.mkString("<ul><li>", "</li><li>", "</li></ul>")
    else ""


      s""" <div class="descript" id="mech$elemNum">
        |    <div class="leftpic"></div><div class="rightpic"><img class="suit" src="$img"></div>
        |    <div class="name">${m.name}</div>
        |    <div class="left">$leftText</div>
        |    <div class="right">$rightText</div>
        |    <div class="footer">$footerText</div>
        |</div>
      """.stripMargin
  }


  def mkScript(num: Int) =
    List.range(1, num + 1).map{
      n =>
      "$" + s""" ("#descript$n").boxfit({align_center: false, align_middle: false, multiline: true, maximum_font_size: 19});\n""" +
        "$" + s""" ("#mech$n").boxfit({align_center: false, align_middle: false, multiline: true, maximum_font_size: 19});\n"""
      }.mkString


  def toHtml(monsters : Seq[Monster]): String = {
    val rows = 4
    val columns = 3

    val num_per_table = rows * columns
    val tables_needed = (monsters.length.toDouble / num_per_table).ceil.toInt


    val (descs, mechs) = (monsters.zipWithIndex map { case (m, cpt) => (mkDesc(m, cpt + 1), mkMech(m, cpt + 1))}).unzip


    val script = mkScript(mechs.size)

    import Template._

    val hruler_ =hruler(columns)


    def tableHtml(adjust: Int => Int, cellContents : Array[String])(table: Int): String = {
      val html = new StringBuilder
      html append "<table>"
      html append hruler_

      List.range(table * rows, table * rows + rows).foreach{
        row =>
          html append "<tr>"
          html append vruler

          List.range(0, columns). foreach {
            column =>
              val h = try cellContents(row * columns + adjust(column))
              catch {
                case _: Exception => ""
              }

              html append s"<td>$h</td>$vruler"
          }

          html append "</tr>"
          html append hruler_
      }

      html append "</table>"

      html.toString()
    }


    def descTableHtml = tableHtml(identity, descs.toArray) _
    def mechTableHtml = tableHtml(c => columns - 1 - c, mechs.toArray) _

    val tables = List.range(0, tables_needed).map{ i =>
      descTableHtml(i) + mechTableHtml(i)
    }.mkString

    templatehtml + "<center>" + tables + "</center>" + credits + "<script>" + script +"</script>" + templatehtmlend

  }
}
