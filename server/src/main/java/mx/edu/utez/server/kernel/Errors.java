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
    USER_HAS_DEPENDENCIES,

    // PATIENT
    DUPLICATED_PATIENT,
    NO_PATIENT_FOUND,
    PATIENT_IS_INACTIVE,

    // DOCTOR
    DUPLICATED_DOCTOR,
    NO_DOCTOR_FOUND,
    DOCTOR_HAS_DEPENDENCIES,

    // PERSON
    NO_PERSON_FOUND,
    DUPLICATED_PERSON,

    // SHIFT
    NO_SHIFT_FOUND,
    SHIFT_IS_INACTIVE,

    // GENDER
    NO_GENDER_FOUND,

    // Session
    CREDENTIALS_MISMATCH,
    USER_IS_INACTIVE,
    USER_IS_BLOCKED,
    CANNOT_ACCESS_TO_RESOURCE,
    USER_IS_NOT_VERIFIED,

    // VERIFICATION CODE
    NO_VERIFICATION_CODE_FOUND,
    VERIFICATION_CODE_IS_EXPIRED,
    VERIFICATION_CODE_IS_INVALID,
    VERIFICATION_CODE_WAS_ALREADY_USED,
    ACCOUNT_NOT_ACTIVATED,
    ERROR_SENDING_CODE,
    NO_CODES_REMAINING,

    // APPOINTMENT TYPE
    NO_APPOINTMENT_TYPE_FOUND,
    APPOINTMENT_TYPE_IS_INACTIVE,
    APPOINTMENT_WAS_ALREADY_CONFIRMED,
    APPOINTMENT_IS_NOT_PENDING,

    // APPOINTMENT
    NO_AVAILABILITY,
    NO_APPOINTMENT_FOUND,
    NO_RESCHEDULES_REMAINING,
    CANNOT_RESCHEDULE,

    // SERVER
    SERVER_ERROR,
}
