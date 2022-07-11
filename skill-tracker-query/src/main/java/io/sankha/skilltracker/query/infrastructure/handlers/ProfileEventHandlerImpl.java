package io.sankha.skilltracker.query.infrastructure.handlers;

import io.sankha.skilltracker.exception.BusinessException;
import io.sankha.skilltracker.profile.events.ProfileAddedEvent;
import io.sankha.skilltracker.profile.events.ProfileDeletedEvent;
import io.sankha.skilltracker.profile.events.ProfileUpdatedEvent;
import io.sankha.skilltracker.profile.models.EmployeeEntity;
import io.sankha.skilltracker.query.api.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProfileEventHandlerImpl implements ProfileEventHandler {
  private final ProfileRepository profileRepository;

  @Override
  public void on(ProfileAddedEvent event) {
    var employeeEntity =
        EmployeeEntity.builder()
            .id(event.getId())
            .firstName(event.getFirstName())
            .lastName(event.getLastName())
            .email(event.getEmail())
            .mobile(event.getMobile())
            .angular(event.getAngular())
            .aptitude(event.getAptitude())
            .aws(event.getAws())
            .communication(event.getCommunication())
            .docker(event.getDocker())
            .git(event.getGit())
            .hibernate(event.getHibernate())
            .htmlCssJavascript(event.getHtmlCssJavascript())
            .jenkins(event.getJenkins())
            .react(event.getReact())
            .restful(event.getRestful())
            .spoken(event.getSpoken())
            .spring(event.getSpring())
            .build();
    profileRepository.save(employeeEntity);
  }

  @Override
  public void on(ProfileUpdatedEvent event) {
    var currentEmployee =
        profileRepository.findById(event.getId()).orElseThrow(BusinessException::new);

    currentEmployee.setFirstName(event.getFirstName());
    currentEmployee.setLastName(event.getLastName());
    currentEmployee.setEmail(event.getEmail());
    currentEmployee.setMobile(event.getMobile());
    currentEmployee.setAngular(event.getAngular());
    currentEmployee.setAptitude(event.getAptitude());
    currentEmployee.setAws(event.getAws());
    currentEmployee.setCommunication(event.getCommunication());
    currentEmployee.setDocker(event.getDocker());
    currentEmployee.setHibernate(event.getHibernate());
    currentEmployee.setJenkins(event.getJenkins());
    currentEmployee.setGit(event.getGit());
    currentEmployee.setHtmlCssJavascript(event.getHtmlCssJavascript());
    currentEmployee.setReact(event.getReact());
    currentEmployee.setRestful(event.getRestful());
    currentEmployee.setSpoken(event.getSpoken());
    currentEmployee.setSpring(event.getSpring());
    profileRepository.save(currentEmployee);
  }

  @Override
  public void on(ProfileDeletedEvent event) {
    profileRepository.deleteById(event.getId());
  }
}
