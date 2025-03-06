package com.example.thy;

import com.example.thy.dto.RouteDto;
import com.example.thy.dto.request.RouteRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findValidRoutes() {
        RouteRequestDto routeRequestDto = new RouteRequestDto();
        routeRequestDto.setStartLocationId(39L);
        routeRequestDto.setEndLocationId(43L);
        routeRequestDto.setDepartureDate(LocalDate.of(2024, 4, 30));

        //  Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RouteRequestDto> request = new HttpEntity<>(routeRequestDto, headers);

        //  Post request
        String url = "http://localhost:" + port + "/api/route/findValidRoutes";
        ResponseEntity<RouteDto[]> response = restTemplate.postForEntity(url, request, RouteDto[].class);

        // http status code should be 200
        assert (response.getStatusCode()).equals(HttpStatus.OK);


        List<RouteDto> routes = List.of(Objects.requireNonNull(response.getBody()));
        // route should not be empty
        assert (!routes.isEmpty());

        //  assert
        routes.forEach(validRoute -> {
            assert validRoute.getDepartureDate().equals(LocalDate.of(2024, 4, 30));
            assert validRoute.isValid();
            assert validRoute.getEndLocationId().equals(routeRequestDto.getEndLocationId());
            assert validRoute.getStartLocationId().equals(routeRequestDto.getStartLocationId());
        });
    }
}
