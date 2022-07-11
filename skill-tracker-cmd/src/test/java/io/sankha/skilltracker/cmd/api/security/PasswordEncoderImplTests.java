package io.sankha.skilltracker.cmd.api.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PasswordEncoderImplTests {

  @InjectMocks PasswordEncoderImpl passwordEncoder;

  @Mock private BCryptPasswordEncoder encoder;

  @Test
  void hashPasswordTest() {
    Mockito.when(encoder.encode(Mockito.anyString())).thenCallRealMethod();
    var password = passwordEncoder.hashPassword("");
    Assertions.assertNotNull(password);
  }
}
