package cz.muni.csirt.analyza.util.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration HDFS class.
 *
 * @author David Brilla*xbrilla*469054
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    private String processedLocation = "/data";
    private String hdfsLocation = "hdfs://147.251.124.63:9000/";

    public String getProcessedLocation() {
        return processedLocation;
    }

    public void setProcessedLocation(String processedLocation) {
        this.processedLocation = processedLocation;
    }

    public String getHdfsLocation() {
        return hdfsLocation;
    }

    public void setHdfsLocation(String hdfsLocation) {
        this.hdfsLocation = hdfsLocation;
    }
}
