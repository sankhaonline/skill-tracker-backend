package io.sankha.skilltracker.query.api.repositories;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.profile.models.EmployeeEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<EmployeeEntity, String> {
  List<BaseEntity> findByFirstName(String firstName);

  List<BaseEntity> findByHtmlCssJavascriptGreaterThan(int htmlCssJavascript);

  List<BaseEntity> findByAngularGreaterThan(int angular);

  List<BaseEntity> findByReactGreaterThan(int react);

  List<BaseEntity> findBySpringGreaterThan(int spring);

  List<BaseEntity> findByRestfulGreaterThan(int restful);

  List<BaseEntity> findByHibernateGreaterThan(int hibernate);

  List<BaseEntity> findByGitGreaterThan(int git);

  List<BaseEntity> findByDockerGreaterThan(int docker);

  List<BaseEntity> findByJenkinsGreaterThan(int jenkins);

  List<BaseEntity> findByAwsGreaterThan(int aws);

  List<BaseEntity> findBySpokenGreaterThan(int spoken);

  List<BaseEntity> findByCommunicationGreaterThan(int communication);

  List<BaseEntity> findByAptitudeGreaterThan(int aptitude);
}
