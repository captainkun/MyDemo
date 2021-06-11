package com.qk.admin.config;

import com.qk.admin.notify.CompanyWechatNotifier;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qu.kun
 * @date 2021/3/30
 */
@Configuration
@AutoConfigureBefore({AdminServerNotifierAutoConfiguration.NotifierTriggerConfiguration.class, AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration.class})
public class NotifierConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CompanyWechatNotifier dingTalkNotifier(InstanceRepository repository) {
        return new CompanyWechatNotifier(repository);
    }

}
