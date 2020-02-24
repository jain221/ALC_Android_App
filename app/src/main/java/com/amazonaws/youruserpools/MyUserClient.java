package com.amazonaws.youruserpools;

@com.amazonaws.mobileconnectors.apigateway.annotation.Service(endpoint = " https://brh4n8g8q9.execute-api.eu-west-1.amazonaws.com/default/lanternm")

public interface MyUserClient {


    /**
     * A generic invoker to invoke any API Gateway endpoint.
     * @param request
     * @return ApiResponse
     */
    com.amazonaws.mobileconnectors.apigateway.ApiResponse execute(com.amazonaws.mobileconnectors.apigateway.ApiRequest request);

    /**
     *
     *
     * @param lantern_manufacturer
     * @return Output
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/", method = "GET")
    Output rootGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "lantern_manufacturer", location = "query")
                    String lantern_manufacturer);
}