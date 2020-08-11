package com.example.cgc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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
import com.example.cgc.model.TcUser;
import com.example.cgc.repository.TcClientRepository;
import com.example.cgc.repository.TcUserRepository;
import com.example.cgc.service.ErrorManagerService;
import com.example.cgc.utils.ApiResponse;
import com.example.cgc.utils.ResponseResult;
import com.example.cgc.utils.User;

@RestController
@RequestMapping("/user")
public class TcUserController {

	private ApiResponse apiResponse;

	public TcUserController() {
		apiResponse = new ApiResponse();
		apiResponse.setData(null);
		apiResponse.setSingleValue(null);
	}

	@Autowired
	ErrorManagerService errorManagerService;

	@Autowired
	TcUserRepository tcUserRepository;
	
	@Autowired
	TcClientRepository tcClientRepository;

	@PostMapping("/login")
	public ApiResponse login(@Valid @RequestBody User user, HttpServletRequest request) {
		try {
			/*byte[] tmpBPass = Base64.decodeBase64(user.getPassword());
			String tmpPass = new String(tmpBPass);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername().trim().toLowerCase(), tmpPass));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtProvider.generateJwtToken(authentication);*/
			Optional<TcUser> iu = tcUserRepository.findByUsername(user.getUsername().trim().toLowerCase());
			TcUser tcUser = iu.get();
			Optional<TcClient> ic = tcClientRepository.findByTcUser(tcUser);
			TcClient c = ic.get();
			List<TcClient> data = new ArrayList<>();
			data.add(c);
			apiResponse.setData(data);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
			//apiResponse.setSingleValue(token);
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());

		}
		return apiResponse;
	}

	@PutMapping("/add")
	public ApiResponse setUser(@Valid @RequestBody TcUser tcUser) {
		try {
			tcUser.setUsername(tcUser.getUsername().trim().toLowerCase());
			tcUser.setEmail(tcUser.getEmail().trim().toLowerCase());			
			tcUserRepository.save(tcUser);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;
	}

	@PostMapping("/{userId}")
	public ApiResponse updUser(@PathVariable(value = "userId") Long userId, @Valid @RequestBody TcUser tcUser) {
		try {
			Optional<TcUser> iu = tcUserRepository.findById(userId);
			if (iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				TcUser u = iu.get();
				if (u.getUserId() == tcUser.getUserId()) {
					u.setFullname(tcUser.getFullname());
					u.setStatusId(tcUser.getStatusId());
					tcUserRepository.save(u);
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

	@GetMapping("/{userId}")
	public ApiResponse getUserById(@PathVariable(value = "userId") Long userId) {
		try {
			Optional<TcUser> iu = tcUserRepository.findById(userId);
			if (!iu.isEmpty()) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("No existen datos relacionados con el identificador");
			} else {
				List<TcUser> data = new ArrayList<>();
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
			List<TcUser> data = tcUserRepository.findAll();
			apiResponse.setData(data);
			apiResponse.setStatus(ResponseResult.success.getValue());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			apiResponse.setMessage(errorManagerService.managerException(e));
		}
		return apiResponse;
	}

}
