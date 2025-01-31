package br.com.bookstoreconsumer.adapters.configuration;

import feign.RetryableException;
import feign.Retryer;

public class CustomFeignRetryer implements feign.Retryer, Cloneable {

    private final int maxAttempts;
    private final long backoff;
    private int attempt = 1;

    public CustomFeignRetryer(int maxAttempts, long backoff) {
        this.maxAttempts = maxAttempts;
        this.backoff = backoff;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (attempt++ >= maxAttempts) {
            throw e;
        }
        try {
            Thread.sleep(backoff);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    public Retryer clone() {
        try {
            return (CustomFeignRetryer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }
}

