package com.jpmc.collateral.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * It is used to provide a centralized and consistent way of handling and reporting
 * errors in the application. The enum constants represent the different types of
 * errors that can occur, and the fields errorCode and message provide the
 * necessary information to identify and describe the errors.
 */

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    POSITION_SERVICE_UNAVAILABLE(101, "Position service is unavailable"),
    ELIGIBILITY_SERVICE_UNAVAILABLE(102, "Eligibility service is unavailable"),
    PRICE_SERVICE_UNAVAILABLE(103, "Price service is unavailable"),

    POSITION_DATA_EMPTY(104, "Position data is empty");

    private final int errorCode;
    private final String message;

}
