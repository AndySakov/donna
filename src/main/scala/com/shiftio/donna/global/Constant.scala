package com.shiftio.donna.global

object Constant extends Enumeration {
  type Constant = Value
  val CONFIG_DIR,
    PLUGIN_DIR,
    USER_DIR,
    APP_DIR,
    LOCK_FILE,
    CONFIG_FILE_MAIN = Value
}
