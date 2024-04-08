package mx.edu.utez.server.kernel;

public enum Errors {
    INVALID_FIELDS,
    MISSING_FIELDS,
    DATA_TYPE_WRONG,
    REVIEW_REQUEST,
    NO_DATA_FOUND,
    RECORDS_ARE_NOT_RELATED,
    RECORD_NOT_REGISTERED,
    RECORD_NOT_UPDATED,
    RECORD_NOT_DELETED,
    RECORD_HAS_DEPENDENCIES,

    // Area
    DUPLICATED_AREA,
    NO_AREA_FOUND,
    AREA_HAS_DEPENDENCIES,
    AREA_IS_INACTIVE,

    // Specitality
    DUPLICATED_SPECIALITY,
    NO_SPECIALITY_FOUND,
    SPECIALITY_HAS_DEPENDENCIES,
    SPECIALITY_IS_INACTIVE,
    // Status
    NO_STATUS_FOUND,
    // ROLE
    NO_ROLE_FOUND,
    // USER
    DUPLICATED_USER,
    NO_USER_FOUND,
    // PATIENT
    DUPLICATED_PATIENT,
    NO_PATIENT_FOUND,
    // DOCTOR
    DUPLICATED_DOCTOR,
    NO_DOCTOR_FOUND,
    // PERSON
    NO_PERSON_FOUND,
    DUPLICATED_PERSON,
    // SHIFT
    NO_SHIFT_FOUND,
    // GENDER
    NO_GENDER_FOUND,
    // Session
    CREDENTIALS_MISMATCH,
    USER_IS_INACTIVE,
    USER_IS_BLOCKED,
    CANNOT_ACCESS_TO_RESOURCE,
    // VERIFICATION CODE
    NO_VERIFICATION_CODE_FOUND,
    VERIFICATION_CODE_IS_EXPIRED,
    VERIFICATION_CODE_IS_INVALID,
    VERIFICATION_CODE_WAS_ALREADY_USED,
    ACCOUNT_NOT_ACTIVATED,
    ERROR_SENDING_CODE,
    NO_CODES_REMAINING,

    // SERVER
    SERVER_ERROR,
}
