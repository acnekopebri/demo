package com.example.demo.validation;

public interface Rule {
    /**
     * Determine if the validation rule passes.
     */
    public boolean passes(String value);

    /**
     * Get the validation error message.
     */
    public String message();
}
