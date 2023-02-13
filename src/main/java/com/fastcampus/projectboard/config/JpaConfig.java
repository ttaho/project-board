package com.fastcampus.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
//Auditing을 사용하기위해 아래의 애노테이션 사용
@EnableJpaAuditing
//각종 설정을 잡기위해 config사용
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {//auditorAware이 만든사람인식하는거인듯
        return () -> Optional.of("pazu"); //auditing할때마다 사람이름은 pazu가 들어간다. TODO: 스프링 시큐리티로 인증 기능을 붙이게 될때, 수정하자
    }

}
