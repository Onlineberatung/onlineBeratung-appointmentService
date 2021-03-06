package com.vi.appointmentservice.service;

import com.vi.appointmentservice.agencyservice.generated.web.AgencyControllerApi;
import com.vi.appointmentservice.service.securityheader.SecurityHeaderSupplier;
import com.vi.appointmentservice.agencyservice.generated.ApiClient;
import com.vi.appointmentservice.userservice.generated.web.model.AgencyDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgencyService {

    private final @NonNull AgencyControllerApi agencyControllerApi;
    private final @NonNull SecurityHeaderSupplier securityHeaderSupplier;

    private void addDefaultHeaders(ApiClient apiClient) {
        var headers = this.securityHeaderSupplier.getKeycloakAndCsrfHttpHeaders();
        headers.forEach((key, value) -> apiClient.addDefaultHeader(key, value.iterator().next()));
    }

}
