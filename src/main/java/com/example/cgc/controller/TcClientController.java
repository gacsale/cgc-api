package com.example.cgc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cgc.model.TcClient;
import com.example.cgc.repository.TcClientRepository;
import com.example.cgc.service.ErrorManagerService;
import com.example.cgc.utils.ApiResponse;
import com.example.cgc.utils.ResponseResult;

@RestController
@RequestMapping("/client")
public class TcClientController {
	
	private ApiResponse apiResponse;
	
	public TcClientController() {
		apiResponse = new ApiResponse();
		apiResponse.setData(null);
		apiResponse.setSingleValue(null);
	}
	
	@Autowired
	ErrorManagerService errorManagerService;
	
	@Autowired
	TcClientRepository tcClientRepository;
	
	@PutMapping("/add")
	public ApiResponse setClient(@Valid @RequestBody TcClient tcClient) {
		try {
			tcClientRepository.save(tcClient);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;		
	}
	
	@PostMapping("/{clientId}")
	public ApiResponse updClient(@PathVariable(value = "clientId") Long clientId, @Valid @RequestBody TcClient tcClient) {
		try {
			Optional<TcClient> iu = tcClientRepository.findById(clientId);
			if (iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				TcClient u = iu.get();
				if (u.getClientId() == tcClient.getClientId()) {
					u.setClientDesc(tcClient.getClientDesc());
					u.setStatusId(tcClient.getStatusId());
					tcClientRepository.save(u);
					apiResponse.setStatus(ResponseResult.success.getValue());
					apiResponse.setMessage(ResponseResult.success.getMessage());
				} else {
					apiResponse.setStatus(ResponseResult.fail.getValue());
					apiResponse.setMessage("El identificador indicado no coincide con la informacion proporcionada");
				}
			}
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;		
	}
	
	@GetMapping("/{clientId}")
	public ApiResponse getClientById(@PathVariable(value = "clientId") Long clientId) {
		try {
			Optional<TcClient> iu = tcClientRepository.findById(clientId);
			if (iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				List<TcClient> data = new ArrayList<>();
				data.add(iu.get());
				apiResponse.setData(data);
				apiResponse.setStatus(ResponseResult.success.getValue());
			}
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;
	}
	
	@GetMapping("/all")
	public ApiResponse getAll() {
		try {
			List<TcClient> data = tcClientRepository.findAll();
			apiResponse.setData(data);
			apiResponse.setStatus(ResponseResult.success.getValue());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;
	}

}
