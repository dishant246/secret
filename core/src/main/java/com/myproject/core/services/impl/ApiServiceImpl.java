package com.myproject.core.services.impl;

import com.myproject.core.services.ApiService;

/**
 * Implementation of ApiService that reads configuration from OSGI config.
 * In a real AEM project, this would use @Component and @ObjectClassDefinition annotations.
 * Simplified here for demonstration purposes.
 */
public class ApiServiceImpl implements ApiService {

    private String apiKey;
    private String apiEndpoint;

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getApiEndpoint() {
        return apiEndpoint;
    }
}
