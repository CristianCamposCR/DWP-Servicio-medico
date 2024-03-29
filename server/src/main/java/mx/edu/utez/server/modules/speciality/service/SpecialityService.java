package mx.edu.utez.server.modules.speciality.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import mx.edu.utez.server.modules.area.model.IAreaRepository;
import mx.edu.utez.server.modules.doctor.model.IDoctorRepository;
import mx.edu.utez.server.modules.speciality.controller.dto.SpecialityDto;
import mx.edu.utez.server.modules.speciality.model.ISpecialityRepository;
import mx.edu.utez.server.modules.speciality.model.Speciality;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.Validations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class SpecialityService {
    private final ISpecialityRepository iSpecialityRepository;
    private final IStatusRepository iStatusRepository;
    private final IAreaRepository iAreaRepository;
    private final IAppointmentRepository iAppointmentRepository;
    private final IDoctorRepository iDoctorRepository;

    @Transactional(readOnly = true)
    public ResponseApi<Page<Speciality>> findAll(SpecialityDto specialityDto, Pageable pageable) {
        try {
            Page<Speciality> specialities;
            if (specialityDto != null && specialityDto.getName() != null) {
                specialities = this.iSpecialityRepository.findAllByNameContainingIgnoreCase(specialityDto.getName(), pageable);
            } else {
                specialities = this.iSpecialityRepository.findAll(pageable);
            }
            return new ResponseApi<>(
                    specialities,
                    HttpStatus.OK,
                    false,
                    "Especialidades"
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(readOnly = true)
    public ResponseApi<Page<Speciality>> openFindAll(SpecialityDto specialityDto, Pageable pageable) {
        try {
            Page<Speciality> specialities;
            if (specialityDto != null && specialityDto.getName() != null) {
                specialities = this.iSpecialityRepository.findAllByNameContainingIgnoreCaseAndStatus_Name(
                        specialityDto.getName(), Statuses.ACTIVO, pageable);
            } else {
                specialities = this.iSpecialityRepository.findAllByStatus_Name(Statuses.ACTIVO, pageable);
            }
            return new ResponseApi<>(
                    specialities,
                    HttpStatus.OK,
                    false,
                    "Especialidades"
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(readOnly = true)
    public ResponseApi<Speciality> findOne(Long id) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Speciality> optionalSpeciality = this.iSpecialityRepository.findById(id);
            return optionalSpeciality.map(speciality ->
                    new ResponseApi<>(speciality, HttpStatus.OK, false, "Especialidad")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_SPECIALITY_FOUND.name())
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(readOnly = true)
    public ResponseApi<Speciality> openFindOne(Long id) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Speciality> optionalSpeciality = this.iSpecialityRepository.findByIdAndStatus_Name(id, Statuses.ACTIVO);
            return optionalSpeciality.map(speciality ->
                    new ResponseApi<>(speciality, HttpStatus.OK, false, "Especialidad")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_SPECIALITY_FOUND.name())
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(readOnly = true)
    public ResponseApi<Set<Speciality>> openListAll() {
        try {
            Set<Speciality> specialities = this.iSpecialityRepository.findAllByStatus_Name(Statuses.ACTIVO);

            return new ResponseApi<>(
                    specialities,
                    HttpStatus.OK,
                    false,
                    "Lista de especialidades"
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Speciality> save(SpecialityDto specialityDto) {
        try {
            Long areaId = specialityDto.getArea().getId();
            if (Validations.isInvalidId(areaId))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            if (!areaExists(areaId))
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_AREA_FOUND.name());

            if (!areaIsActive(areaId))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.AREA_IS_INACTIVE.name());

            if (this.iSpecialityRepository.existsByNameIgnoreCase(specialityDto.getName()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_SPECIALITY.name());

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.ACTIVO, StatusType.GENERAL);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            specialityDto.setStatus(optionalStatus.get());

            return new ResponseApi<>(
                    this.iSpecialityRepository.saveAndFlush(specialityDto.getSpecialityEntity()),
                    HttpStatus.CREATED,
                    false,
                    "Especialidad creada."
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Speciality> update(SpecialityDto specialityDto) {
        try {
            Long areaId = specialityDto.getArea().getId();
            Long specialityId = specialityDto.getId();
            if (Validations.isInvalidId(areaId) || Validations.isInvalidId(specialityId))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            if (!areaExists(areaId))
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_AREA_FOUND.name());

            if (!areaIsActive(areaId))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.AREA_IS_INACTIVE.name());

            Optional<Speciality> optionalSpeciality = this.iSpecialityRepository.findById(specialityDto.getId());
            if (optionalSpeciality.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_SPECIALITY_FOUND.name());

            if (this.iSpecialityRepository.existsByNameIgnoreCaseAndIdNot(specialityDto.getName(), specialityDto.getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_SPECIALITY.name());

            this.iStatusRepository.findById(optionalSpeciality.get().getStatus().getId()).ifPresent(specialityDto::setStatus);

            return new ResponseApi<>(
                    this.iSpecialityRepository.saveAndFlush(specialityDto.getSpecialityEntity()),
                    HttpStatus.OK,
                    false,
                    "Especialidad actualizada."
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Speciality> changeStatus(Long id) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Speciality> optionalSpeciality = this.iSpecialityRepository.findById(id);
            if (optionalSpeciality.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_SPECIALITY_FOUND.name());

            if (this.iAppointmentRepository.existsBySpeciality_Id(id) || this.iDoctorRepository.existsBySpeciality_Id(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.SPECIALITY_HAS_DEPENDENCIES.name());

            Statuses newStatus = optionalSpeciality.get().getStatus().getName().equals(Statuses.ACTIVO) ?
                    Statuses.INACTIVO : Statuses.ACTIVO;

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(newStatus, StatusType.GENERAL);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            optionalSpeciality.get().setStatus(optionalStatus.get());

            return new ResponseApi<>(
                    this.iSpecialityRepository.saveAndFlush(optionalSpeciality.get()),
                    HttpStatus.OK,
                    false,
                    "Estado de especialidad actualizado."
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    private boolean areaExists(Long areaId) {
        return this.iAreaRepository.existsById(areaId);
    }

    private boolean areaIsActive(Long areaId) {
        return this.iAreaRepository.existsByIdAndStatus_Name(areaId, Statuses.ACTIVO);
    }
}