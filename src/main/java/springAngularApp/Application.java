package springAngularApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springAngularApp.system.configuration.MvcConfiguration;
import springAngularApp.system.configuration.SpringJpaConfig;
import springAngularApp.system.configuration.WebSecurityConfig;

@SpringBootApplication
@EnableAspectJAutoProxy //开启对AspectJ自动代理的支持
@EnableTransactionManagement(proxyTargetClass = true)   //开启注解式事务的支持
@EnableJpaRepositories(transactionManagerRef = "txManager", entityManagerFactoryRef = "entityManagerFactory")   //开启对Spring DataJPA Repository的支持
@Import({SpringJpaConfig.class, WebSecurityConfig.class, MvcConfiguration.class})
public class Application {

	/**
	 * 此为SpringBoot的入口
	 * @param args
	 */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}