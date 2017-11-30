package util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

import scala.util.control.Exception.allCatch

object DateUtil {
  /**
    * @param A String that looks like "[21/Jul/2009:02:48:13 -0700]"
    */
  def parseDateField(field: String): Option[java.util.Date] = {
    val dateRegex = "\\[(.*?) .+]"
    val datePattern = Pattern.compile(dateRegex)
    val dateMatcher = datePattern.matcher(field)
    if (dateMatcher.find) {
      val dateString = dateMatcher.group(1)
      println("***** DATE STRING" + dateString)
      // HH is 0-23; kk is 1-24
      val dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH)
      dateFormat.parse(dateString)

      val maybeDate = allCatch.opt(dateFormat.parse(dateString))
      maybeDate  // return Option[Date]
    } else {
      None
    }
  }

}
