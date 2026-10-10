
package com.placement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placement.constants.PlacementConstants;
import com.placement.dto.InterviewRequestDto;
import com.placement.dto.InterviewResponseDto;
import com.placement.entity.Application;
import com.placement.entity.Interview;
import com.placement.enums.ApplicationStatus;
import com.placement.enums.InterviewResult;
import com.placement.enums.InterviewRound;
import com.placement.exception.ApplicationNotFoundException;
import com.placement.exception.InterviewNotFoundException;
import com.placement.mapper.InterviewMapper;
import com.placement.repository.ApplicationRepository;
import com.placement.repository.InterviewRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private InterviewRepository interviewRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private InterviewMapper mapper;

	@Override
	@Transactional
	@CacheEvict(value = {
		
			"interviews", "interviewsByResult", "interviewsByRound", "interviewsByApplication",
			"interviewsByInterviewer", "interviewsByRoundAndResult", "selectedInterviews", "pendingInterviews",
			"interviewsPages", "interviewsSorted",



			"applications", "applicationsByStatus", "applicationsByShortlisted", "applicationsByStudent",
			"applicationsByJobDrive", "applicationsByStatusAndShortlisted", "shortlistedApplications",
			"appliedApplications", "applicationsPages", "applicationsSorted" }, allEntries = true)
	public InterviewResponseDto createOrUpdate(InterviewRequestDto requestDto) {

		log.info("Interview request received. ID: {}, Application ID: {}", requestDto.getId(),
				requestDto.getApplicationId());

		Application application = applicationRepository.findById(requestDto.getApplicationId())
				.orElseThrow(() -> new ApplicationNotFoundException(
						PlacementConstants.APPLICATION_NOT_FOUND + requestDto.getApplicationId()));

		Interview interview;

		if (requestDto.getId() == null) {

			
			interview = mapper.toEntity(requestDto);
			interview.setApplication(application);

			log.info("Creating interview for Application ID: {}", application.getId());

		} else {

			
			interview = interviewRepository.findById(requestDto.getId()).orElseThrow(
					() -> new InterviewNotFoundException(PlacementConstants.INTERVIEW_NOT_FOUND + requestDto.getId()));

			mapper.updateEntity(requestDto, interview);
			interview.setApplication(application);

			log.info("Updating interview. ID: {}", requestDto.getId());
		}

		Interview savedInterview = interviewRepository.save(interview);

		log.info("Interview saved successfully. ID: {}", savedInterview.getId());

		return mapper.toResponseDto(savedInterview);
	}

	@Override
	@Cacheable(value = "interviews", key = "#id")
	public InterviewResponseDto getById(Long id) {

		Interview interview = interviewRepository.findById(id)
				.orElseThrow(() -> new InterviewNotFoundException(PlacementConstants.INTERVIEW_NOT_FOUND + id));

		return mapper.toResponseDto(interview);
	}

	@Override
	@Cacheable(value = "interviews", key = "'all'")
	public List<InterviewResponseDto> getAll() {

		return interviewRepository.findAll().stream().map(mapper::toResponseDto).collect(Collectors.toList());
	}

	@Override
	@CacheEvict(value = { "interviews", "interviewsByResult", "interviewsByRound", "interviewsByApplication",
			"interviewsByInterviewer", "interviewsByRoundAndResult", "selectedInterviews", "pendingInterviews",
			"interviewsPages", "interviewsSorted" }, allEntries = true)
	public void deleteById(Long id) {

		Interview interview = interviewRepository.findById(id)
				.orElseThrow(() -> new InterviewNotFoundException(PlacementConstants.INTERVIEW_NOT_FOUND + id));

		interviewRepository.delete(interview);

		log.info("Interview deleted successfully. ID: {}", id);
	}

	@Override
	@Cacheable(value = "interviewsByResult", key = "#result")
	public List<InterviewResponseDto> getByResult(InterviewResult result) {

		return interviewRepository.findByResult(result).stream().map(mapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	@Cacheable(value = "interviewsByRound", key = "#round")
	public List<InterviewResponseDto> getByRound(InterviewRound round) {

		return interviewRepository.findByRound(round).stream().map(mapper::toResponseDto).collect(Collectors.toList());
	}

	@Override
	@Cacheable(value = "interviewsByApplication", key = "#applicationId")
	public List<InterviewResponseDto> getByApplicationId(Long applicationId) {

		return interviewRepository.findByApplicationId(applicationId).stream().map(mapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	@Cacheable(value = "interviewsByInterviewer", key = "#interviewer")
	public List<InterviewResponseDto> getByInterviewer(String interviewer) {

		return interviewRepository.findByInterviewer(interviewer).stream().map(mapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	@Cacheable(value = "interviewsByRoundAndResult", key = "#round.name() + '-' + #result.name()")
	public List<InterviewResponseDto> getByRoundAndResult(InterviewRound round, InterviewResult result) {

		return interviewRepository.findByRoundAndResult(round, result).stream().map(mapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	@Cacheable(value = "selectedInterviews", key = "'all'")
	public List<InterviewResponseDto> getSelectedInterviews() {

		return interviewRepository.findSelectedInterviews(InterviewResult.SELECTED).stream().map(mapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	@Cacheable(value = "pendingInterviews", key = "'all'")
	public List<InterviewResponseDto> getPendingInterviews() {

		return interviewRepository.findPendingInterviews().stream().map(mapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	@Cacheable(value = "interviewsPages", key = "#pageNo + '-' + #pageSize")
	public Page<InterviewResponseDto> getInterviews(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);

		Page<Interview> interviews = interviewRepository.findAll(pageable);

		return interviews.map(mapper::toResponseDto);
	}

	@Override
	@Cacheable(value = "interviewsSorted", key = "#pageNo + '-' + #pageSize + '-' + #field")
	public Page<InterviewResponseDto> getInterviewsSorted(int pageNo, int pageSize, String field) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(field).ascending());

		Page<Interview> interviews = interviewRepository.findAll(pageable);

		return interviews.map(mapper::toResponseDto);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "interviews", "interviewsByResult", "interviewsByRound", "interviewsByApplication",
			"interviewsByInterviewer", "interviewsByRoundAndResult", "selectedInterviews", "pendingInterviews",
			"interviewsPages", "interviewsSorted" }, allEntries = true)
	public void updateInterviewResult(Long id, InterviewResult result) {

		log.info("Updating interview result. Interview ID: {}, Result: {}", id, result);

		Interview interview = interviewRepository.findById(id)
				.orElseThrow(() -> new InterviewNotFoundException(PlacementConstants.INTERVIEW_NOT_FOUND + id));

		interview.setResult(result);
		interviewRepository.save(interview);

		
		Application application = interview.getApplication();

		if (result == InterviewResult.SELECTED) {

			application.setStatus(ApplicationStatus.SELECTED);

			log.info("Application status updated to SELECTED. Application ID: {}", application.getId());

		} else if (result == InterviewResult.REJECTED) {

			application.setStatus(ApplicationStatus.REJECTED);

			log.info("Application status updated to REJECTED. Application ID: {}", application.getId());
		}

		applicationRepository.save(application);

		log.info("Interview result updated successfully. Interview ID: {}", id);
	}
}
