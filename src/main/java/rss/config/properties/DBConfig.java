package rss.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver}")
    private String driver;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show_sql:true}")
    private String showSql;

    @Value("${hibernate.hbm2ddl.auto:validate}")
    private String hbm2ddlAuto;

    @Value("${entitymanager.packagesToScan:rss.model}")
    private String packagesToScan;

    @Value("${spring.jpa.properties.hibernate.jdbc.time_zone:GMT}")
    private String timezone;

    //region getters and setters

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }

    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    public void setHbm2ddlAuto(String hbm2ddlAuto) {
        this.hbm2ddlAuto = hbm2ddlAuto;
    }

    public String getPackagesToScan() {
        return packagesToScan;
    }

    public void setPackagesToScan(String packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    //endregion
}
