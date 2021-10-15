package ptit.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditingProvider")
class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditingProvider(){
        return new AuditorAwareImpl();
    }

}
