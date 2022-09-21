package com.vi.appointmentservice.api.controller;

import com.vi.appointmentservice.api.model.CalDavCredentials;
import com.vi.appointmentservice.api.service.calcom.caldav.CalDavService;
import com.vi.appointmentservice.generated.api.controller.CaldavApi;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "calDav")
@RequiredArgsConstructor
public class CalDavController implements CaldavApi {

  private final @NotNull CalDavService calDavService;

  @Override
  public ResponseEntity<Void> resetCalDavPassword(
      @Valid CalDavCredentials calDavCredentials) {
    calDavService.resetPassword(calDavCredentials);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }
}