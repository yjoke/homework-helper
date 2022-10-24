package work.yjoker.homeworkhelper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import work.yjoker.homeworkhelper.helper.JacksonObjectMapper;
import work.yjoker.homeworkhelper.handler.LoginInterceptor;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author HeYunjia
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, converter);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")

                // 登录使用的地址
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/forget")
                .excludePathPatterns("/code")

                // swagger 文档的静态资源
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns("/error");
    }
}
