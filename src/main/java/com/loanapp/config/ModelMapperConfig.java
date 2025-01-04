package com.loanapp.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : TiamiyuKehinde5@gmail.com
 */

@org.springframework.context.annotation.Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setAmbiguityIgnored(true);

        return modelMapper;
    }
}
