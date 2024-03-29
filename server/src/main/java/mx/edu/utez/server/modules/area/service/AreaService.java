package mx.edu.utez.server.modules.area.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.area.controller.dto.AreaDto;
import mx.edu.utez.server.modules.area.model.Area;
import mx.edu.utez.server.modules.area.model.IAreaRepository;
import mx.edu.utez.server.modules.speciality.model.ISpecialityRepository;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.utils.ResponseApi;
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
public class AreaService {
    private final IAreaRepository iAreaRepository;
    private final IStatusRepository iStatusRepository;
    private final ISpecialityRepository iSpecialityRepository;

    @Transactional(readOnly = true)
    public ResponseApi<Page<Area>> findAll(AreaDto areaDto, Pageable pageable) {
        try {
            Page<Area> areas;
            if (areaDto != null && areaDto.getName() != null) {
                areas = this.iAreaRepository.findAllByNameContainingIgnoreCase(areaDto.getName(), pageable);
            } else {
                areas = this.iAreaRepository.findAll(pageable);
            }
            return new ResponseApi<>(
                    areas,
                    HttpStatus.OK,
                    false,
                    "Áreas"
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
    public ResponseApi<Page<Area>> openFindAll(AreaDto areaDto, Pageable pageable) {
        try {
            Page<Area> areas;
            if (areaDto != null && areaDto.getName() != null) {
                areas = this.iAreaRepository.findAllByNameContainingIgnoreCaseAndStatus_Name(
                        areaDto.getName(), Statuses.ACTIVO, pageable);
            } else {
                areas = this.iAreaRepository.findAllByStatus_Name(Statuses.ACTIVO, pageable);
            }
            return new ResponseApi<>(
                    areas,
                    HttpStatus.OK,
                    false,
                    "Áreas"
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
    public ResponseApi<Area> findOne(Long id) {
        try {
            if (id == null || id <= 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Area> optionalArea = this.iAreaRepository.findById(id);
            return optionalArea.map(area ->
                    new ResponseApi<>(area, HttpStatus.OK, false, "Área")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_AREA_FOUND.name())
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
    public ResponseApi<Area> openFindOne(Long id) {
        try {
            if (id == null || id <= 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Area> optionalArea = this.iAreaRepository.findByIdAndStatus_Name(id, Statuses.ACTIVO);
            return optionalArea.map(area ->
                    new ResponseApi<>(area, HttpStatus.OK, false, "Área")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_AREA_FOUND.name())
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
    public ResponseApi<Set<Area>> openListAll() {
        try {
            Set<Area> areas = this.iAreaRepository.findAllByStatus_Name(Statuses.ACTIVO);

            return new ResponseApi<>(
                    areas,
                    HttpStatus.OK,
                    false,
                    "Lista de áreas."
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
    public ResponseApi<Area> save(AreaDto areaDto) {
        try {
            if (this.iAreaRepository.existsByNameIgnoreCase(areaDto.getName()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_AREA.name());

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.ACTIVO, StatusType.GENERAL);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            areaDto.setStatus(optionalStatus.get());

            return new ResponseApi<>(
                    this.iAreaRepository.saveAndFlush(areaDto.getAreaEntity()),
                    HttpStatus.CREATED,
                    false,
                    "Area creada."
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
    public ResponseApi<Area> update(AreaDto areaDto) {
        try {
            if (areaDto.getId() == null || areaDto.getId() <= 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Area> optionalArea = this.iAreaRepository.findById(areaDto.getId());
            if (optionalArea.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_AREA_FOUND.name());

            if (this.iAreaRepository.existsByNameIgnoreCaseAndIdNot(areaDto.getName(), areaDto.getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_AREA.name());

            this.iStatusRepository.findById(optionalArea.get().getStatus().getId()).ifPresent(areaDto::setStatus);

            return new ResponseApi<>(
                    this.iAreaRepository.saveAndFlush(areaDto.getAreaEntity()),
                    HttpStatus.OK,
                    false,
                    "Area actualizada."
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
    public ResponseApi<Area> changeStatus(Long id) {
        try {
            if (id == null || id <= 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Area> optionalArea = this.iAreaRepository.findById(id);
            if (optionalArea.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_AREA_FOUND.name());

            if (this.iSpecialityRepository.existsByArea_Id(optionalArea.get().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.AREA_HAS_DEPENDENCIES.name());

            Statuses newStatus = optionalArea.get().getStatus().getName().equals(Statuses.ACTIVO) ?
                    Statuses.INACTIVO : Statuses.ACTIVO;

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(newStatus, StatusType.GENERAL);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            optionalArea.get().setStatus(optionalStatus.get());

            return new ResponseApi<>(
                    this.iAreaRepository.saveAndFlush(optionalArea.get()),
                    HttpStatus.OK,
                    false,
                    "Estado de área actualizado."
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }
}