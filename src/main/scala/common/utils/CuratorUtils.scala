package common.utils

/**
 * Created by v765849 on 5/1/15.
 */

import org.apache.curator.framework.{CuratorFrameworkFactory, CuratorFramework}

import scala.collection.JavaConversions._

import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.zookeeper.KeeperException

class CuratorUtils(ZK_URL: String) {

  private val ZK_CONNECTION_TIMEOUT_MILLIS = 15000
  private val ZK_SESSION_TIMEOUT_MILLIS = 60000
  private val RETRY_WAIT_MILLIS = 5000
  private val MAX_RECONNECT_ATTEMPTS = 3

  def newClient(zkUrlConf: String = "oncue.rna.zookeeper"): CuratorFramework = {
    println("ZKCONNECT ==== " + ZK_URL)
    val zk = CuratorFrameworkFactory.newClient(ZK_URL,
      ZK_SESSION_TIMEOUT_MILLIS, ZK_CONNECTION_TIMEOUT_MILLIS,
      new ExponentialBackoffRetry(RETRY_WAIT_MILLIS, MAX_RECONNECT_ATTEMPTS))
    zk.start()
    zk
  }

  def mkdir(zk: CuratorFramework, path: String) {
    if (zk.checkExists().forPath(path) == null) {
      try {
        zk.create().creatingParentsIfNeeded().forPath(path)
      } catch {
        case nodeExist: KeeperException.NodeExistsException =>
        // do nothing, ignore node existing exception.
        case e: Exception => throw e
      }
    }
    ()
  }

  def listRecursive(zk: CuratorFramework, path: String) {
    if (zk.checkExists().forPath(path) != null) {
      for (child <- zk.getChildren.forPath(path)) {
        listRecursive(zk, path + "/" + child)
      }
      println("Path: " + path)
    }
    ()
  }

  def deleteRecursive(zk: CuratorFramework, path: String) {
    if (zk.checkExists().forPath(path) != null) {
      for (child <- zk.getChildren.forPath(path)) {
        deleteRecursive(zk, path + "/" + child)
      }
      println("Path: " + path)
      zk.delete().forPath(path)
    }
    ()
  }

}
