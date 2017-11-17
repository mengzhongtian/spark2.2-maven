package test

object Test2 {
  def main(args: Array[String]): Unit = {
    val sql = "SELECT " + "area," + "product_id," + "click_count," + "city_infos," + "product_name," + "product_status " + "FROM (" + "SELECT " + "area," + "product_id," + "click_count," + "city_infos," + "product_name," + "product_status," + "ROW_NUMBER() OVER(PARTITION BY area ORDER BY click_count DESC) rank " + "FROM tmp_area_fullprod_click_count " + ") t " + "WHERE rank<=3"
    println(sql)
  }

}
