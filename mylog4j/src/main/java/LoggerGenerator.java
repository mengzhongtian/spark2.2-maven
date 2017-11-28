import org.apache.log4j.Logger;

/**
 * 模拟日志产生
 */

/**
 * 0.添加log4j.properties
 *
 *
 * 1.添加依赖：
 * <dependency>
 * <groupId>org.apache.flume.flume-ng-clients</groupId>
 * <artifactId>flume-ng-log4jappender</artifactId>
 * <version>1.6.0</version>
 * </dependency>
 *
 *
 * 2.提交运行：java -classpath mylog4j.jar LoggerGenerator
 *
 *
 */
public class LoggerGenerator {

    private static Logger logger = Logger.getLogger(LoggerGenerator.class.getName());

    public static void main(String[] args) throws Exception {

        int index = 0;
        while (true) {
            Thread.sleep(1000);
            logger.info("value : " + index++);
        }
    }
}
