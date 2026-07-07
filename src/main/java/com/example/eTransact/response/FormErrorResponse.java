package com.example.eTransact.response;

import com.example.eTransact.error.FormError;

public record FormErrorResponse(String message, FormError error) {
}
