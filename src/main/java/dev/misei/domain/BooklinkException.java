package dev.misei.domain;

import java.util.function.Supplier;

public class BooklinkException extends RuntimeException {
    public BooklinkException(String message) {
        super(message);
    }

    public enum Type {
        USER_NOT_FOUND("User not found"),
        BUSINESS_NOT_FOUND("Business not found"),
        SERVICE_NOT_MATCHING_DURATION("Current service not matching duration with slot provided"),
        APPOINTMENT_CONFLICT("The appointment is conflicting"),
        APPOINTMENT_CONFLICT_USER("You cannot be in two places at the same time"),
        APPOINTMENT_NOT_SUITABLE("The appointment is not on a suitable working time"),
        MORE_THAN_ONE_BUSINESS("At the moment is not possible to have more than one business"),
        ALREADY_TAKEN_SUBDOMAIN("This subdomain is already taken"),
        UNKNOWN_SUBDOMAIN("This subdomain is not registered"),
        USER_NOT_ADMIN("The user is not a business administrator"),
        APPOINTMENT_ID_NOT_FOUND("The ID is not matching with any appointment"),
        USER_FOR_APPOINTMENT_NOT_FOUND("The user has not been found for the appointment id"),
        ID_MISMATCH("The current id does not belong to a valid business that matches with the user");

        private final String message;

        Type(String message) {
            this.message = message;
        }

        public BooklinkException boom() {
            return new BooklinkException(message);
        }

    }

}
