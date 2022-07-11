package io.sankha.skilltracker.query.api.queries.handler;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.exception.BusinessException;
import io.sankha.skilltracker.query.api.dto.SkillNames;
import io.sankha.skilltracker.query.api.queries.profile.FindAllProfilesQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByIdQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByNameQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileWithSkillQuery;
import io.sankha.skilltracker.query.api.repositories.ProfileRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProfileQueryHandlerImpl implements ProfileQueryHandler {

  public static final int SKILL_LEVEL = 10;
  private final ProfileRepository profileRepository;

  @Override
  public List<BaseEntity> handle(FindAllProfilesQuery query) {
    var bankAccounts = profileRepository.findAll();
    var bankAccountsList = new ArrayList<BaseEntity>();
    bankAccounts.forEach(bankAccountsList::add);
    return bankAccountsList;
  }

  @Override
  public List<BaseEntity> handle(FindProfileByIdQuery query) {
    return profileRepository.findById(String.valueOf(query.getId())).stream()
        .collect(Collectors.toList());
  }

  @Override
  public List<BaseEntity> handle(FindProfileByNameQuery query) {
    return profileRepository.findByFirstName(query.getFirstName());
  }

  @Override
  public List<BaseEntity> handle(FindProfileWithSkillQuery query) {
    List<BaseEntity> skillProfileList;
    switch (SkillNames.valueOf(query.getSkillName().toUpperCase())) {
      case HTMLCSSJAVASCRIPT:
        skillProfileList = profileRepository.findByHtmlCssJavascriptGreaterThan(SKILL_LEVEL);
        break;
      case ANGULAR:
        skillProfileList = profileRepository.findByAngularGreaterThan(SKILL_LEVEL);
        break;
      case REACT:
        skillProfileList = profileRepository.findByReactGreaterThan(SKILL_LEVEL);
        break;
      case SPRING:
        skillProfileList = profileRepository.findBySpringGreaterThan(SKILL_LEVEL);
        break;
      case RESTFUL:
        skillProfileList = profileRepository.findByRestfulGreaterThan(SKILL_LEVEL);
        break;
      case HIBERNATE:
        skillProfileList = profileRepository.findByHibernateGreaterThan(SKILL_LEVEL);
        break;
      case GIT:
        skillProfileList = profileRepository.findByGitGreaterThan(SKILL_LEVEL);
        break;
      case DOCKER:
        skillProfileList = profileRepository.findByDockerGreaterThan(SKILL_LEVEL);
        break;
      case JENKINS:
        skillProfileList = profileRepository.findByJenkinsGreaterThan(SKILL_LEVEL);
        break;
      case AWS:
        skillProfileList = profileRepository.findByAwsGreaterThan(SKILL_LEVEL);
        break;
      case SPOKEN:
        skillProfileList = profileRepository.findBySpokenGreaterThan(SKILL_LEVEL);
        break;
      case COMMUNICATION:
        skillProfileList = profileRepository.findByCommunicationGreaterThan(SKILL_LEVEL);
        break;
      case APTITUDE:
        skillProfileList = profileRepository.findByAptitudeGreaterThan(SKILL_LEVEL);
        break;
      default:
        throw new BusinessException("No suitable skill found!");
    }
    return skillProfileList;
  }
}
