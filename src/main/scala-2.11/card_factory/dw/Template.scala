package card_factory.dw

/**
 * Created by lorilan on 6/30/15.
 */
object Template {

  val templatehtml =
    """<html><head>
      |    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
      |    <script src="jquery.boxfit.js"></script>
      |  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
      |  <style type="text/css">
      |  @media print { @page { size: landscape; margin: 0.2cm } }
      |  table
      |  {
      |  border-collapse:collapse;
      |  page-break-after: always;
      |  margin-left: auto ;
      |  margin-right: auto ;
      |  }
      |  table, th, td
      |  {
      |  border: 1px solid transparent;
      |  }
      |  tr
      |  {
      |  height:4.6cm;
      |  }
      |
      |  tr.hruler
      |  {
      |  height: 1px;
      |  border: 0px;
      |  background-color:transparent;
      |  }
      |
      |  td
      |  {
      |  width:7.8cm;
      |  padding:0.2cm;
      |  position: relative;
      |  z-index: 1;
      |  }
      |  td.hruler
      |  {
      |  padding:0px;
      |  }
      |  td.vruler
      |  {
      |  width: 1px;
      |  border: 0px;
      |  background-color:transparent;
      |  padding:0px;
      |  }
      |
      |  ul
      |  {
      |  padding-top: 0px;
      |  margin-top: 0px;
      |  }
      |  div.descript
      |  {
      |  height:4.5cm;
      |  width:7.8cm;
      |  text-align: left;
      |  }
      |  div.name
      |  {
      |  text-align: center;
      |  width:100%;
      |  font-weight:bold;
      |  font-size: 22px;
      |  margin-top:0px;
      |  }
      |
      |  div.leftpic
      |  {
      |      width:6mm;
      |      height:6mm;
      |      float:left;
      |  }
      |  div.rightpic
      |  {
      |      width:6mm;
      |      height:6mm;
      |      float:right;
      |  }
      |
      |  img.suit
      |  {
      |      width:6mm;
      |  }
      |
      |  div.left
      |  {
      |  width:45%;
      |  float:left;
      |  padding-right:5%;
      |  }
      |
      |  div.right
      |  {
      |  width:45%;
      |  float:right;
      |  padding-left:5%;
      |
      |  }
      |  div.footer
      |  {
      |  padding-top:0.1px;
      |
      |  width:100%;
      |  clear:both;
      |  border-color:grey;
      |  }
      |  img.color
      |  {
      |  height:100%;
      |  width:100%;
      |  }
      |  div.vruler_show
      |  {
      |  height:10px;
      |  width:1px;
      |  }
      |  div.vruler_hide
      |  {
      |  height:165px;
      |  width:1px;
      |  }
      |  div.hruler_left
      |  {
      |  height:1px;
      |  width:10px;
      |  float:left;
      |  }
      |  div.hruler_right
      |  {
      |  height:1px;
      |  width:10px;
      |  float:right;
      |  }
      |
      |  </style>
      |  </head>
      |  <body>
      |  <table><tr><td></td></tr></table>""".stripMargin

  val templatehtmlend="</body></html>"

  def cross(h : String, v : String) =
    s"""<div class="vcross" style="{$h}:-0.75px; {$v}:-6px;"></div>
       |<div class="hcross" style="{$h}:-6px; {$v}:-0.75px;"></div>""".stripMargin

  val rightbottomcrosshtml = cross("right", "bottom")
  val leftbottomcrosshtml = cross("left", "bottom")
  val righttopcrosshtml = cross("right", "top")
  val lefttopcrosshtml = cross("left", "top")


  val vrulershtml =
    s"""<tr class="vrulers">
        |  <td class="corner">$rightbottomcrosshtml</td>
        |  <td class="vrulers">$rightbottomcrosshtml</td>
        |  <td class="vrulers">$rightbottomcrosshtml</td>
        |  <td class="vrulers">$rightbottomcrosshtml</td>
        |  <td class="corner"></td>
        |</tr>""".stripMargin

  val beginrow = s"""<td class="hrulers">$rightbottomcrosshtml</td>"""
  val endrow = """<td class="hrulers"></td>"""


  val vruler =
  """<td class="vruler">
    |<div class="vruler_show"><img src="img/color.png" class="color"/></div>
    |<div class="vruler_hide"></div>
    |<div class="vruler_show"><img src="img/color.png" class="color"/></div>
    |</td>
  """.stripMargin

  def hruler(columns : Int) : String = {

    def aux(i : Int, acc : String) : String =
    if(i >= columns ) acc
    else aux(i + 1,
      acc + """<td class="hruler">
        | <div class="hruler_left"><img src="img/color.png" class="color"/></div>
        | <div class="hruler_right"><img src="img/color.png" class="color"/></div>
        | </td>
        | <td class="vruler"></td>
      """.stripMargin
    )

    """<tr class="hruler">
      | <td class="vruler">
      | </td>
    """.stripMargin +
    aux(0, "") + "</tr>\n" +
      "<!-- end of hruler -->"

  }

  val credits = "Monsters from Dungeon World by Adam Sage and Sage LaTorra and licensed under CC-BY. Images by am_(anmcarrow@gmail.com), licensed under CC-BY-SA. Layout by Vasiliy Shapovalov and completely free to use as you like. French translation by Kobal, Maxime Moraine, The Judge and Virgile Malaquin - ( http://dungeonworld-fr.blogspot.fr/p/blog-page_25.html )"



}
