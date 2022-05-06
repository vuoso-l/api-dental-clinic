package com.example.dentalClinicApi.service.impl;

import com.example.dentalClinicApi.DTO.AddressDTO;
import com.example.dentalClinicApi.entity.Address;
import com.example.dentalClinicApi.exception.ResourceNotFoundException;
import com.example.dentalClinicApi.repository.IAddressRepository;
import com.example.dentalClinicApi.service.IAddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressService implements IAddressService {

    @Autowired
    IAddressRepository iAddressRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public AddressDTO create(AddressDTO addressDTO) {
        if(addressDTO == null){
            throw new ResourceNotFoundException("Domicilio", "id", addressDTO.getId());
        }

        Address address = mapEntity(addressDTO);
        return mapDTO(iAddressRepository.save(address));
    }

    @Override
    public AddressDTO findOne(Integer id) {
        Address address = iAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Domicilio", "Id", id));
        return mapDTO(address);
    }

    @Override
    public AddressDTO update(AddressDTO addressDTO, Integer id) {
        Address address = iAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Domicilio", "Id", id));

        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setLocality(addressDTO.getLocality());
        address.setProvince(addressDTO.getProvince());

        return mapDTO(iAddressRepository.save(address));
    }

    @Override
    public void delete(Integer id) {
        Address address = iAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Domicilio", "Id", id));

        iAddressRepository.delete(address);
    }

    @Override
    public Collection<AddressDTO> findAll() {
        List<Address> addressList= iAddressRepository.findAll();
        Set<AddressDTO> addressesDTO= new HashSet<>();
        for (Address address: addressList){
            addressesDTO.add(mapper.convertValue(address, AddressDTO.class));
        }
        return addressesDTO;
    }

    private AddressDTO mapDTO(Address address){
        return mapper.convertValue(address, AddressDTO.class);
    }

    public Address mapEntity(AddressDTO addressDTO){
        return mapper.convertValue(addressDTO, Address.class);
    }
}
