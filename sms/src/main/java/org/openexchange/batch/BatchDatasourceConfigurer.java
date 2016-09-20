package org.openexchange.batch;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

@Configuration
public class BatchDatasourceConfigurer extends DefaultBatchConfigurer {
    @Override
    @Autowired
    public void setDataSource(@Qualifier("batchDataSource") DataSource batchDataSource) {
        super.setDataSource(batchDataSource);
    }

    @Bean
    public BatchDatabaseInitializer batchDatabaseInitializer(ResourceLoader resourceLoader,
                                                             @Qualifier("batchDataSource") DataSource dataSource,
                                                             BatchProperties properties) {
        return new BatchDatasourceDatabaseInitializer(resourceLoader, dataSource, properties);
    }
}
