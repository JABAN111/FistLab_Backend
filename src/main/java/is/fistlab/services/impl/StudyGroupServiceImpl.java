package is.fistlab.services.impl;

import is.fistlab.database.entities.*;
import is.fistlab.database.enums.Color;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.database.repositories.specifications.StudyGroupSpecifications;
import is.fistlab.dto.PersonDto;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.dto.UserDto;
import is.fistlab.exceptions.dataBaseExceptions.studyGroup.StudyGroupAlreadyExistException;
import is.fistlab.exceptions.dataBaseExceptions.studyGroup.StudyGroupNotExistException;
import is.fistlab.mappers.PersonMapper;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.security.sevices.Impl.AuthServiceImpl;
import is.fistlab.services.StudyGroupService;
import is.fistlab.utils.AuthenticationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class StudyGroupServiceImpl implements StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final AuthenticationUtils authenticationUtils;
    private final AuthServiceImpl authServiceImpl;
    private final PersonRepository personRepository;

    @Autowired
    public StudyGroupServiceImpl(StudyGroupRepository studyGroupRepository,
                                 AuthenticationUtils authenticationUtils, AuthServiceImpl authServiceImpl, PersonRepository personRepository) {
        this.studyGroupRepository = studyGroupRepository;
        this.authenticationUtils = authenticationUtils;
        this.authServiceImpl = authServiceImpl;
        this.personRepository = personRepository;
    }

    @Transactional
    @Override
    public StudyGroup add(StudyGroupDto dto) {
        StudyGroup studyGroupToSave = StudyGroupMapper.toEntity(dto);
        if (studyGroupToSave.getId() != null && studyGroupRepository.existsById(studyGroupToSave.getId())) {
            log.error("Study group with id {} already exists", studyGroupToSave.getId());
            throw new StudyGroupAlreadyExistException
                    ("Study group with id " + studyGroupToSave.getId() + " already exists");
        }
        studyGroupToSave.setCreator(authenticationUtils.getCurrentUserFromContext());
//        log.debug("studyGroupToSave: {},",studyGroupToSave);
        log.debug("len(name) = {}", studyGroupToSave.getName().length());
        var savedStudyGroup = studyGroupRepository.save(studyGroupToSave);
        log.info("Study group created: {}", studyGroupToSave);
        return savedStudyGroup;
    }

    @Override
    public Page<StudyGroup> getAllStudyGroups(Pageable pageable) {
        return studyGroupRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public StudyGroup updateStudyGroup(Long id, StudyGroupDto dto) {
        Optional<StudyGroup> optionalStudyGroup = studyGroupRepository.findById(id);

        if (optionalStudyGroup.isEmpty()) {
            log.error("Study group with id {} does not exist", id);
            throw new StudyGroupNotExistException("Study group with id " + id + " does not exist");
        }

        var newStudyGroup = StudyGroupMapper.toEntity(dto);
        newStudyGroup.setId(id);
        StudyGroup savedStudyGroup;
        authenticationUtils.verifyAccess(optionalStudyGroup.get());
        newStudyGroup.setCreator(authenticationUtils.getCurrentUserFromContext());
        savedStudyGroup = studyGroupRepository.save(newStudyGroup);

        log.info("Study group updated: {}", savedStudyGroup);
        return savedStudyGroup;
    }

    @Transactional
    @Override
    public void deleteStudyGroup(Long id) {
        Optional<StudyGroup> optionalStudyGroup = studyGroupRepository.findById(id);
        if (optionalStudyGroup.isEmpty()) {
            log.error("Study group with such id {} does not exist", id);
            throw new StudyGroupNotExistException("Study group with id " + id + " does not exist");
        }

        if (!studyGroupRepository.existsById(id)) {
            log.error("Study group does not exist: {}", id);
            throw new StudyGroupNotExistException("Такой группы не существует");
        }
        authenticationUtils.verifyAccess(optionalStudyGroup.get());
        studyGroupRepository.deleteById(id);
        log.info("Deleted StudyGroup with id: {}", id);

    }

    @Override
    public List<Map<String, Object>> getCountFormsOfEducations() {
        return studyGroupRepository.getCountFormsOfEducations();
    }

    @Override
    public void updateAdminGroup(Long groupId, Long adminId) {
        studyGroupRepository.updateGroupAdmin(groupId, adminId);
    }

    @Override
    public void deleteByGroupAdminName(String groupAdminName) {
        studyGroupRepository.deleteByAdminName(groupAdminName);
    }

    @Override
    public List<Float> getUniqueStudyGroupByAverageMark() {
        return studyGroupRepository.getUniqueGroupsByAverageMark();
    }

    @Override
    public Integer getCountOfExpelledStudents() {
        return studyGroupRepository.getCountOfExpelledStudents();
    }

    @Override
    public List<StudyGroup> filterStudyGroups(final String name, final Long studentsCount,
                                              final FormOfEducation formOfEducation,
                                              final Semester semester, final LocalDate createdAfter,
                                              final Long shouldBeExpelled, final Float averageMark,
                                              final Long expelledStudents, final Integer transferredStudents,
                                              final Person admin) {
        Specification<StudyGroup> specification = Specification.where(null);

        if (Objects.nonNull(name)) {
            specification = specification.and(StudyGroupSpecifications.hasName(name));
        }
        if (Objects.nonNull(studentsCount)) {
            specification = specification.and(StudyGroupSpecifications.hasStudentsCountGreaterThan(studentsCount));
        }
        if (Objects.nonNull(formOfEducation)) {
            specification = specification.and(StudyGroupSpecifications.hasFormOfEducation(formOfEducation));
        }
        if (Objects.nonNull(semester)) {
            specification = specification.and(StudyGroupSpecifications.hasSemester(semester));
        }
        if (Objects.nonNull(createdAfter)) {
            specification = specification.and(StudyGroupSpecifications.createdAfter(createdAfter));
        }
        if (Objects.nonNull(shouldBeExpelled)) {
            specification = specification.and(StudyGroupSpecifications.hasShouldBeExpelledGreaterThan(shouldBeExpelled));
        }
        if (Objects.nonNull(averageMark)) {
            specification = specification.and(StudyGroupSpecifications.hasAverageMarkGreaterThan(averageMark));
        }
        if (Objects.nonNull(expelledStudents)) {
            specification = specification.and(StudyGroupSpecifications.hasExpelledStudentsGreaterThan(expelledStudents));
        }
        if (Objects.nonNull(transferredStudents)) {
            specification = specification.and(StudyGroupSpecifications.hasTransferredStudentsGreaterThan(transferredStudents));
        }

        if (Objects.nonNull(admin)) {
            specification = specification.and(StudyGroupSpecifications.hasAdmin(admin));
        }

        return studyGroupRepository.findAll(specification);
    }

    @Override
    public Page<StudyGroup> getPagedResult(final List<StudyGroup> studyGroups, final Pageable pageable) {
        int start = Math.min((int) pageable.getOffset(), studyGroups.size());
        int end = Math.min((start + pageable.getPageSize()), studyGroups.size());
        return new PageImpl<>(studyGroups.subList(start, end), pageable, studyGroups.size());
    }

    @Override
    public Pageable getPageAfterSort(final int page, final int size, final String sortBy, final String sortDirection) {
        final List<String> allowedSortFields = List.of("id", "name", "studentsCount", "expelledStudents",
                "transferredStudents", "shouldBeExpelled",
                "averageMark", "creationDate");

        Sort sort = Sort.by("id"); //sort by default

        if (Objects.nonNull(sortBy) && Objects.nonNull(sortDirection) && allowedSortFields.contains(sortBy)) {
            sort = Sort.by(sortBy);
            if ("desc".equalsIgnoreCase(sortDirection)) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }
        } else {
            log.warn("Invalid sortBy field: {}. Default sorting by id is used.", sortBy);
        }

        return PageRequest.of(page, size, sort);
    }


}
