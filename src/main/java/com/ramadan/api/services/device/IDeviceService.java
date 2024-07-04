package com.ramadan.api.services.device;

import com.ramadan.api.dto.device.DeviceRequestDto;
import com.ramadan.api.dto.device.DeviceResponseDto;
import com.ramadan.api.dto.device.DeviceUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;

import java.util.Map;

public interface IDeviceService {
    DeviceResponseDto findByUuid(String uuid) throws APIErrorException;

    Map<String, Object> findAllByCompany(String companyUuid, int page, int size, String[] sort) throws APIErrorException;

    DeviceResponseDto saveDevice(DeviceRequestDto deviceRequestDto) throws APIErrorException;

    DeviceResponseDto updateDevice(DeviceUpdateDto requestDto) throws APIErrorException;

    void deleteDevice(String uuid) throws APIErrorException;

    Map<String, Object> getAll(int page, int size, String[] sort) throws APIErrorException;
}
