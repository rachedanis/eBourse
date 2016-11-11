package com.bfigroupe.ebourse.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "com.bfigroupe.ebourse.task" })
public class SpringTaskConfig {

}
