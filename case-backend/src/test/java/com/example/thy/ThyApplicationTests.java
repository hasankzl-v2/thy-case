package com.example.thy;

import com.example.thy.dto.RouteRequestDto;
import com.example.thy.dto.ValidRoutesResponseDto;
import com.example.thy.service.RouteService;
import jakarta.annotation.PostConstruct;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ThyApplicationTests {
	@Autowired
	RouteService routeService;
	static ValidRoutesResponseDto allValidRoutes;
	static RouteRequestDto routeRequestDto;
	@PostConstruct
	void init() {
		 routeRequestDto = new RouteRequestDto();
		routeRequestDto.setStartLocationId(15L);
		routeRequestDto.setEndLocationId(17L);
		routeRequestDto.setDepartureDate(LocalDate.of(2024,4,30));
		 allValidRoutes = routeService.findAllValidRoutes(routeRequestDto);
	}

	@Test
	void validateRoutes() {
		allValidRoutes.getValidRoutes().forEach(validRoute -> {
			assert validRoute.getDepartureDate().equals(LocalDate.of(2024,4,30));
			assert validRoute.isValid();
			assert validRoute.getEndLocationId().equals(routeRequestDto.getEndLocationId());
			assert validRoute.getStartLocationId().equals(routeRequestDto.getStartLocationId());
		});
	}

}
