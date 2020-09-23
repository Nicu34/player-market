package com.feecalculatorservice.controller;

import com.feecalculatorservice.business.FeeService;
import com.feecalculatorservice.dto.PlayerFeeRequest;
import com.feecalculatorservice.dto.PlayerFeeResponse;
import com.feecalculatorservice.exception.PTSClientException;
import com.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/fee")
@Api(tags = "Fee data controller. For calculating contract, transfer and team fees.")
public class FeeDataController {

    private FeeService feeService;

    @GetMapping(value = "/player")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Calculate player fees by player id with currency rates from given date.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved data."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 500, message = "Error received while trying to get data from Player Team service."),
    })
    public PlayerFeeResponse calculatePlayerFees(@RequestParam(name = "id") int playerId,
                                                 @RequestParam @DateTimeFormat(pattern = Constants.DATE_FORMAT) LocalDate date) throws PTSClientException {
        return feeService.calculatePlayerFees(playerId, date);
    }

    @GetMapping(value = "/team")
    @ApiOperation(value = "Calculate all players fees by team id with currency rates from given date.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved data."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 500, message = "Error received while trying to get data from Player Team service."),
    })
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerFeeResponse> calculateTeamPlayerFees(@RequestParam(name = "id") int teamId,
                                                           @RequestParam @DateTimeFormat(pattern = Constants.DATE_FORMAT) LocalDate date) throws PTSClientException {
        return feeService.calculateTeamPlayerFees(teamId, date);
    }

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Calculate all players fees.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved data."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 500, message = "Error received while trying to get data from Player Team service."),
    })
    public List<PlayerFeeResponse> calculateAllPlayerFees(@RequestParam @DateTimeFormat(pattern = Constants.DATE_FORMAT) LocalDate date, Pageable pageable) throws PTSClientException {
        return feeService.calculateAllPlayerFees(date, pageable);
    }
}
