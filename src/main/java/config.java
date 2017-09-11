import java.util.Properties;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

@Configuration
public class config {

	@Bean
	public JndiTemplate solaceJndiTemplate()
	{
		Properties environment = new Properties();
		environment.setProperty("java.naming.provider.url", "smf://___IP:PORT___");
		environment.setProperty("java.naming.factory.initial", "com.solacesystems.jndi.SolJNDIInitialContextFactory");
		environment.setProperty("java.naming.security.principal", "spring_user@Solace_Spring_VPN");
		environment.setProperty("java.naming.security.credentials", "spring_password");
		
		return new JndiTemplate(environment);
		
	}
	
	@Bean
	public JndiObjectFactoryBean solaceConnectionFactory()
	{
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("JNDI/CF/spring");
		jndiObjectFactoryBean.setJndiTemplate(solaceJndiTemplate());
		
		return jndiObjectFactoryBean;
	}
	
	@Bean
	public CachingConnectionFactory solaceCachedConnectionFactory()
	{
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setTargetConnectionFactory(solaceConnectionFactory());
		cachingConnectionFactory.setSessionCacheSize(10);
		return cachingConnectionFactory;
		
	}
	
}
