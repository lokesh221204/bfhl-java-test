package com.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookResponse {

    @JsonProperty("webhook")
    private String webhookUrl;

    @JsonProperty("accessToken")
    private String accessToken;

    public String getWebhookUrl() { return webhookUrl; }
    public String getAccessToken() { return accessToken; }

    public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}
