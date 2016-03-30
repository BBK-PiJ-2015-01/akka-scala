package router

sealed trait PayloadMessage
case object Perform extends PayloadMessage
case object Complete extends PayloadMessage


sealed trait RouterSystemMessage
case object Initialize extends RouterSystemMessage
case object Finalize extends RouterSystemMessage
