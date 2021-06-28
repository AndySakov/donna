package com.shiftio.donna.api

import better.files.File
import better.files.File._
import com.shiftio.donna.global.Constant._
import com.shiftio.donna.global.OS._

object Constance {

  def _OS_ : OS = _OS_NAME_

  def getConstant(const: Constant): Option[File] = {
    val app_dir = Some(home / ".donna")
    val config_dir = _OS_ match {
      case MAC_OS_X => Some(home / "Library" / "Application Support" / "Donna" / "Props")
      case UNKNOWN => None
      case _ => app_dir.map(c => c / "props")
    }
    const match {
      case APP_DIR => app_dir
      case CONFIG_DIR =>
        config_dir
      case PLUGIN_DIR =>
        _OS_ match {
          case MAC_OS_X => Some(home / "Library" / "Application Support" / "Donna" / "Plugs")
          case WINDOWS => app_dir.map(c => c / "plugs")
          case UNKNOWN => None
          case _ => Some(root / "usr" / "lib" / "donna" / "plugs")
        }
      case USER_DIR => Some(home)
      case LOCK_FILE => app_dir.map(c => c / "donna.lock")
      case CONFIG_FILE_MAIN => config_dir.map(c => c / "donna-main.json")
    }
  }
}
