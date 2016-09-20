package org.openexchange.batch;

import org.springframework.batch.support.DatabaseType;
import org.springframework.boot.autoconfigure.batch.BatchDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.MetaDataAccessException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

class BatchDatasourceDatabaseInitializer extends BatchDatabaseInitializer {

    private final BatchProperties properties;
    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    BatchDatasourceDatabaseInitializer(ResourceLoader resourceLoader, DataSource dataSource, BatchProperties properties) {
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
        this.properties = properties;
    }

    @Override
    @PostConstruct
    protected void initialize() {
        if (this.properties.getInitializer().isEnabled()) {
            String platform = getDatabaseType();
            if ("hsql".equals(platform)) {
                platform = "hsqldb";
            }
            if ("postgres".equals(platform)) {
                platform = "postgresql";
            }
            if ("oracle".equals(platform)) {
                platform = "oracle10g";
            }
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            String schemaLocation = this.properties.getSchema();
            schemaLocation = schemaLocation.replace("@@platform@@", platform);
            populator.addScript(this.resourceLoader.getResource(schemaLocation));
            populator.setContinueOnError(true);
            DatabasePopulatorUtils.execute(populator, this.dataSource);
        }
    }

    private String getDatabaseType() {
        try {
            return DatabaseType.fromMetaData(this.dataSource).toString().toLowerCase();
        } catch (MetaDataAccessException ex) {
            throw new IllegalStateException("Unable to detect database type", ex);
        }
    }

}
