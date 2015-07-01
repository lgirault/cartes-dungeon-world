package card_factory

import card_factory.dw.Monster

object Front {

  import java.io._


  def main(args : Array[String]): Unit ={

    val f = new File(args(0))
    if(! f.exists()) {
      println(s"$f does not exists")
      sys.exit()
    }
    else{
      val ms = Monster.fromFile(f)
      val fw = new FileWriter(f.getAbsolutePath .replaceAllLiterally(".xml", ".html"))
      fw.write(Monster.toHtml(ms))
      fw.close()
    }
  }




}
