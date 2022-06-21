package com.olatoye.photoapp.services.nativeFolder;

import com.olatoye.photoapp.dtos.requests.NativeRequestDto;
import com.olatoye.photoapp.dtos.responses.NativeResponseDto;

import java.util.List;

public interface NativeService {
    NativeResponseDto saveNative(NativeRequestDto nativeRequestDto);
    List<NativeResponseDto> findNativesByCohortNumber(Integer cohortNumber);
    NativeResponseDto findByEmail(String email);
    NativeResponseDto findByFirstName(String firstName);
    NativeResponseDto findByLastName(String lastName);
    NativeResponseDto findByUserName(String userName);
    NativeResponseDto findById(Long id);
}
