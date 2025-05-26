package com.jacto.agendamento.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.jacto.agendamento.repository",
    entityManagerFactoryRef = "agendamentoEntityManager",
    transactionManagerRef = "agendamentoTransactionManager"
)
public class AgendamentoDSConfig {

    @Autowired
    private DataSource dataSource; // Injeta o DataSource padrão configurado pelo Spring Boot

    @Bean
    public LocalContainerEntityManagerFactoryBean agendamentoEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource); // Usa o DataSource padrão
        em.setPackagesToScan("com.jacto.agendamento.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update"); // Ações com o banco de dados
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"); // Configura o dialeto
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public JdbcTemplate agendamentoJdbcTemplate() {
        return new JdbcTemplate(dataSource); // Usa o DataSource padrão
    }

    @Bean(name = "agendamentoTransactionManager")
    public PlatformTransactionManager agendamentoTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(Objects.requireNonNull(agendamentoEntityManager().getObject()));
        return transactionManager;
    }
}