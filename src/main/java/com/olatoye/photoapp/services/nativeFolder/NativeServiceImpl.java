package com.olatoye.photoapp.services.nativeFolder;

import com.olatoye.photoapp.data.models.Native;
import com.olatoye.photoapp.data.repositories.NativeRepository;
import com.olatoye.photoapp.dtos.requests.NativeRequestDto;
import com.olatoye.photoapp.dtos.responses.NativeResponseDto;
import com.olatoye.photoapp.utils.ModelMapper;
import com.olatoye.photoapp.web.exceptions.NativeAlreadyExistsException;
import com.olatoye.photoapp.web.exceptions.NativeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class NativeServiceImpl implements NativeService {
    private final NativeRepository nativeRepository;
    private final ModelMapper modelMapper;

    public NativeServiceImpl(@Autowired NativeRepository nativeRepository, ModelMapper modelMapper) {
        this.nativeRepository = nativeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NativeResponseDto saveNative(NativeRequestDto nativeRequestDto) {
        boolean alreadyExists = nativeRepository.existsByEmail(nativeRequestDto.getEmail());
        if (alreadyExists) throw new NativeAlreadyExistsException("Native already exists");

        Native newNative = modelMapper.map(nativeRequestDto);
        nativeRepository.save(newNative);
        return modelMapper.map(newNative);
    }

    @Override
    public List<NativeResponseDto> findNativesByCohortNumber(Integer cohortNumber) {
        List<Native> foundNatives = nativeRepository.findByCohortCohortNumber(cohortNumber);
        if (foundNatives.isEmpty())
            throw new NativeNotFoundException("No native found for cohort number " + cohortNumber);

        List<NativeResponseDto> listOfNatives = new ArrayList<>();
        for (Native aNative : foundNatives) {
            listOfNatives.add(modelMapper.map(aNative));
        }
        return listOfNatives;
    }

    @Override
    public NativeResponseDto findByEmail(String email) {
        Native foundNative = nativeRepository.findByEmail(email).orElseThrow(
                () -> new NativeNotFoundException("Native with email " + email + " not found"));
        return modelMapper.map(foundNative);
    }

    @Override
    public NativeResponseDto findByFirstName(String firstName) {
        Native foundNative = nativeRepository.findByFirstName(firstName).orElseThrow(
                () -> new NativeNotFoundException("Native with first name " + firstName + " not found"));
        return modelMapper.map(foundNative);
    }

    @Override
    public NativeResponseDto findByLastName(String lastName) {
        Native foundNative = nativeRepository.findByLastName(lastName).orElseThrow(
                () -> new NativeNotFoundException("Native with last name " + lastName + " not found"));
        return modelMapper.map(foundNative);
    }

    @Override
    public NativeResponseDto findByUserName(String userName) {
        Native foundNative = nativeRepository.findByUserName(userName).orElseThrow(
                () -> new NativeNotFoundException("Native with username " + userName + " not found"));
        return modelMapper.map(foundNative);
    }

    @Override
    public NativeResponseDto findById(Long id) {
        Native foundNative = nativeRepository.findById(id).orElseThrow(
                () -> new NativeNotFoundException("Native with id " + id + " not found"));
        return modelMapper.map(foundNative);
    }
}
