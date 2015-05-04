package common.utils

import org.apache.curator.framework.CuratorFramework
object ZookeeperOps {


  def main (args: Array[String]) {

    val zkUrl = args(0) //"localhost:2181"
    val curator: CuratorUtils = new CuratorUtils(zkUrl)
    val zk: CuratorFramework = curator.newClient()

    if(args.length > 2) {

      val path = args(2)

      args(1) match {
        case "list" => curator.listRecursive(zk, path)
        case "delete" => curator.deleteRecursive(zk, path)
        case "mkdir" => curator.mkdir(zk, path)
        case "adddata" => ???
      }

    } else {
      println("Args not valid: "+ args.length)
    }
  }
}