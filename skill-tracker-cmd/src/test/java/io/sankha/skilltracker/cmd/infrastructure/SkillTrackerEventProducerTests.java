package io.sankha.skilltracker.cmd.infrastructure;

import io.sankha.skilltracker.profile.events.ProfileAddedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SkillTrackerEventProducerTests {

  @InjectMocks SkillTrackerEventProducer eventProducer;

  @Mock private KafkaTemplate<String, Object> kafkaTemplate;

  @Test
  void hashPasswordTest() {
    Mockito.when(kafkaTemplate.send(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
        .thenReturn(null);

    eventProducer.produce("", ProfileAddedEvent.builder().build());
    Mockito.verify(kafkaTemplate, Mockito.times(1)).send("", ProfileAddedEvent.builder().build());
  }
}
