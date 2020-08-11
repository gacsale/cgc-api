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

import com.example.cgc.model.TcProduct;
import com.example.cgc.repository.TcProductRepository;
import com.example.cgc.service.ErrorManagerService;
import com.example.cgc.utils.ApiResponse;
import com.example.cgc.utils.ResponseResult;

@RestController
@RequestMapping("/product")
public class TcProductController {
	
	private ApiResponse apiResponse;
	
	public TcProductController() {
		apiResponse = new ApiResponse();
		apiResponse.setData(null);
		apiResponse.setSingleValue(null);
	}
	
	@Autowired
	ErrorManagerService errorManagerService;
	
	@Autowired
	TcProductRepository tcProductRepository;
	
	@PutMapping("/add")
	public ApiResponse setProduct(@Valid @RequestBody TcProduct tcProduct) {
		try {
			tcProductRepository.save(tcProduct);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;		
	}
	
	@PostMapping("/{productId}")
	public ApiResponse updProduct(@PathVariable(value = "productId") Long productId, @Valid @RequestBody TcProduct tcProduct) {
		try {
			Optional<TcProduct> iu = tcProductRepository.findById(productId);
			if (iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				TcProduct u = iu.get();
				if (u.getProductId() == tcProduct.getProductId()) {
					u.setProductDesc(tcProduct.getProductDesc());
					u.setStatusId(tcProduct.getStatusId());
					tcProductRepository.save(u);
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
	
	@GetMapping("/{productId}")
	public ApiResponse getProductById(@PathVariable(value = "productId") Long productId) {
		try {
			Optional<TcProduct> iu = tcProductRepository.findById(productId);
			if (iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				List<TcProduct> data = new ArrayList<>();
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
	
	@GetMapping("/sku/{sku}")
	public ApiResponse getProductBySku(@PathVariable(value = "sku") Long sku) {
		try {
			Optional<TcProduct> iu = tcProductRepository.findBySku(sku);
			if (iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				List<TcProduct> data = new ArrayList<>();
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
			List<TcProduct> data = tcProductRepository.findAll();
			apiResponse.setData(data);
			apiResponse.setStatus(ResponseResult.success.getValue());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;
	}

}
