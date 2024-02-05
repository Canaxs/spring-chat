package com.springchat.models.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenBooleanResponse {
    Boolean isExpired;
}
