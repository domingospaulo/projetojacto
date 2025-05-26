package com.jacto.agendamento.config;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
    basePackages = "com.jacto.agendamento.repository", 
    entityManagerFactoryRef = "agendamentoEntityManager", 
    transactionManagerRef = "agendamentoTransactionManager"
)
public class AgendamentoDSConfig {
        @Autowired
        private Environment env;
        
        @Bean
        public LocalContainerEntityManagerFactoryBean agendamentoEntityManager() {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(agendamentoDataSource());
            em.setPackagesToScan(new String[] { "com.jacto.agendamento.entity" });

            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);
            HashMap<String, Object> properties = new HashMap<>();
            properties.put("hibernate.hbm2ddl.auto",   env.getProperty("hibernate.hbm2ddl.auto"));
            properties.put("hibernate.dialect",   env.getProperty("hibernate.dialect"));
            em.setJpaPropertyMap(properties);   
            
            return em;
        }

        @ConfigurationProperties("spring.agendamento-datasource")
        @Bean(name = "agendamento")
        public HikariDataSource agendamentoDataSource() {
            return DataSourceBuilder.create().type(HikariDataSource.class).build();
        }

        @Bean(name = "agendamentoJdbcTemplate")
        public JdbcTemplate applicationDataConnection(){
            return new JdbcTemplate(agendamentoDataSource());
        }

        @Bean(name= "agendamentoTransactionManager")
        public PlatformTransactionManager agendamentoTransactionManager() {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            return transactionManager;
        }
}