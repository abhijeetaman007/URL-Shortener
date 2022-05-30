package com.example.urlshortener.data.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateUrlResponse {

    private boolean isSuccess;
    private String url;
    private String msg;

}
