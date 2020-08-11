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

import com.example.cgc.model.TcDetailSale;
import com.example.cgc.model.TcSale;
import com.example.cgc.repository.TcDetailSaleRepository;
import com.example.cgc.repository.TcSaleRepository;
import com.example.cgc.service.ErrorManagerService;
import com.example.cgc.utils.ApiResponse;
import com.example.cgc.utils.ResponseResult;

@RestController
@RequestMapping("/sale")
public class TcSaleController {
	
	private ApiResponse apiResponse;
	
	public TcSaleController() {
		apiResponse = new ApiResponse();
		apiResponse.setData(null);
		apiResponse.setSingleValue(null);
	}
	
	@Autowired
	ErrorManagerService errorManagerService;
	
	@Autowired
	TcSaleRepository tcSaleRepository;
	
	@Autowired
	TcDetailSaleRepository tcDetailSaleRepository;
	
	@PutMapping("/add")
	public ApiResponse setSale(@Valid @RequestBody TcSale tcSale) {
		try {
			float total = 0, iva, subtotal;			
			for (TcDetailSale d : tcSale.getDetail()) {
				subtotal = d.getAmount() * d.getPrice();
				d.setTotal(subtotal);
				total += subtotal;
			}
			iva = (float) (total * 0.12);
			tcSale.setIva(iva);
			tcSale.setTotal(total);
			tcSale = tcSaleRepository.save(tcSale);
			for (TcDetailSale d : tcSale.getDetail()) {
				d.setTcSale(tcSale);
				tcDetailSaleRepository.save(d);
			}
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;		
	}
	
	@PostMapping("/{saleId}")
	public ApiResponse updSale(@PathVariable(value = "saleId") Long saleId, @Valid @RequestBody TcSale tcSale) {
		try {
			Optional<TcSale> iu = tcSaleRepository.findById(saleId);
			if (iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				TcSale u = iu.get();
				if (u.getSaleId() == tcSale.getSaleId()) {					
					u.setStatusId(tcSale.getStatusId());
					tcSaleRepository.save(u);
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
	
	@GetMapping("/{saleId}")
	public ApiResponse getSaleById(@PathVariable(value = "saleId") Long saleId) {
		try {
			Optional<TcSale> iu = tcSaleRepository.findById(saleId);
			if (iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				List<TcSale> data = new ArrayList<>();
				TcSale s = iu.get();
				List<TcDetailSale> detail = tcDetailSaleRepository.findAllByTcSale(s);
				s.setDetail(detail);
				data.add(s);
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
			List<TcSale> data = tcSaleRepository.findAll();
			apiResponse.setData(data);
			apiResponse.setStatus(ResponseResult.success.getValue());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;
	}

}
