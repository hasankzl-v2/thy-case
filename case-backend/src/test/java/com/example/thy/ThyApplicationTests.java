package com.example.thy;

import com.example.thy.dto.RouteDto;
import com.example.thy.dto.RouteValidationDto;
import com.example.thy.dto.request.RouteRequestDto;
import com.example.thy.service.RouteService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class ThyApplicationTests {
	@Autowired
	RouteService routeService;
	static RouteValidationDto allRouteValidationDto;
	static RouteRequestDto routeRequestDto;

	@PostConstruct
	void init() {
		 routeRequestDto = new RouteRequestDto();
		routeRequestDto.setStartLocationId(39L);
		routeRequestDto.setEndLocationId(43L);
		routeRequestDto.setDepartureDate(LocalDate.of(2025,3,3));
		 allRouteValidationDto = routeService.findAllValidRoutes(routeRequestDto);
	}

	@Test
	void validateRoutes() {
		allRouteValidationDto.getValidRoutes().forEach(validRoute -> {
			assert validRoute.getDepartureDate().equals(LocalDate.of(2025,3,3));
			assert validRoute.isValid();
			assert validRoute.getEndLocationId().equals(routeRequestDto.getEndLocationId());
			assert validRoute.getStartLocationId().equals(routeRequestDto.getStartLocationId());
		});
	}



}
