package com.ramadan.api.services.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.device.DeviceRequestDto;
import com.ramadan.api.dto.device.DeviceResponseDto;
import com.ramadan.api.dto.device.DeviceUpdateDto;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.device.Device;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.company.CompanyRepository;
import com.ramadan.api.repository.device.DeviceRepository;

@Service
@Transactional
public class DeviceService implements IDeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private MapClassWithDto<Device, DeviceRequestDto> mapClassWithRequestDto;

	@Autowired
	private MapClassWithDto<Device, DeviceResponseDto> mapClassWithResponseDto;

	@Override
	public DeviceResponseDto findByUuid(String uuid) throws APIErrorException {
		Device device = deviceRepository.findByUuid(uuid);
		if (device == null) {
			throw new APIErrorException(ErrorCode.D001);
		} else {
			return mapClassWithResponseDto.convertToDto(device, DeviceResponseDto.class);
		}
	}

	@Override
	public Map<String, Object> findAllByCompany(String companyUuid, int page, int size, String[] sort)
			throws APIErrorException {
		Company company = companyRepository.findByUuid(companyUuid);
		if (company != null) {
			List<Sort.Order> orders = Utils.getListOrderBySort(sort);
			Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

			Page<Device> devicePage = deviceRepository.findAllByCompany(companyUuid, pageable);
			if (devicePage.hasContent()) {
				List<DeviceResponseDto> devices = devicePage.getContent().stream()
						.map(device -> mapClassWithResponseDto.convertToDto(device, DeviceResponseDto.class))
						.collect(Collectors.toList());

				Map<String, Object> response = new HashMap<>();
				response.put("devices", devices);
				response.put("currentPage", devicePage.getNumber());
				response.put("totalItems", devicePage.getTotalElements());
				response.put("totalPages", devicePage.getTotalPages());

				return response;
			} else {
				throw new APIErrorException(ErrorCode.D003);
			}
		} else {
			throw new APIErrorException(ErrorCode.A010);
		}
	}

	@Override
	public DeviceResponseDto saveDevice(DeviceRequestDto deviceRequestDto) throws APIErrorException {
		if (deviceRequestDto != null) {
			Company company = companyRepository.findByUuid(deviceRequestDto.getCompanyUuid());
			if (company == null) {
				throw new APIErrorException(ErrorCode.A010);
			} else {
				Device device = mapClassWithRequestDto.convertToEntity(deviceRequestDto, Device.class);
				device.setCompany(company);
				Device savedDevice = deviceRepository.save(device);
				return mapClassWithResponseDto.convertToDto(savedDevice, DeviceResponseDto.class);
			}
		} else {
			throw new APIErrorException(ErrorCode.A507);
		}
	}

	@Override
	public DeviceResponseDto updateDevice(DeviceUpdateDto requestDto) throws APIErrorException {
		Device existingDevice = deviceRepository.findByUuid(requestDto.getUuid());
		if (existingDevice == null) {
			throw new APIErrorException(ErrorCode.A507);
		} else {
			// Update device attributes if provided in the requestDto

			/*
			 * if (requestDto.getCodeMarquePhone() != null) {
			 * existingDevice.setCodeMarquePhone(requestDto.getCodeMarquePhone()); } if
			 * (requestDto.getCodeModulePhone() != null) {
			 * existingDevice.setCodeModulePhone(requestDto.getCodeModulePhone()); }
			 */
			if (requestDto.getCordova() != null) {
				existingDevice.setCordova(requestDto.getCordova());
			}
			if (requestDto.getCodePlateform() != null) {
				existingDevice.setCodePlateform(requestDto.getCodePlateform());
			}
			if (requestDto.getVersion() != null) {
				existingDevice.setVersion(requestDto.getVersion());
			}
			if (requestDto.getEmei() != null) {
				existingDevice.setEmei(requestDto.getEmei());
			}
			if (requestDto.getRang() != null) {
				existingDevice.setRang(requestDto.getRang());
			}
			if (requestDto.getName() != null) {
				// Assuming name is an attribute of the related Brand entity
				existingDevice.setName(requestDto.getName());
			}
			if (requestDto.getDescription() != null) {
				// Assuming description is an attribute of the related Brand entity
				existingDevice.setDescription(requestDto.getDescription());
			}
			// Add similar update logic for other attributes

			Device updatedDevice = deviceRepository.save(existingDevice);
			return mapClassWithResponseDto.convertToDto(updatedDevice, DeviceResponseDto.class);
		}
	}

	@Override
	public void deleteDevice(String uuid) throws APIErrorException {
		Device device = deviceRepository.findByUuid(uuid);
		if (device != null) {
			device.setDelete(true);
			deviceRepository.save(device);
		} else {
			throw new APIErrorException(ErrorCode.D001);
		}
	}

	@Override
	public Map<String, Object> getAll(int page, int size, String[] sort) throws APIErrorException {
		List<Sort.Order> orders = Utils.getListOrderBySort(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		Page<Device> devicePage = deviceRepository.findAll(pageable);

		List<DeviceResponseDto> devices = devicePage.getContent().stream()
				.map(device -> mapClassWithResponseDto.convertToDto(device, DeviceResponseDto.class))
				.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("devices", devices);
		response.put("currentPage", devicePage.getNumber());
		response.put("totalItems", devicePage.getTotalElements());
		response.put("totalPages", devicePage.getTotalPages());

		return response;
	}
}
