package request.sender.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import request.sender.dto.RequestDto;
import request.sender.service.RequestSendingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sender")
public class RequestController {
    private final RequestSendingService service;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public void startRequestSending(@RequestBody RequestDto requestDto) {
        service.startRequestSending(requestDto);
    }

    @GetMapping("/results")
    public List<Object> getResults() {
        return service.getResults();
    }
}
