package com.example.taskmanager.config;
 
import io.micrometer.core.instrument.Clock;
import io.micrometer.graphite.GraphiteConfig;
import io.micrometer.graphite.GraphiteMeterRegistry;
import io.micrometer.graphite.GraphiteProtocol;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import java.time.Duration;
 
@Configuration
public class GraphiteConfiguration {
 
    @Bean
    public GraphiteMeterRegistry graphiteMeterRegistry() {
 
        GraphiteConfig config = new GraphiteConfig() {
 
            @Override
            public String get(String key) {
                return null;
            }
 
            @Override
            public String host() {
                return "host.docker.internal";
            }
 
            @Override
            public int port() {
                return 2003;
            }
 
            @Override
            public GraphiteProtocol protocol() {
                return GraphiteProtocol.PLAINTEXT;
            }
 
            @Override
            public Duration step() {
                return Duration.ofSeconds(10);
            }
        };
 
        return new GraphiteMeterRegistry(config, Clock.SYSTEM);
    }
}
 