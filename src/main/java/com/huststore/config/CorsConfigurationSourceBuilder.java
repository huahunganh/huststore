

package com.huststore.config;

import com.huststore.exceptions.CorsMappingParseException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Type that creates an instance of CorsConfigurationSource using information from an instance of CorsProperties
 */
public class CorsConfigurationSourceBuilder {

  private final String listDelimiter;
  private final List<String> allowedHeaders;
  private final List<String> allowedOrigins;
  private final Map<String, String> mappings;

  public CorsConfigurationSourceBuilder(CorsProperties corsProperties) throws CorsMappingParseException {
    this.listDelimiter = corsProperties.getListDelimiter();
    this.allowedHeaders = Arrays.asList(corsProperties.getAllowedHeaders().split(this.listDelimiter));
    this.allowedOrigins = Arrays.asList(corsProperties.getAllowedOrigins().split(this.listDelimiter));
    this.mappings = this.parseMappings(corsProperties);
  }

  public CorsConfigurationSource build() {
    CorsConfiguration baseConfig = new CorsConfiguration();
    baseConfig.setAllowedHeaders(this.allowedHeaders);
    baseConfig.setAllowCredentials(true);
    baseConfig.setMaxAge(300L);
    UrlBasedCorsConfigurationSource cfg = new UrlBasedCorsConfigurationSource();
    for (Map.Entry<String,String> properties : mappings.entrySet()) {
      List<String> methods = Arrays.asList(properties.getValue().split(","));
      CorsConfiguration pathConfig = new CorsConfiguration(baseConfig);
      pathConfig.setAllowedOrigins(this.allowedOrigins);
      pathConfig.setAllowedMethods(methods);
      cfg.registerCorsConfiguration(properties.getKey(), pathConfig);
    }
    return cfg;
  }

  private Map<String, String> parseMappings(CorsProperties corsProperties) throws CorsMappingParseException {
    Map<String, String> map = new HashMap<>();

    for (String chunk : corsProperties.getMappings().split(this.listDelimiter)) {
      String[] mapping = chunk.split(" ");
      try {
        String method = mapping[0] + ",HEAD,OPTIONS";
        String path = mapping[1];
        map.put(path, method);
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new CorsMappingParseException(
                "Could not parse '" + chunk + "', format must be 'METHOD[,METHOD2,...] /path/to/api'");
      }
    }
    return map;
  }

}
