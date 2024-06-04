package com.example.webflux1.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableR2dbcRepositories
@EnableR2dbcAuditing //@CreatedDate 사용 위함
public class R2dbcConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DatabaseClient databaseClient;

    // 시작단계에서 r2dbc connection에 대한 검증
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // reactor : publisher, subscriber
        databaseClient.sql("SELECT 1").fetch().one()
                .subscribe(
                        success -> {
                            log.info("Initialize r2dbc database connection");
                        },
                        error -> {
                            log.info("Failed to initialize r2dbc database connection");
                            SpringApplication.exit(event.getApplicationContext(), () -> -110);
                        }
                );
    }
}
