package com.xh.mongodb.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * MybatisPluginCodeGeneral
 * <p>
 * mybatis 插件代码生成器
 * <p>
 * 参照链接1 : https://mp.baomidou.com/guide/generator.html
 * 参照链接2 : https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-generator/src/main/java/com/baomidou/mybatisplus/generator/engine/AbstractTemplateEngine.java
 *
 * @author xiaohe
 */
@Slf4j
public class MybatisPluginCodeGenerate {

    /**
     * 要生成对应代码的表名与对应的模块名称
     * <p>
     * 例如日志模块下的日志类 : <code>sys_log:log</code> 即 表名:模块名
     * </p>
     * <p>
     * 如果没有模块就直接写表名
     * </p>
     */
    private static final String[] TABLES = {"mongo_article", "mongo_article_answer"};

    // =========================== 包相关 ================================

    /**
     * 包名
     */
    private static final String PACKAGE_NAME = "com.xh.mongodb";

    /**
     * 类名的创建者
     */
    private static final String CREATE_USER = "xiaohe";

    /**
     * 模块名称 -- 如果多模块不使用这个的话会生成到父项目目录中
     * <p>
     * 记得前面加斜杠
     * </p>
     */
    private static final String MODEL_NAME = "";


    // =========================== 数据库相关 ================================

    /**
     * 链接数据库 url
     */
    private static final String DB_URL = "jdbc:mysql://47.98.142.170:3306/dream?useUnicode=true&characterEncoding=utf8&useSSL=false&autoReconnect=true";

    /**
     * 数据库驱动
     */
    private static final String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";

    /**
     * 数据库用户名
     */
    private static final String DB_USER = "root";

    /**
     * 数据库密码
     */
    private static final String DB_PASSWORD = "accp";

    public static void main(String[] args) {
        for (String tableInfo : TABLES) {
            if (StringUtils.isBlank(tableInfo)) {
                log.warn("there has table info is blank.");
                continue;
            }
            String table;
            String module;

            if (tableInfo.contains(":")) {
                table = tableInfo.split(":")[0];
                module = tableInfo.split(":")[1];
            } else {
                table = tableInfo;
                module = "";
            }

            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();

            // 全局配置
            GlobalConfig gc = new GlobalConfig();
            String projectPath = System.getProperty("user.dir") + MybatisPluginCodeGenerate.MODEL_NAME;
            gc.setOutputDir(projectPath + "/src/main/java");
            gc.setAuthor(MybatisPluginCodeGenerate.CREATE_USER);
            gc.setOpen(false);
            // gc.setSwagger2(true); 实体属性 Swagger2 注解
            mpg.setGlobalConfig(gc);

            // 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl(MybatisPluginCodeGenerate.DB_URL);
            // dsc.setSchemaName("public")
            dsc.setDriverName(MybatisPluginCodeGenerate.DB_DRIVER_NAME);
            dsc.setUsername(MybatisPluginCodeGenerate.DB_USER);
            dsc.setPassword(MybatisPluginCodeGenerate.DB_PASSWORD);
            mpg.setDataSource(dsc);

            // 包配置
            PackageConfig pc = new PackageConfig();
            if (StringUtils.isNotBlank(module)) {
                pc.setModuleName(module);
            }
            pc.setParent(MybatisPluginCodeGenerate.PACKAGE_NAME);
            mpg.setPackageInfo(pc);

            // 自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    // to do nothing
                }
            };

            // 如果模板引擎是 freemarker
            String templatePath = "/templates/mapper.xml.ftl";
            // 如果模板引擎是 velocity
            // String templatePath = "/templates/mapper.xml.vm";

            // 自定义输出配置
            List<FileOutConfig> focList = new ArrayList<>();
            // 自定义配置会被优先输出
            focList.add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    String outputPath = projectPath + "/src/main/resources/mapper/";
                    if (StringUtils.isNotBlank(pc.getModuleName())) {
                        outputPath = outputPath + pc.getModuleName() + "/";
                    }
                    outputPath = outputPath + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                    return outputPath;
                }
            });

            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);

            // 配置模板
            TemplateConfig templateConfig = new TemplateConfig();

            // 配置自定义输出模板
            //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
            // templateConfig.setEntity("templates/entity2.java")
            // templateConfig.setService()
            // templateConfig.setController()

            templateConfig.setXml(null);
            mpg.setTemplate(templateConfig);

            // 策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);
            // strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!")
            strategy.setEntityLombokModel(true);
            strategy.setRestControllerStyle(true);
            // 公共父类
            // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!")
            // 写于父类中的公共字段
            // strategy.setSuperEntityColumns("id")
            strategy.setInclude(table);
            strategy.setControllerMappingHyphenStyle(true);
            // strategy.setTablePrefix(pc.getModuleName() + "_")
            mpg.setStrategy(strategy);
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
            mpg.execute();
        }
    }
}
