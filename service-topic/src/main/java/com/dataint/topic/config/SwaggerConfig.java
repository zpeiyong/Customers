package com.dataint.topic.config;

import com.dataint.cloud.common.model.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/** * SwaggerConfig */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static List<Parameter> setHeader(){
		// admin
//		UserVO user = new UserVO();
//		user.setUserId("ec895a30-1be0-4901-8c4e-faeaadf19f95");
//		String auth = JWTConfig.getTokenByUser(user);
//		 String auth = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMDBmNTc3Ni0yODRhLTRlNjktYWU1NS0yMDdlOGI0MDk0YzAiLCJpYXQiOjE1MDQ5NDQwNTYsInN1YiI6IntcInVzZXJJZFwiOlwiZWM4OTVhMzAtMWJlMC00OTAxLThjNGUtZmFlYWFkZjE5Zjk1XCJ9In0.88OIJ4wCJK4yMu2LJp-iiq0iB8Z6bbsXbov-6EXIj5g";
		// test
		String auth = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjJjNmU2My04ZDIyLTQ2MzAtOGJlNS1hYWUyYmEzMDg3OTYiLCJpYXQiOjE1NjUxNTM5MjYsInN1YiI6IntcInJvbGVcIjpcImN1c3RvbWVyXCIsXCJ1c2VySWRcIjoyfSIsImV4cCI6MTU2NTE2ODMyNn0.EcEqJH_rezKCpg2kJKY3x_uWtXCQya8dHhPNSSaQrug";
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name(Constants.AUTHORIZE_ACCESS_TOKEN).defaultValue(auth).description("免token验证")
			.modelRef(new ModelRef("string"))
			.parameterType("header")
			.required(false)
			.build();
		List<Parameter> aParameters = new ArrayList<>();
		aParameters.add(aParameterBuilder.build());
		return aParameters;
	}

    /** SpringBoot默认已经将classpath:/META-INF/resources/和
     * 			classpath:/META-INF/resources/webjars/映射 * 所以该方法不需要重写，
     * 如果在SpringMVC中，可能需要重写定义（我没有尝试） * 重写该方法需要 extends WebMvcConfigurerAdapter **/

    /** * 可以定义多个组，比如本类中定义把test和demo区分开了 * （访问页面就可以看到效果了）
     *  //访问地址  * http://localhost:8080/swagger-ui.html#/*/
    @Bean
    public Docket userApi() {
    	
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/sys/user/.*")))//过滤的接口
                .build()
                .apiInfo(userApiInfo());
    }

    private ApiInfo userApiInfo() {
		return new ApiInfoBuilder()
				.title("基础平台接口")  //大标题
				.description("用户模块")  //小标题
				.version("0.0.1")  //版本
				.termsOfServiceUrl("")
				.contact(new Contact("", "", "xx@dataint.cn"))  //作者
				.license("基础平台")  //链接显示文字
				.licenseUrl("http://localhost:9090/")  //网站链接
				.build();
    }
    
    @Bean
    public Docket companyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("company")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/sys/company/.*")))//过滤的接口
                .build()
                .apiInfo(companyApiInfo());
    }

    private ApiInfo companyApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("组织/公司/机构模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }

    @Bean
    public Docket positionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("position")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/sys/position/.*")))//过滤的接口
                .build()
                .apiInfo(positionApiInfo());
	}

    private ApiInfo positionApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("岗位模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }

	@Bean
	public Docket menuApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("menu")
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.globalOperationParameters(setHeader())
				.forCodeGeneration(true)
				.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select()
				.paths(or(regex("/sys/menu/.*")))//过滤的接口
				.build()
				.apiInfo(menuApiInfo());
	}

	private ApiInfo menuApiInfo() {
		return new ApiInfoBuilder()
				.title("基础平台接口")  //大标题
				.description("菜单模块")  //小标题
				.version("0.0.1")  //版本
				.termsOfServiceUrl("")
				.contact(new Contact("", "", "xx@dataint.cn"))  //作者
				.license("基础平台")  //链接显示文字
				.licenseUrl("http://localhost:9090/")  //网站链接
				.build();
	}
    
    @Bean
    public Docket moduleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("module")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/sys/module/.*")))//过滤的接口
                .build()
                .apiInfo(moduleApiInfo());
	}

    private ApiInfo moduleApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("菜单模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }
    
    @Bean
    public Docket configApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("config")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/sys/sysconf/.*")))//过滤的接口
                .build()
                .apiInfo(configApiInfo());
	}

    private ApiInfo configApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("配置模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }
    
    @Bean
    public Docket roleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("role")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/sys/role/.*")))//过滤的接口
                .build()
                .apiInfo(roleApiInfo());
	}

    private ApiInfo roleApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("角色模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }

    @Bean
    public Docket departmentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("department")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/sys/department/.*")))//过滤的接口
                .build()
                .apiInfo(departmentApiInfo());
    }

    private ApiInfo departmentApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("部门模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }

	@Bean
    public Docket RoleModuleApi(){
    	return new Docket(DocumentationType.SWAGGER_2)
    			.groupName("roleModule")
    			.genericModelSubstitutes()
    			.useDefaultResponseMessages(false)
    			.globalOperationParameters(setHeader())
    			.forCodeGeneration(true)
    			.pathMapping("/")
    			.select()
    			.paths(or(regex("/sys/rolemodule/.*")))
    			.build()
    			.apiInfo(roleModuleApiInfo());
    }

	private ApiInfo roleModuleApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("角色权限模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
	}

    @Bean
    public Docket sysDictionaryGroupApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("dictionary")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/sys/dictionary/.*")))//过滤的接口
                .build()
                .apiInfo(sysDictionaryGroupApiInfo());
    }

    private ApiInfo sysDictionaryGroupApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("系统字典模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }

	@Bean
	public Docket enumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("enum")
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.globalOperationParameters(setHeader())
				.forCodeGeneration(true)
				.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select()
				.paths(or(regex("/sys/enum/.*")))//过滤的接口
				.build()
				.apiInfo(enumApiInfo());
	}

	private ApiInfo enumApiInfo() {
		return new ApiInfoBuilder()
				.title("基础平台接口")  //大标题
				.description("枚举常量")  //小标题
				.version("0.0.1")  //版本
				.termsOfServiceUrl("")
				.contact(new Contact("", "", "xx@dataint.cn"))  //作者
				.license("基础平台")  //链接显示文字
				.licenseUrl("http://localhost:9090/")  //网站链接
				.build();
	}

	@Bean
	public Docket FileApi() {
		return new Docket(DocumentationType.SWAGGER_2)
    			.groupName("file")
    			.genericModelSubstitutes()
    			.useDefaultResponseMessages(false)
    			.globalOperationParameters(setHeader())
    			.forCodeGeneration(true)
    			.pathMapping("/")
    			.select()
    			.paths(or(regex("/file/.*")))
    			.build()
    			.apiInfo(fileApiInfo());
	}
	
	private ApiInfo fileApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("文件模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("wcs", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
	}

	@Bean
	public Docket CrawlSiteApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("crawlSite")
				.genericModelSubstitutes()
				.useDefaultResponseMessages(false)
				.globalOperationParameters(setHeader())
				.forCodeGeneration(true)
				.pathMapping("/")
				.select()
				.paths(or(regex("/crawlsite/.*")))
				.build()
				.apiInfo(CrawlSiteApiInfo());
	}

	private ApiInfo CrawlSiteApiInfo() {
		return new ApiInfoBuilder()
				.title("基础平台接口")  //大标题
				.description("crawlSite模块")  //小标题
				.version("0.0.1")  //版本
				.termsOfServiceUrl("")
				.contact(new Contact("wcs", "", "xx@dataint.cn"))  //作者
				.license("基础平台")  //链接显示文字
				.licenseUrl("http://localhost:9090/")  //网站链接
				.build();
	}

	@Bean
	public Docket PoKeywordApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("poKeyword")
				.genericModelSubstitutes()
				.useDefaultResponseMessages(false)
				.globalOperationParameters(setHeader())
				.forCodeGeneration(true)
				.pathMapping("/")
				.select()
				.paths(or(regex("/pokeyword/.*")))
				.build()
				.apiInfo(PoKeywordApiInfo());
	}

	private ApiInfo PoKeywordApiInfo() {
		return new ApiInfoBuilder()
				.title("基础平台接口")  //大标题
				.description("pokeyword模块")  //小标题
				.version("0.0.1")  //版本
				.termsOfServiceUrl("")
				.contact(new Contact("wcs", "", "xx@dataint.cn"))  //作者
				.license("基础平台")  //链接显示文字
				.licenseUrl("http://localhost:9090/")  //网站链接
				.build();
	}

    @Bean
    public Docket MediaTypeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("mediaType")
                .genericModelSubstitutes()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(or(regex("/mediatype/.*")))
                .build()
                .apiInfo(MediaTypeApiInfo());
    }

    private ApiInfo MediaTypeApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("mediaType模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("wcs", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }

    @Bean
    public Docket ArticleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("article")
                .genericModelSubstitutes()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(or(regex("/article/.*")))
                .build()
                .apiInfo(ArticleApiInfo());
    }

    private ApiInfo ArticleApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("article模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("wcs", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }

	@Bean
	public Docket EventApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("event")
				.genericModelSubstitutes()
				.useDefaultResponseMessages(false)
				.globalOperationParameters(setHeader())
				.forCodeGeneration(true)
				.pathMapping("/")
				.select()
				.paths(or(regex("/event/.*")))
				.build()
				.apiInfo(EventApiInfo());
	}

	private ApiInfo EventApiInfo() {
		return new ApiInfoBuilder()
				.title("基础平台接口")  //大标题
				.description("event模块")  //小标题
				.version("0.0.1")  //版本
				.termsOfServiceUrl("")
				.contact(new Contact("wcs", "", "xx@dataint.cn"))  //作者
				.license("基础平台")  //链接显示文字
				.licenseUrl("http://localhost:9090/")  //网站链接
				.build();
	}

    @Bean
    public Docket StatisticApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("statistic")
                .genericModelSubstitutes()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(setHeader())
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(or(regex("/statistic/.*")))
                .build()
                .apiInfo(StatisticApiInfo());
    }

    private ApiInfo StatisticApiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台接口")  //大标题
                .description("statistic模块")  //小标题
                .version("0.0.1")  //版本
                .termsOfServiceUrl("")
                .contact(new Contact("wcs", "", "xx@dataint.cn"))  //作者
                .license("基础平台")  //链接显示文字
                .licenseUrl("http://localhost:9090/")  //网站链接
                .build();
    }
}
