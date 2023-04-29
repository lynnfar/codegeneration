import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeGeneration {

    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://192.168.1.189:3308/blog?serverTimezone=GMT%2B8", "root", "lynn1234@" )
                .globalConfig(builder -> {
                    builder.author("lynnfar") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()//禁止打开输出目录，默认为：true
                            .commentDate("yyyy-MM-dd")//注释日期
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(System.getProperty("user.dir")+"/src/main/java");
                })

                .packageConfig(builder -> {
                    builder.parent("com.lynnfar") // 设置父包名
                            .controller("controller")   //Controller 包名 默认值:controller
                            .entity("entity")           //Entity 包名 默认值:entity
                            .service("service")         //Service 包名 默认值:service
                            .serviceImpl("service.impl")//自定义serviceImpl包名
                            .mapper("mapper")           //Mapper 包名 默认值:mapper
                            .other("model")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                    //默认存放在mapper的xml下
                })
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                    // DTO、VO
                    customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
                    customFile.put("VO.java", "/templates/entityVO.java.ftl");

                    consumer.customFile(customFile);
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_sort","t_pages") // 设置需要生成的表名 可边长参数“user”, “user1”
                            .addTablePrefix("t_") // 设置过滤表前缀
                            .serviceBuilder()//service策略配置
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()// 实体类策略配置
                            .idType(IdType.AUTO)//主键策略
                            .addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置
                            .addTableFills(new Property("update_time", FieldFill.INSERT_UPDATE))
                            .enableLombok() //开启lombok
                            //.logicDeleteColumnName("deleted")// 说明逻辑删除是哪个字段
                            .enableTableFieldAnnotation()// 属性加上注解说明
                            .controllerBuilder() //controller 策略配置
                            .formatFileName("%sController")
                            .enableRestStyle() // 开启RestController注解
                            .mapperBuilder()// mapper策略配置
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()//@mapper注解开启
                            .formatXmlFileName("%sMapper");
                })

                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                //.templateEngine(new FreemarkerTemplateEngine())
                .templateEngine(new CustomTemplateEngine())
                .execute();

    }
}



